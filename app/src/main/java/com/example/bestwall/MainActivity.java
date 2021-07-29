package com.example.bestwall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

     private EditText searchedit;
     private ImageView seachicon;
     private RecyclerView categoryRV, wallpaperRV;
     private ProgressBar loadingPB;
     private ArrayList<String> wallpaperArrayList;
     private ArrayList<CategoryModal> categoryModalArrayList;
     private CategoryRVAdapter categoryRVAdapter;
     private WallpaperRVadapter wallpaperRVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchedit=findViewById(R.id.idEditsearch);
        seachicon=findViewById(R.id.idImageIcon);
        categoryRV=findViewById(R.id.idRVCategory);
        wallpaperRV=findViewById(R.id.idRVWallpapers);
        loadingPB=findViewById(R.id.idPBLoading);
        wallpaperArrayList= new ArrayList<>();
        categoryModalArrayList= new ArrayList<>();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdapter= new CategoryRVAdapter(categoryModalArrayList,this,this);
        categoryRV.setAdapter(categoryRVAdapter);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVadapter = new WallpaperRVadapter(wallpaperArrayList,this);
        wallpaperRV.setAdapter(wallpaperRVadapter);
        
        
        getCategories();
        
        getWallpapers();
        seachicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchstr= searchedit.getText().toString();
                if(searchstr.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Search for Wallpaper category", Toast.LENGTH_SHORT).show();
                    
                }else
                {
                    getWallpapersByCategory(searchstr);
                    
                }
            }
        });
        
    }

    private void getWallpapersByCategory(String searchstr) {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/search?query="+ searchstr + "&per_page=30&page=1";
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//              JSONArray photoArray = null;
              try {
               JSONArray  photoArray=response.getJSONArray("photos");
                  for(int i=0;i<photoArray.length();i++)
                  {
                      JSONObject photoobj = photoArray.getJSONObject(i);
                      String url= photoobj.getJSONObject("src").getString("portrait");
                      wallpaperArrayList.add(url);

                  }
wallpaperRVadapter.notifyDataSetChanged();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Fail to find wallpaper!!", Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getHeaders()throws AuthFailureError{
                HashMap<String, String> headers= new HashMap<>();
                headers.put("Authorization", "563492ad6f917000010000019edf190c6b15409cb3e29846afca0442");
                return headers;

            }
        };
        requestQueue.add(jsonObjectRequest);
        loadingPB.setVisibility(View.GONE);
    }

    private void getWallpapers() {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String imgurl= photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgurl);
                    }
                    wallpaperRVadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Fail to find wallpaper!!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "563492ad6f917000010000019edf190c6b15409cb3e29846afca0442");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    private void getCategories() {
        categoryModalArrayList.add(new CategoryModal("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModalArrayList.add(new CategoryModal("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
            categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryModalArrayList.get(position).getCategory();
        getWallpapersByCategory(category);
    }

}