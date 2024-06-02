package com.maleo.devsa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.unity3d.player.UnityPlayerActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DevSAUtils {

  public static void executeUNT(Context context) {
    Intent untIntent = new Intent(context, UnityPlayerActivity.class);
    context.startActivity(untIntent);
    ((Activity) context).finish();
  }

  public static boolean checkDLCDecompressed(Context context) {
    File directory = new File(context.getFilesDir().getPath());
    File file = new File(directory, "dlc_devSA");
    return file.exists();
  }

  public static void writeFileInternal(Context context, String fileName, String fileContent) {
    try {
      File directory = new File(context.getFilesDir().getPath());
      File file = new File(directory, fileName);
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(fileContent.getBytes());
      fos.close();
    } catch (IOException e) {
      ((Activity) context).finish();
    }
  }

  public static String readFileInternal(Context context, String fileName) {
    try {
      File directory = new File(context.getFilesDir().getPath());
      File file = new File(directory, fileName);
      if (!file.exists()) {
        return "";
      }

      BufferedReader reader = new BufferedReader(new FileReader(file));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
      reader.close();
      return stringBuilder.toString();
    } catch (IOException e) {
      return null;
    }
  }

  public static void showToast(final Context context, final String msg) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
  }

  public static void goIntent(final Context ct, final Class<?> nextIntent) {
    Intent intent = new Intent(ct, nextIntent);
    ct.startActivity(intent);
    if (ct instanceof Activity) {
      ((Activity) ct).finish();
    }
  }
}
