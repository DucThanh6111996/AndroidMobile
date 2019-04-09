package com.mta.studyenglish.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mta.studyenglish.R;
import com.mta.studyenglish.app.AppController;
import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.helper.PrefManager;
import com.mta.studyenglish.model.UserItem;

/**
 *
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefManager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin    = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_forgot_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tính năng chưa hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Đăng nhập ...");
        pDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);

                Message msg = new Message();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (checkLogin(username, password)) {
                    msg.arg1 = 1;
                    Bundle b = new Bundle();
                    b.putString("username", username);
                    msg.setData(b);
                } else {
                    msg.arg1 = 0;
                }

                msg.setTarget(mHandler);
                msg.sendToTarget();
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {

                PrefManager.setLogin(true);
                PrefManager.setUsername(msg.getData().getString("username"));

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Tài khoản đăng nhập không đúng!", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    };

    private boolean checkLogin(String username, String password) {
        MySqliteHelper sqliteHelper = AppController.getSqlitHelper();
        UserItem userItem = sqliteHelper.findUserLogin(username, password);

        return userItem != null;
    }
}
