package com.example.alienware.selectionofdormitory;

/**
 * Created by alienware on 2018/1/9.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.alienware.selectionofdormitory.Util.SSLUtil;
import com.example.alienware.selectionofdormitory.bean.StudentInfo;


import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login  extends Activity implements View.OnClickListener{
    private EditText usernameTV;   //定义控件
    private EditText passwordTV;
    private Button log;


    //private ConfigUtil configUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);//加载布局
        //configUtil = ConfigUtil.getConfigUtil(this);
        initView();//调用一个方法
    }
    //获取数据的方法
    void initView() {
        usernameTV = (EditText) findViewById(R.id.usernameTV);//拿到布局的控件，并强制类型转换
        passwordTV = (EditText) findViewById(R.id.passwordTV);
        log = (Button) findViewById(R.id.log);
        log.setOnClickListener(this);

    }

    //添加OnClick事件
    @Override
    public void onClick(View view) {

        //拿到学号,密码的值
        final String username = usernameTV.getText().toString().trim();
        final String password = passwordTV.getText().toString().trim();
        //进行输入验证,如果没有输入的话进行提示
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username.trim()) || TextUtils.isEmpty(password.trim())){
            Toast.makeText(this,"用户名或者密码不能为空",Toast.LENGTH_LONG).show();
        }else {
            //调用get请求的方法
            ByGet(username);
            loginByGet(usernameTV.getText().toString(),passwordTV.getText().toString());
        }

    }


    public void loginByGet(String username, String password) {

        //if (username.length() == 0 || password.length() == 0) {
        //  NetUtil.showToast(activity_login.this, "请输入账户或密码");
        //  return;
        // }
        String data = "username=" + username + "&password=" + password;
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?"+ data;
        Log.d("Login", ip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(ip);
                    //信任所有证书
                    if("https".equalsIgnoreCase(url.getProtocol())){
                        SSLUtil.ignoreSsl();
                    }
                    //打开链接
                    conn = (HttpURLConnection) url.openConnection();
                    //GET请求
                    conn.setRequestMethod("GET");
                    //设置属性
                    conn.setReadTimeout(8000);//读取数据超时时间
                    conn.setConnectTimeout(8000);//连接的超时时间

                    InputStream is = conn.getInputStream();//字节流转换成字符串
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String str;

                    while ((str = reader.readLine()) != null) {
                        response.append(str + "\n");
                        Log.d("Login",str);
                    }
                    //获取字符串
                    String responseStr = response.toString();
                    Log.d("Login",responseStr );
                    //解析JSON格式
                    getJSON(responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();

    }

    /**
     * JSON解析方法
     * @param Jsondata
     */
    private void getJSON(String Jsondata){
        final String username = usernameTV.getText().toString();
        try{

            JSONObject a = new JSONObject(Jsondata);
            System.out.println(a.get("errcode"));
            String code = a.getString("errcode");
            if(code.equals("0")){//请求成功

                String stu = usernameTV.getText().toString();
                ((StudentInfo)getApplication()).setStudentid(stu);//共享
                int i = Integer.parseInt(stu);

                if (i % 2 == 1) {
                    Intent intent = new Intent(this, StudentInfoGet.class);//页面跳转
                    intent.putExtra("username", username);
                    startActivity(intent);//加载页面
                    this.finish();//关闭此页面
                }else{
                    Intent intent = new Intent(this, Result.class);//页面跳转
                    intent.putExtra("username", username);


                    startActivity(intent);//加载页面
                    this.finish();//关闭此页面
                }
            }else if (code.equals("40001")){//用户名或者密码错误
                Looper.prepare();
                Toast.makeText(Login.this, "学号不存在" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else if (code.equals("40002")){
                Looper.prepare();
                Toast.makeText(Login.this, "密码错误" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else if(code.equals("40009")){
                Looper.prepare();
                Toast.makeText(Login.this, "参数错误" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else {
                Looper.prepare();
                Toast.makeText(Login.this, "请求失败！" , Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ByGet(String student) {

        String data = "stuid=" + student;
        final String ip = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?" + data;
        Log.d("Login", ip);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(ip);
                    //信任所有证书
                    if ("https".equalsIgnoreCase(url.getProtocol())) {
                        SSLUtil.ignoreSsl();
                    }
                    //打开链接
                    conn = (HttpURLConnection) url.openConnection();
                    //GET请求
                    conn.setRequestMethod("GET");
                    //设置属性
                    conn.setReadTimeout(8000);//读取数据超时时间
                    conn.setConnectTimeout(8000);//连接的超时时间

                    InputStream is = conn.getInputStream();//字节流转换成字符串
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str + "\n");
                        Log.d("Login", str);
                    }
                    //获取字符串
                    String responseStr = response.toString();
                    Log.d("Login", responseStr);
                    //解析JSON格式
                    JSON(responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }

                }
            }
        }).start();

    }

    /**
     * JSON解析方法
     *
     * @param stuid
     */
    private void JSON(String stuid) {
        try {
            //字符串转换为JSONObject对象
            JSONObject a = new JSONObject(stuid);
            String code = a.getString("errcode");
            if (code != "0") {//请求成功
                //从jsonObject对象中取出来key是data的对象
                JSONObject data = a.getJSONObject("data");
                if (data != null) {
                    //从data对象里取出
                    String stu = data.getString("studentid");
                    ((StudentInfo)getApplication()).setStudentid(stu);
                    String name = data.getString("name");
                    ((StudentInfo)getApplication()).setName(name);
                    String gender = data.getString("gender");
                    ((StudentInfo)getApplication()).setGender(gender);
                    String vcode = data.getString("vcode");
                    ((StudentInfo)getApplication()).setVcode(vcode);
                    ((StudentInfo)getApplication()).setBuildnum("5");

                } else {
                    Looper.prepare();
                    Toast.makeText(this, "请求失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
