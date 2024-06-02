package com.maleo.devsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.TextView;
import com.unity3d.player.UnityPlayerActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DLCPicker_DevSA {
  private Context context;

  public DLCPicker_DevSA(Context ct) {
    this.context = ct;
    showAlertDialog();
  }

  private void showAlertDialog() {
    // Check if context is an instance of Activity
    if (!(context instanceof Activity)) {
      throw new IllegalArgumentException("Context must be an instance of Activity");
    }

    // Create a TextView with the message and set padding
    TextView messageView = new TextView(context);
    messageView.setText("Unlock Every Map");

    // Convert dp to px for padding
    int paddingInDp = 50; // Set padding in dp (example: 20dp)
    int paddingInPx =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                paddingInDp,
                context.getResources().getDisplayMetrics());
    messageView.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);

    // Set left and right padding to 100dp
    int paddingLRInDp = 100;
    int paddingLRInPx =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                paddingLRInDp,
                context.getResources().getDisplayMetrics());
    messageView.setPadding(paddingLRInPx, paddingInPx, paddingLRInPx, paddingInPx);

    AlertDialog.Builder builder =
        new AlertDialog.Builder(context)
            .setTitle("Patch DLC")
            .setCancelable(false)
            .setView(messageView) // Set the TextView as the dialog view
            .setPositiveButton(
                "Select",
                (dialog, which) -> {
                  Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                  intent.setType("application/zip");
                  ((Activity) context).startActivityForResult(intent, 89);
                })
            .setNegativeButton(
                "Continue",
                (dialog, which) -> {
                  DevSAUtils.goIntent(context, UnityPlayerActivity.class);
                });

    builder.create().show();
  }

  public void decompressDlc(Uri uri) {
    new LoaderDialog(context).show();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    executor.execute(
        () -> {
          boolean success = false;
          try {
            ZipExtractor_DevSA.extractZipFile(context, uri);
            success = true;
          } catch (Exception e) {
            e.printStackTrace();
          }

          boolean finalSuccess = success;
          handler.post(
              () -> {
                if (finalSuccess) {
                  DevSAUtils.writeFileInternal(context, "dlc_devSA", "patched");
                  DevSAUtils.goIntent(context, UnityPlayerActivity.class);
                } else {
                  DevSAUtils.showToast(context, "Patching failed");
                }
              });
        });
  }
}
