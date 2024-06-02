package com.unity3d.player;

import android.app.Activity;
import android.os.Bundle;
import com.maleo.devsa.DevSAUtils;
import com.maleo.devsa.LoaderDialog;

public class UnityPlayerActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DevSAUtils.showToast(this,"Patched By DevSA");
    }
}
