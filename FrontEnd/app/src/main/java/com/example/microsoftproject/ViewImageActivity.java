package com.example.microsoftproject;

import static com.example.microsoftproject.HomePage.PHONE_KEY;
import static com.example.microsoftproject.HomePage.SHARED_PREF_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.microsoftproject.service.NetworkServiceClass;


public class ViewImageActivity extends AppCompatActivity {

    ImageView imageView;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        imageView = findViewById(R.id.show_image_id);

        String res ;
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        downloadImage();

    }

    public void downloadImage(){
            String phone = getPhoneNo(PHONE_KEY);
            String imageName = getIntent().getStringExtra("name");

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest request = new StringRequest(Request.Method.GET, NetworkServiceClass.URL_BASE + "/user/"+phone+"/"+imageName,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.d("mytag" ,response);
                            byte[] decodeImage  = Base64.decode(response , Base64.DEFAULT);

                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);

                            imageView.setImageBitmap(decodedByte);
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error != null)
                                Log.d("error tag" , error.toString());
                            else
                                Log.d("myTag" , "null error");
                        }
                    }){

            };

            requestQueue.add(request);
        }

    String getPhoneNo (String key){
        String phoneNo = sharedPreferences.getString(key, "");
        return phoneNo;
    }

}

