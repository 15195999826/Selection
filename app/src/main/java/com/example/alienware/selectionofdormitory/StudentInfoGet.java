package com.example.alienware.selectionofdormitory;

/**
 * Created by alienware on 2018/1/9.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.example.alienware.selectionofdormitory.Util.SSLUtil;
import com.example.alienware.selectionofdormitory.bean.StudentInfo;

//import com.pku.dormitory.people.person_one;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class StudentInfoGet  extends Activity implements View.OnClickListener{
    private TextView nam;   //定义控件
    private TextView nu;
    private TextView genderTV;
    private TextView codeTV;
    private Button btnTV;
    private TextView xhTv;
    //public static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);//加载布局
        Intent intent = this.getIntent();
        String username = intent.getStringExtra("username");


        nam = (TextView) findViewById(R.id.nm);//拿到布局的控件，并强制类型转换
        nu = (TextView) findViewById(R.id.stu);
        genderTV = (TextView) findViewById(R.id.gd);
        codeTV = (TextView) findViewById(R.id.vc);
        btnTV = (Button) findViewById(R.id.button1);

        String xh = ((StudentInfo) getApplication()).getStudentid();
        String name = ((StudentInfo) getApplication()).getName();
        String gender = ((StudentInfo) getApplication()).getGender();
        String vcode = ((StudentInfo) getApplication()).getVcode();
        nu.setText(xh);
        nam.setText(name);
        genderTV.setText(gender);
        codeTV.setText(vcode);
        //loginByGet(username);


        btnTV.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SelectRoom.class);//页面跳转
        String gender=this.genderTV.getText().toString();
        intent.putExtra("gender",gender);
        startActivity(intent);//加载页面
        this.finish();//关闭此页面
    }
}
