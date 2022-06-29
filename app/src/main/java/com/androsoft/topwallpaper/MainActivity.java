package com.androsoft.topwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androsoft.topwallpaper.Adapter.AndroCategoryRVAdapter;
import com.androsoft.topwallpaper.Adapter.AndroWallpaperRVAdapter;
import com.androsoft.topwallpaper.Model.AndroCategoryRVModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AndroCategoryRVAdapter.AndroCategoryClickInterface {

    SearchView andro_search_logo;
    RecyclerView andro_image_wallpaper_RV, andro_image_category_rv;
    ProgressBar andro_progressBar;

    ArrayList<String> androWallpaperRVModelArrayList;
    ArrayList<AndroCategoryRVModel> androCategoryRVModelArrayList;
    AndroWallpaperRVAdapter androWallpaperRVAdapter;
    AndroCategoryRVAdapter androCategoryRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        andro_search_logo = findViewById(R.id.andro_search_logo);

        andro_image_wallpaper_RV = findViewById(R.id.andro_image_wallpaper_RV);
        andro_image_category_rv = findViewById(R.id.andro_image_category_rv);

        andro_progressBar = findViewById(R.id.andro_progressBar);

        androWallpaperRVModelArrayList = new ArrayList<>();
        androCategoryRVModelArrayList = new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        andro_image_wallpaper_RV.setLayoutManager(gridLayoutManager);
        androWallpaperRVAdapter = new AndroWallpaperRVAdapter(this, androWallpaperRVModelArrayList);
        andro_image_wallpaper_RV.setAdapter(androWallpaperRVAdapter);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        andro_image_category_rv.setLayoutManager(linearLayoutManager);
        androCategoryRVAdapter = new AndroCategoryRVAdapter(this, androCategoryRVModelArrayList,this::onAndroCatagoryClick);
        andro_image_category_rv.setAdapter(androCategoryRVAdapter);
        
        
        
        getAndroCategoryes();

        getAndroWallpapers();


        andro_search_logo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchLogoString = andro_search_logo.getQuery().toString();
                if (searchLogoString.isEmpty())
            {
                Toast.makeText(MainActivity.this, "Please search now", Toast.LENGTH_SHORT).show();
            } else
            {
                getWallpaperShowByCategory(searchLogoString);
            }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void getWallpaperShowByCategory(String userCategory) {
        androWallpaperRVModelArrayList.clear();
        andro_progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query=" + userCategory + "&per_page=80&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                andro_progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonPhotoArray = response.getJSONArray("photos");
                    for (int i=0; i<jsonPhotoArray.length(); i++ )
                    {
                        JSONObject jsonPhotoObject = jsonPhotoArray.getJSONObject(i);
                        String imgJsonUrl = jsonPhotoObject.getJSONObject("src").getString("portrait");
                        androWallpaperRVModelArrayList.add(imgJsonUrl);
                    }
                    androWallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headersID = new HashMap<>();
                headersID.put("Authorization", "563492ad6f91700001000001c30c35cb9dc443ec98a88486eb6da16d");
                return headersID;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

    //-------->>
    private void getAndroWallpapers() {
        androWallpaperRVModelArrayList.clear();
        andro_progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=80&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                andro_progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonPhotoArray = response.getJSONArray("photos");
                    for (int i=0; i<jsonPhotoArray.length(); i++ )
                    {
                        JSONObject jsonPhotoObject = jsonPhotoArray.getJSONObject(i);
                        String imgJsonUrl = jsonPhotoObject.getJSONObject("src").getString("portrait");
                        androWallpaperRVModelArrayList.add(imgJsonUrl);
                    }
                    androWallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headersID = new HashMap<>();
                headersID.put("Authorization", "563492ad6f91700001000001c30c35cb9dc443ec98a88486eb6da16d");
                return headersID;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

    private void getAndroCategoryes() {
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Nature", "https://images.pexels.com/photos/15286/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Animation", "https://images.pexels.com/photos/9669042/pexels-photo-9669042.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Car", "https://images.pexels.com/photos/1149137/pexels-photo-1149137.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Music", "https://images.pexels.com/photos/1105666/pexels-photo-1105666.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Gaming", "https://images.pexels.com/photos/275033/pexels-photo-275033.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Flowers", "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Books", "https://images.pexels.com/photos/590493/pexels-photo-590493.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Programming", "https://images.pexels.com/photos/2653362/pexels-photo-2653362.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Technology", "https://images.pexels.com/photos/2007647/pexels-photo-2007647.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVModelArrayList.add(new AndroCategoryRVModel("Architecture", "https://images.pexels.com/photos/3697742/pexels-photo-3697742.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        androCategoryRVAdapter.notifyDataSetChanged();


    }


    @Override
    public void onAndroCatagoryClick(int position) {
        String userCategory = androCategoryRVModelArrayList.get(position).getCategoryName();
        getWallpaperShowByCategory(userCategory);

    }
}