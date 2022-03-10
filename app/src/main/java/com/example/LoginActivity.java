package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText accountText;
    private EditText passwordText;
    private Button loginButton;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        accountText = findViewById(R.id.account);
        passwordText =findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        rememberPass = findViewById(R.id.remember_pass);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountText.getText().toString();
                String password = passwordText.getText().toString();
                if ("admin".equals(account) && "123456".equals(password)){
                    editor = preferences.edit();
                    if (rememberPass.isChecked()){ // 检查复选框是否被选中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply(); // 提交数据

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember){ // 将账号和密码设置到文本框中
            String account = preferences.getString("account", "");
            String password = preferences.getString("password", "");
            accountText.setText(account);
            passwordText.setText(password);
            rememberPass.setChecked(true);
        }
    }
}