package com.example.alienware.selectionofdormitory;

/**
 * Created by alienware on 2018/1/9.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alienware.selectionofdormitory.bean.StudentInfo;



public class Result  extends Activity implements View.OnClickListener{
    private Button btn;
    private TextView nu,nam,genderTV,buildnum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);//加载布局
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setContentView(R.layout.mytext);
        nu = (TextView)findViewById(R.id.myxuehao);
        nam =(TextView)findViewById(R.id.myname);
        genderTV = (TextView)findViewById(R.id.mygender);
        buildnum = (TextView)findViewById(R.id.building_num);
        String xh = ((StudentInfo)getApplication()).getStudentid();
        String name = ((StudentInfo)getApplication()).getName();
        String gender = ((StudentInfo) getApplication()).getGender();
        String  build= ((StudentInfo)getApplication()).getBuildnum();
        nu.setText(xh);
        nam.setText(name);
        genderTV.setText(gender);
        buildnum.setText(build);


    }
}
