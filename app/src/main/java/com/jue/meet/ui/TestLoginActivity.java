package com.jue.meet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jue.meet.MainActivity;
import com.jue.meet.R;
import com.jue.framework.base.BaseBackActivity;
import com.jue.framework.bmob.BmobManager;
import com.jue.framework.bmob.IMUser;
import com.jue.framework.helper.ActivityHelper;
import com.jue.framework.manager.KeyWordManager;
import com.jue.framework.utils.LogUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * FileName: TestLoginActivity
 * Founder: Jue
 * Profile: 测试登录页面
 */
public class TestLoginActivity extends BaseBackActivity implements View.OnClickListener {

    private EditText et_phone;
    private EditText et_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        initView();
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, getString(R.string.text_login_phone_null), Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, getString(R.string.text_login_pw_null), Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobManager.getInstance().loginByAccount(phone, password, new SaveListener<IMUser>() {
                    @Override
                    public void done(IMUser imUser, BmobException e) {
                        KeyWordManager.getInstance().hideKeyWord(TestLoginActivity.this);
                        if (e == null) {
                            //登陆成功
                            startActivity(new Intent(
                                    TestLoginActivity.this, MainActivity.class));
                            ActivityHelper.getInstance().exit();
                            finish();
                        } else {
                            LogUtils.e("Login Error:" + e.toString());
                            if (e.getErrorCode() == 101) {
                                Toast.makeText(TestLoginActivity.this, getString(R.string.text_test_login_fail), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
        }
    }
}
