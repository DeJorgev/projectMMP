package com.mmp.musemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;

import java.util.ArrayList;
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

        TimerTask tTask = new TimerTask() {
            @Override
            public void run() {
                if (EasyPermissions.hasPermissions(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    SongFetcher fetcher = new SongFetcher(SplashScreen.this);
                    deviceSongs.addAll(fetcher.manageSongsFetch());
                    deviceAlbums.addAll(fetcher.getAlbums());
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("songs",  deviceSongs);
                    intent.putParcelableArrayListExtra("albums", deviceAlbums);
                    startActivity(intent);
                }
            }
        };
    }
}
