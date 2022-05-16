package com.mmp.musemusicplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.Player;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mmp.musemusicplayer.Fragments.TabsFragment;
import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;
import com.mmp.musemusicplayer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button next;
    private Button prev;
    private TextView[] titulos;
    private BottomSheetBehavior bottomSheetBehavior;

    private static List<Song> deviceSongs;
    public static List<Song> getDeviceSongs() {
        return deviceSongs;
    }

    private static boolean playing = false;
    public static boolean isPlaying() {
        return playing;
    }
    public static void setPlaying(boolean playing) {
        MainActivity.playing = playing;
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
        Log.e("Main activity","1");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.e("Main activity","2");

        if (!EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(MainActivity.this, "Requesting permission to access storage", 102, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        SongFetcher fetcher = new SongFetcher(MainActivity.this);
        deviceSongs = fetcher.manageSongsFetch();
        deviceAlbums = fetcher.getAlbums();

        player = new ExoPlayer.Builder(this).build();

        titulos = new TextView[]{findViewById(R.id.titulo), findViewById(R.id.text_songName)};

        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new TabsFragment());
        ft.commit();

        //Reproductor con slide hacia arriba
        ConstraintLayout bottomLayout = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomLayout);
        ConstraintLayout miniplayer = findViewById(R.id.mini_player);
        bottomSheetBehavior.addBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_EXPANDED:
                                miniplayer.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }

                    float slideAnterior = 0;
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        float alpha = 1 - slideOffset;
                        if (slideAnterior < slideOffset)
                            miniplayer.setAlpha(slideOffset);
                        else
                            miniplayer.setVisibility(View.VISIBLE);
                        miniplayer.setAlpha(alpha);
                        slideAnterior = slideOffset;
                    }
                }
        );

        miniplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
            }
        });

        //Boton play/pause
        ImageButton[] playPause = new ImageButton[]{findViewById(R.id.play_pause), findViewById(R.id.inner_play)};
        new UtilPlayer(player, playPause, titulos, findViewById(R.id.text_songInfo));
        for(ImageButton imgBtn : playPause){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changePlayPauseState(player, playPause);
                }
            });
        }

        //Boton siguiente
        ImageButton[] nexts = new ImageButton[]{findViewById(R.id.next),findViewById(R.id.mini_next)};
        for(ImageButton imgBtn : nexts){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilPlayer.getPlayer().seekToNext();
                }
            });
        }

        //Boton anterior
        ImageButton[] prevs = new ImageButton[]{findViewById(R.id.prev),findViewById(R.id.mini_previous)};
        for(ImageButton imgBtn : prevs){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilPlayer.getPlayer().seekToPrevious();
                }
            });
        }

        ImageButton random = findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player.getShuffleModeEnabled()){
                    player.setShuffleModeEnabled(false);
                    random.setAlpha(0.5f);
                }else{
                    player.setShuffleModeEnabled(true);
                    random.setAlpha(1f);
                }
            }
        });

    }

    public void showtoast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void changePlayPauseState(ExoPlayer player, ImageButton[] playPause){
        if (player.isPlaying()) {
            player.pause();
            for (ImageButton ib: playPause) {
                ib.setImageResource(R.drawable.ic_play);
            }
        } else {
            player.play();
            for (ImageButton ib: playPause) {
                ib.setImageResource(R.drawable.ic_stop);
            }
        }
    }

    //Various
    //Overrides using EasyPermissions library for a simpler code. By Google
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }
}