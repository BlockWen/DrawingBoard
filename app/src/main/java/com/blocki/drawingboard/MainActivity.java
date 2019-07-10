package com.blocki.drawingboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button touchPathBtn;
    private Button bezierPathBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchPathBtn = findViewById(R.id.main_touchPathView);
        bezierPathBtn = findViewById(R.id.main_bezierPathView);

        touchPathBtn.setOnClickListener(this);
        bezierPathBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_touchPathView:
                Intent intent = new Intent(this,TouchPathActivity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }

}
