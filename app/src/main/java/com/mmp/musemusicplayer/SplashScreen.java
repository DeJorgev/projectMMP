package com.mmp.musemusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

public class SplashScreen extends AppCompatActivity {


    private static ArrayList<Song> deviceSongs;
    private static ArrayList<Album> deviceAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!EasyPermissions.hasPermissions(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(SplashScreen.this, "Requesting permission to access storage", 102, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        Log.e("Hasta aqui","-");
        if (EasyPermissions.hasPermissions(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            SongFetcher fetcher = new SongFetcher(SplashScreen.this);
            deviceSongs.addAll(fetcher.manageSongsFetch());
            deviceAlbums.addAll(fetcher.getAlbums());
            Log.e("Hasta aqui","-");
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putParcelableArrayListExtra("songs",  deviceSongs);
            intent.putParcelableArrayListExtra("albums", deviceAlbums);
            Log.e("Hasta aqui","-");
            startActivity(intent);
        }
    }

}
