package com.yzg.oemassistant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.yzg.oemassistant.R;
import com.yzg.oemassistant.base.BaseActivity;
import com.yzg.oemassistant.tool.CommonMethod;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Intent mIntent;
    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        ImageView ivSettings = findViewById(R.id.iv_settings);
        TextView tvStandardMachine = findViewById(R.id.tv_standard_machine);
        ivSettings.setOnClickListener(this);
        tvStandardMachine.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.iv_settings == id){
            onSets();
        }else if(R.id.tv_standard_machine == id){
            mIntent = new Intent(MainActivity.this, ScanCodeEntryActivity.class);
        }
        startActivity(mIntent);
    }

    private void onSets() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, R.string.need_to_dynamically_acquire_permissions, Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 0);
            } else {
                mIntent = new Intent(MainActivity.this,SettingsActivity.class);
            }
        } else {
            mIntent = new Intent(MainActivity.this,SettingsActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    mIntent = new Intent(MainActivity.this,SettingsActivity.class);
                }
            }
        }
    }

}
