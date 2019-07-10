package com.blocki.drawingboard;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TouchPathActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "TouchPathActivity";

    private static final int CODE_PERMISSION_WRITEFILE = 0;
    private static final String NAME_SAVE_FILE_POSTFIX = ".png";
    private static String DIR_EXTERNAL_PATH = "";
    private static String DIR_APP_FILES = "";
    private Button resetBtn;
    private Button saveBtn;
    private TouchPathView pathView;



    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_path);

        resetBtn = findViewById(R.id.touchPath_reset_Btn);
        saveBtn = findViewById(R.id.btn_save_touchPath);
        pathView = findViewById(R.id.touchPath_pathView);
        resetBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        DIR_EXTERNAL_PATH = Environment.getExternalStorageDirectory().getPath();
        Log.d(TAG, "onCreate: DIR_EXTERNAL_PATH:" + DIR_EXTERNAL_PATH);
        DIR_APP_FILES = getExternalCacheDir().getPath();
        Log.d(TAG, "onCreate: DIR_APP_FILES:" + DIR_APP_FILES);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touchPath_reset_Btn:
                pathView.reset();
                break;
            case R.id.btn_save_touchPath:
                bitmap = pathView.getBitMap();
                if (bitmap != null){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"正在保存图片，请授予权限",Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CODE_PERMISSION_WRITEFILE);
                    }else{
                        boolean successed = saveBitMapAsFile();
                        if (successed){
                            Toast.makeText(this,"保存完成",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this,"保存失败，未获取到绘制的bitmap",Toast.LENGTH_SHORT).show();
                }
            default:break;
        }
    }

    private boolean saveBitMapAsFile(){
        boolean saved = false;
        FileOutputStream fos = null;
        try {
            String fileName = UUID.randomUUID().toString()+NAME_SAVE_FILE_POSTFIX;
            Log.d(TAG, "saveBitMapAsFile: " + fileName);
            File file = new File(DIR_EXTERNAL_PATH , fileName);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            saved = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return saved;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length > 0 && grantResults.length > 0){
            switch (requestCode){
                case CODE_PERMISSION_WRITEFILE:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        boolean saved = saveBitMapAsFile();
                        if (saved){
                            Toast.makeText(this,"保存完成",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"未获取到权限不能保存为图片",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:break;
            }
        }
    }
}
