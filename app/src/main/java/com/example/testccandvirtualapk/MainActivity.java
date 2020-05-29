package com.example.testccandvirtualapk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.didi.virtualapk.PluginManager;
import com.example.component_base.ComponentConst;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button bt_to_plugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        bt_to_plugin = (Button) findViewById(R.id.bt_to_plugin);
        bt_to_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/Plugin.apk");
                    File plugin = new File(pluginPath);
                    PluginManager.getInstance(MainActivity.this).loadPlugin(plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (PluginManager.getInstance(MainActivity.this).getLoadedPlugin("com.example.component_plugin") == null) {
                    Toast.makeText(getApplicationContext(), "未加载插件", Toast.LENGTH_SHORT).show();
                } else {
                    //同步调用，直接返回结果
                    CCResult result = CC.obtainBuilder(ComponentConst.Component_plugin.NAME)
                            .setActionName(ComponentConst.Component_plugin.Action.SHOW_ACTIVITY)
                            .build()
                            .call();
                    if(!result.isSuccess()){
                        Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
                                ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                }
                break;
            
            default:
                break;
        }
    }
}
