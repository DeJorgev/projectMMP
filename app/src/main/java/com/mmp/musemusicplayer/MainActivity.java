package com.mmp.musemusicplayer;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.material.tabs.TabLayout;
import com.mmp.musemusicplayer.Fragments.SectionsPagerAdapter;
import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;
import com.mmp.musemusicplayer.databinding.ActivityMainBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button next;
    private Button prev;

    private static List<Song> deviceSongs;
    public static List<Song> getDeviceSongs() {
        return deviceSongs;
    }

    private static List<Album> deviceAlbums;
    public static List<Album> getDeviceAlbums() {
        return deviceAlbums;
    }

    private static ExoPlayer player;
    public static ExoPlayer getExoPlayer() {
        return player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(MainActivity.this, "Requesting permission to access storage", 102, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        SongFetcher fetcher = new SongFetcher(MainActivity.this);
        deviceSongs = fetcher.manageSongsFetch();
        deviceAlbums = fetcher.getAlbums();
        player = new ExoPlayer.Builder(MainActivity.this).build();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        prev = findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekToPrevious();
            }
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekToNext();
            }
        });


    }

    public void showtoast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Various
    //Overrides using EasyPermissions library for a simpler code. By Google
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}