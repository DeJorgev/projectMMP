package com.mmp.musemusicplayer;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mmp.musemusicplayer.Fragments.SectionsPagerAdapter;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;
import com.mmp.musemusicplayer.databinding.ActivityMainBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private static List<Song> deviceSongs;

    public static List<Song> getDeviceSongs() {
        return deviceSongs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(MainActivity.this, "Requesting permission to access storage", 102, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        deviceSongs = new SongFetcher(MainActivity.this).manageSongsFetch();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
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