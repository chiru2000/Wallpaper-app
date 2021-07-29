package com.example.bestwall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class
WallpaperActivity extends AppCompatActivity {

     ImageView wallpaperIV;
     Button setBtnWallpaper;
     String url;
     private ProgressBar loadingwallpaperPB;
    WallpaperManager wallpaperManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        setBtnWallpaper = findViewById(R.id.idbtnsetWallpaper);
        loadingwallpaperPB = findViewById(R.id.idWallpaperPB);
        url = getIntent().getStringExtra("imgUrl");
//        Toast.makeText(getApplicationContext(), (CharSequence) url , Toast.LENGTH_SHORT).show();
        if (url != null) {
            wallpaperIV = findViewById(R.id.idIVwallpaper);
            Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    loadingwallpaperPB.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    loadingwallpaperPB.setVisibility(View.GONE);
                    return false;
                }
            }).into(wallpaperIV);
            wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            setBtnWallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(WallpaperActivity.this).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Toast.makeText(WallpaperActivity.this, "Fail to load!!", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            try {
                                wallpaperManager.setBitmap(resource);
                                Toast.makeText(WallpaperActivity.this, "Wallpaper set to homescreen", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(WallpaperActivity.this, "Fail to load", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            return false;
                        }
                    }).submit();
                    FancyToast.makeText(WallpaperActivity.this, "Wallpaper set to homeScreen", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            });

        }

    }

}