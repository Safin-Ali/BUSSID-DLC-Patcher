package com.unity3d.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.maleo.devsa.DLCPicker_DevSA;
import com.maleo.devsa.DevSAUtils;
import com.unity3d.player.databinding.ActivityMainBinding;

public class MainActivity extends Activity{

  DLCPicker_DevSA dlcPickerCls;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    boolean alreadyPatched = DevSAUtils.checkDLCDecompressed(this);

    if (alreadyPatched) {
      DevSAUtils.goIntent(this, UnityPlayerActivity.class);
      return; // semicolon was missing here
    }

    this.dlcPickerCls = new DLCPicker_DevSA(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 89 && resultCode == Activity.RESULT_OK) {
      if (data != null) {
        dlcPickerCls.decompressDlc(data.getData());
      }
    }
  }
}
