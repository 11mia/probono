package com.github.florent37.materialviewpager.sample;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapRequest extends StringRequest {
    private static final String GPS_REQUEST_URL = "http://211.253.27.67/GetgpsInfo.php";
    private Map<String, String> params;

    public MapRequest(Response.Listener<String> listener){
        super(Method.POST, GPS_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
