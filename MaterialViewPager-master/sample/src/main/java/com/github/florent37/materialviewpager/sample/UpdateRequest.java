package com.github.florent37.materialviewpager.sample;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://211.253.27.67/UserUpdate.php";
    private Map<String, String> params;

    public UpdateRequest(String username, String name,String sex, int age, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("name", name);
        params.put("sex", sex);
        params.put("age", age+ "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
