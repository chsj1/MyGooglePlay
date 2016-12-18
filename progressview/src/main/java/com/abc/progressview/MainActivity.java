package com.abc.progressview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
        progressView.setIcon(R.drawable.ic_pause);

        //如果一个控件,继承ViewGroup,要走到onDraw方法,需要设置一个背景
        progressView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressView.setMax(100);
        progressView.setProgress(50);
        progressView.setNote("50%");
    }
}
