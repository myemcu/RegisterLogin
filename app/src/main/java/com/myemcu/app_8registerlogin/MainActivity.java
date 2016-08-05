/***************************************************************************************************
* 编程规划：
*           1. 注册键要完成的事（1.相关输入信息的处理， 2.将输入信息传递到结果的Activity。）
*           2. 所在地要完成的事（1.启动选择型Activity，2.将该Activity中的用户选择回传到主活动的对应EditTxt）
***************************************************************************************************/

/*
* 编程模型：
* */

package com.myemcu.app_8registerlogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPsd;
    private EditText mPsd2;
    private RadioButton mMale;
    private RadioButton mFemale;
    private Button mCityBtn;
    private EditText mCity;
    private Button mRBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();    // 抽取控件对象

        // 注册键处理
        mRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkResult = checkInfo(); // 读取用户输入信息的结果(1.结果异常，2.结果正常)

                if (checkResult != null) { // 用户输入异常(弹出对话框)
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); // 对话框
                    builder.setTitle("出错提示");       // 标题栏
                    builder.setMessage(checkResult);   // 内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { // 清空密码
                            mPsd.setText("");
                            mPsd2.setText("");
                        }
                    });
                    builder.create().show();
                }
                else { //用户输入正常
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class); // 创建意图

                    intent.putExtra("name", mName.getText().toString());
                    intent.putExtra("psd",mPsd.getText().toString());
                    String sexbie = mMale.isChecked()?"男":"女";
                    intent.putExtra("sexbie",sexbie);
                    intent.putExtra("city",mCity.getText().toString());

                    startActivity(intent);
                }
            }
        });

        // 所在地键处理
        mCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(MainActivity.this, CityChooseActivity.class);
                startActivityForResult(intent, 0); // 请求码0，代表为所在地键请求
            }
        });
    }

    //Ctrl+O方法重写(位于onCreate()之外)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //  所在地键的请求码
        if (requestCode==0 && resultCode==0) {              // 请求码，结果码全0
            Bundle bundle = data.getExtras();               // 读取Intent数据包
            String resultCity = bundle.getString("city");   // 读取数据包中标识为city的数据
            mCity.setText(resultCity);
        }
    }

    private String checkInfo() { // 输入信息处理(必须返回值)

        // 用户名异常验证
        if (mName.getText().toString()==null || mName.getText().toString().equals(""))  //空操作或为空字符
            return "用户名不能为空";

        else if (mPsd.getText().toString().trim().length()<6 || mPsd.getText().toString().trim().length()>12)
            return "密码位数在6~12位之间"; // trim()为去字符串的两头空格

        else if //(mPsd.getText().toString() != mPsd2.getText().toString())
                (! mPsd.getText().toString().equals(mPsd2.getText().toString()))
            return "两次输入的密码不一致";
        else
            return null;    // 输入正常,返回null
    }

    private void findViews() {
        //对象名可与控件Id一致。
        mName = (EditText) findViewById(R.id.name);
        mPsd = (EditText) findViewById(R.id.psd);
        mPsd2 = (EditText) findViewById(R.id.psd2);

        mMale = (RadioButton) findViewById(R.id.male);
        mFemale = (RadioButton) findViewById(R.id.female);

        mCityBtn = (Button) findViewById(R.id.cityBtn);
        mCity = (EditText) findViewById(R.id.city);

        mRBtn = (Button)   findViewById(R.id.rBtn);
    }
}
