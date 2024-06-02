package com.maleo.devsa;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoaderDialog {

  private Context context;
  private AlertDialog alertDialog;

  public LoaderDialog(Context context) {
    this.context = context;
  }

  public void show() {
    dismiss();

    // Convert dp to px for padding
    int paddingInDp = 50;
    int paddingInPx =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                paddingInDp,
                context.getResources().getDisplayMetrics());

    // Set padding left and right to 100dp
    int paddingLRInDp = 100;
    int paddingLRInPx =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                paddingLRInDp,
                context.getResources().getDisplayMetrics());

    // Create a LinearLayout
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(
        paddingLRInPx, paddingInPx, paddingLRInPx, paddingInPx); // Set padding left and right
    layout.setGravity(Gravity.CENTER);

    // Create a ProgressBar and add it to the layout
    ProgressBar progressBar = new ProgressBar(context);
    layout.addView(progressBar);

    // Create a TextView and add it to the layout
    TextView textView = new TextView(context);
    textView.setText("Patching");

    // Convert dp to px for TextView padding
    int textViewPaddingInDp = 30;
    int textViewPaddingInPx =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                textViewPaddingInDp,
                context.getResources().getDisplayMetrics());
    textView.setPadding(0, textViewPaddingInPx, 0, 0);
    textView.setGravity(Gravity.CENTER);
    layout.addView(textView);

    // Create and show the dialog
    alertDialog = new AlertDialog.Builder(context).setView(layout).setCancelable(false).show();
  }

  public void dismiss() {
    if (alertDialog != null && alertDialog.isShowing()) {
      alertDialog.dismiss();
      alertDialog = null;
    }
  }
}
