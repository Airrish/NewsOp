package com.example.newsopdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements clickMe{

    RecyclerView recyclerView;
    ArrayList<Map<String,String>> arr = new ArrayList<>();
    String str = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
    adapter a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        loadApi();
        a=new adapter(MainActivity.this,arr,MainActivity.this);

        recyclerView.setAdapter(a);
    }

    void loadApi(){
        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for(int i = 0; i < jsonArray.length() ; i++) {

                                JSONObject j = jsonArray.getJSONObject(i);

                                String ti = j.getString("title");
                                String des = j.getString("description");
                                String ur = j.getString("url");
                                String uI = j.getString("urlToImage");

                                Map<String,String> mp = new HashMap<>();
                                mp.put("title",ti);
                                mp.put("des",des);
                                mp.put("url",ur);
                                mp.put("urlToImage",uI);

                                arr.add(mp);
                            }
                            a.notifyDataSetChanged();
                        } catch (Exception e){
                            Toast.makeText(MainActivity.this,"Erroe",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please Connect to Internet",Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onClickMe(String s) {
        CustomTabsIntent.Builder b = new CustomTabsIntent.Builder();
        CustomTabsIntent it = b.build();
        it.launchUrl(MainActivity.this, Uri.parse(s));
    }
}