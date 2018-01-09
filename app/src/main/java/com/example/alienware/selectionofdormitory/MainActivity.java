package com.example.alienware.selectionofdormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by alienware on 2017/10/31.
 */

public class MainActivity extends Activity implements View.OnClickListener{
    private ImageView welcome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        welcome = (ImageView)findViewById(R.id.welcome);
        welcome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Login.class);//页面跳转
        startActivity(intent);//加载页面
        this.finish();//关闭此页面

    }
}