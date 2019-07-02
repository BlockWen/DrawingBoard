package com.blocki.drawingboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TouchPathActivity extends AppCompatActivity implements View.OnClickListener{

    private Button resetBtn;
    private TouchPathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_path);

        resetBtn = findViewById(R.id.touchPath_reset_Btn);
        pathView = findViewById(R.id.touchPath_pathView);
        resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touchPath_reset_Btn:
                pathView.reset();
                break;
            default:break;
        }
    }
}
