package com.example.microsoftproject.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.microsoftproject.HomePage;
import com.example.microsoftproject.entity.ImageEntity;
import com.example.microsoftproject.entity.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkServiceClass {



    public static final String URL_BASE = "https://app-drovo-211214153322.azurewebsites.net";

    public void userRegister(Context context , Person person){

        RequestQueue requestQueue = Volley.newRequestQueue(context );

        StringRequest request = new StringRequest(Request.Method.POST, URL_BASE + "/user/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "success" + response, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context , HomePage.class);
                        intent.putExtra(HomePage.PHONE_KEY , String.valueOf(person.getPhoneNo()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null)
                        Log.d("myTag" , error.toString());
                        else
                        Log.d("myTag" , "null error");
                        Toast.makeText(context, "Error : something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject object = new JSONObject();
                try {
                    object.put("name" , person.getName());
                    object.put("phoneNo" , String.valueOf(person.getPhoneNo()));
                    object.put("password" , person.getPassword());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return object.toString().getBytes(StandardCharsets.UTF_8);
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        Log.d("myTag" , "ends here");

        requestQueue.add(request);
    }

    public void login(Context context , Person person){
        RequestQueue requestQueue = Volley.newRequestQueue(context );

        StringRequest request = new StringRequest(Request.Method.POST, URL_BASE + "/user/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true"))
                        {
                            Intent intent = new Intent(context , HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(HomePage.PHONE_KEY , String.valueOf(person.getPhoneNo()));
                            context.startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null)
                            Log.d("myTag" , error.toString());
                        else
                            Log.d("myTag" , "null error");
                        Toast.makeText(context, "Error : something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject object = new JSONObject();
                try {
                    object.put("phoneNo" , String.valueOf(person.getPhoneNo()));
                    object.put("password" , person.getPassword());
                }
                catch (Exception e){

                }
                return object.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        requestQueue.add(request);
    }

    public void uploadImage(Context context , String phone , String encodedImage , String name){

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, URL_BASE + "/user/"+phone+"/upload",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.trim();
                        Toast.makeText(context , s , Toast.LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null)
                            Log.d("myTag" , error.toString());
                        else
                            Log.d("myTag" , "null error");
                        Toast.makeText(context, "Error : something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }){


            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String , String> params = new HashMap<String , String>();
                params.put("file", encodedImage);
                params.put("name" , name);
                return params;
            }

            @Override
            protected String getParamsEncoding() {
                return super.getParamsEncoding();
            }
        };

        requestQueue.add(request);
    }

    public void deleteImage(Context context , String phone , String ImageName){

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, URL_BASE + "/user/"+phone+"/"+ImageName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context , "image deleted" , Toast.LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null)
                            Log.d("myTag" , error.toString());
                        else
                            Log.d("myTag" , "null error");
                        Toast.makeText(context, "Error : something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }){

        };
        requestQueue.add(request);
    }



}
