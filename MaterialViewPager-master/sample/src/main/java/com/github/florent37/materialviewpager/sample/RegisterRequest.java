package com.github.florent37.materialviewpager.sample;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://211.253.27.67/Register1.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, int age, String password, String aduino, String social,String sex, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("age", age + "");
        params.put("username", username);
        params.put("password", password);
        params.put("aduino",aduino);
        params.put("social",social);
        params.put("sex",sex);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

