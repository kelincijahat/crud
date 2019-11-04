package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
RequestQueue requestQueue;
private String TAMP_URL = "http://192.168.43.195:8080/mbii_p1/list_icon.php";
private TextView txtresult;
Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtresult=(TextView) findViewById(R.id.tampil_nmp);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                TAMP_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    //mengambil json array
                    JSONArray json = response.getJSONArray("result");
                    for (int i=0; i<json.length(); i++) {
                        JSONObject info = json.getJSONObject(i);
                        //Mengambil data json dari database dengan field apa
                        String npm = info.getString("npm");
                       String nama = info.getString("nama");
                       String alamat = info.getString("alamat");

                        txtresult.append("\n npm: " + npm + "\n" +
                                "\n nama: " + nama + "\n" +
                                "\n alamat: " + alamat + "\n");


                    }
                    txtresult.append("====\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
    });
        requestQueue.add(jsonObjectRequest);
    }
}
