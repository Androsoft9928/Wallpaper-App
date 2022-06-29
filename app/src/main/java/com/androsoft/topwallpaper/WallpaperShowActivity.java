package com.androsoft.topwallpaper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

public class WallpaperShowActivity extends AppCompatActivity {

    ImageView wallpaperShowImg;
    AppCompatButton wallpaperApplyBtn;

    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_show);

        wallpaperShowImg = findViewById(R.id.wallpaperShowImg);
        wallpaperApplyBtn = findViewById(R.id.wallpaperApplyBtn);
        imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(wallpaperShowImg);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        wallpaperApplyBtn.setOnClickListener(v -> {
            Glide.with(WallpaperShowActivity.this).asBitmap().load(imageUrl).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Toast.makeText(WallpaperShowActivity.this, "Failed to load wallpaper", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    try {
                        wallpaperManager.setBitmap(resource);
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(WallpaperShowActivity.this, "Failed to Set wallpaper", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            }). submit();

            Toast.makeText(WallpaperShowActivity.this, "Set to HomeScreen", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(WallpaperShowActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

    }
}