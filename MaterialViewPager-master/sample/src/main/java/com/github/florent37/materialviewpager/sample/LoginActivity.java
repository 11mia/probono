package com.github.florent37.materialviewpager.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    CheckBox autoLogin;
    Boolean loginChecked=false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);
        pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        autoLogin = (CheckBox) findViewById(R.id.chk_auto);

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.hideSoftInputFromWindow(etPassword.getWindowToken(),0);

                if (isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    editor.clear();
                    editor.commit();
                }

            }
        });


        // if autoLogin checked, get input
        if (pref.getBoolean("autoLogin", false)) {
            username = (pref.getString("id", ""));
            password = (pref.getString("pw", ""));
            autoLogin.setChecked(true);
            // goto mainActivity
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            Log.v("LoginActivity", "LoginSuccess");
                            String name = jsonResponse.getString("name");
                            int age = jsonResponse.getInt("age");
                            String username = jsonResponse.getString("username");
                            String aduino_id = jsonResponse.getString("aduino_id");
                            String sex = jsonResponse.getString("sex");
                            Variable variable = (Variable) getApplication();
                            variable.setName(name);
                            variable.setUsername(username);
                            variable.setAge(age);
                            variable.setAduino_id(aduino_id);
                            variable.setSex(sex);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            Toast toast = new Toast(LoginActivity.this);
                            toast.makeText(LoginActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.v("LoginActivity", "LoginFailed");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("올바른 아이디, 비밀번호를 입력하세요.")
                                    .setTitle("로그인 실패")
                                    .setNegativeButton("확인", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        } else {


            tvRegisterLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
            });

            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();
                    if (username.equals("") || password.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("빈 칸이 존재합니다.")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();
                        return;
                    }
                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    if (loginChecked) {
                                        // if autoLogin Checked, save values
                                        editor.putString("id", username);
                                        editor.putString("pw", password);
                                        editor.putBoolean("autoLogin", true);
                                        editor.commit();
                                    }


                                    Log.v("LoginActivity", "LoginSuccess");
                                    String name = jsonResponse.getString("name");
                                    int age = jsonResponse.getInt("age");
                                    String username = jsonResponse.getString("username");
                                    String aduino_id = jsonResponse.getString("aduino_id");
                                    String sex = jsonResponse.getString("sex");
                                    Variable variable = (Variable) getApplication();
                                    variable.setName(name);
                                    variable.setUsername(username);
                                    variable.setAge(age);
                                    variable.setAduino_id(aduino_id);
                                    variable.setSex(sex);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    Toast toast = new Toast(LoginActivity.this);
                                    toast.makeText(LoginActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();

                                    finish();
                                } else {
                                    Log.v("LoginActivity", "LoginFailed");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("올바른 아이디, 비밀번호를 입력하세요.")
                                            .setTitle("로그인 실패")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            });
        }
    }
}
