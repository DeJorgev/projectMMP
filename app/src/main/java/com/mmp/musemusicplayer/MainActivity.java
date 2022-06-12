package com.mmp.musemusicplayer;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mmp.musemusicplayer.Fragments.TabsFragment;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Artist;
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;
import com.mmp.musemusicplayer.SongTools.SongFetcher;
import com.mmp.musemusicplayer.databinding.ActivityMainBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextView[] titles;
    private ImageButton[] playPause;
    private ImageButton[] nextButtons;
    private ImageButton[] previousButtons;
    private ImageButton random;
    private ImageButton loopMode;
    private BottomSheetBehavior bottomSheetBehavior;
    private SeekBar seekBar;
    private TextView songDuration;
    private TextView currentSecond;
    private ImageButton baseLine;
    private ImageButton baseLineInversed;
    Handler handler = new Handler();

    private static List<Song> deviceSongs;
    public static List<Song> getDeviceSongs() {
        return deviceSongs;
    }

    private static List<Album> deviceAlbums;
    public static List<Album> getDeviceAlbums() {
        return deviceAlbums;
    }

    private static List<Artist> deviceArtists;
    public static List<Artist> getDeviceArtist() {
        return deviceArtists;
    }

    private static ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Request the user for external storage permison
        if (!EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(MainActivity.this, "Requesting permission to access storage", 102, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        //fetches the device songs, albums and artists.
        SongFetcher fetcher = new SongFetcher(MainActivity.this);
        deviceSongs = fetcher.manageSongsFetch();
        deviceAlbums = fetcher.getAlbums();
        deviceArtists = fetcher.getArtists();

        player = new ExoPlayer.Builder(this).build();

        titles = new TextView[]{findViewById(R.id.titulo), findViewById(R.id.text_songName)};

        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new TabsFragment());
        ft.commit();

        //Mini player behavior code
        ConstraintLayout bottomLayout = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomLayout);
        ConstraintLayout miniPlayer = findViewById(R.id.mini_player);
        bottomSheetBehavior.addBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_EXPANDED:
                                miniPlayer.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }

                    float slideAnterior = 0;
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        float alpha = 1 - slideOffset;
                        if (slideAnterior < slideOffset)
                            miniPlayer.setAlpha(slideOffset);
                        else
                            miniPlayer.setVisibility(View.VISIBLE);
                        miniPlayer.setAlpha(alpha);
                        slideAnterior = slideOffset;
                    }
                }
        );

        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Fast deploy
        baseLine = findViewById(R.id.baseline);
        baseLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
            }
        });

        //Fast undeploy
        baseLineInversed = findViewById(R.id.baseline_inversed);
        baseLineInversed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //Button play/pause
        playPause = new ImageButton[]{findViewById(R.id.play_pause), findViewById(R.id.inner_play)};
        new UtilPlayer(player, playPause, titles, findViewById(R.id.text_songInfo), findViewById(R.id.playing_image),this);
        for(ImageButton imgBtn : playPause){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changePlayPauseState(player, playPause);
                }
            });
        }

        //Button next
        nextButtons = new ImageButton[]{findViewById(R.id.next),findViewById(R.id.mini_next)};
        for(ImageButton imgBtn : nextButtons){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilPlayer.getPlayer().seekToNext();
                }
            });
        }

        //Button previous
        previousButtons = new ImageButton[]{findViewById(R.id.prev),findViewById(R.id.mini_previous)};
        for(ImageButton imgBtn : previousButtons){
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilPlayer.getPlayer().seekToPrevious();
                }
            });
        }

        //Button random
        random = findViewById(R.id.random);
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

        //Button loop
        loopMode = findViewById(R.id.loop);
        loopMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (player.getRepeatMode()){
                    case ExoPlayer.REPEAT_MODE_OFF:
                        player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
                        loopMode.setAlpha(1f);
                        loopMode.setImageResource(R.drawable.ic_loop_one);
                        break;
                    case ExoPlayer.REPEAT_MODE_ONE:
                        player.setRepeatMode(ExoPlayer.REPEAT_MODE_ALL);
                        loopMode.setImageResource(R.drawable.ic_loop);
                        break;
                    case ExoPlayer.REPEAT_MODE_ALL:
                        player.setRepeatMode(ExoPlayer.REPEAT_MODE_OFF);
                        loopMode.setAlpha(0.5f);
                        break;
                }
            }
        });

        seekBar = findViewById(R.id.seek_bar);
        songDuration = findViewById(R.id.song_total_duration);
        currentSecond = findViewById(R.id.song_current_second);

        //Seekbar - song times
          //Updates the song time metadata on change
        UtilPlayer.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                if(mediaItem != null) {
                    UtilPlayer.updatePlayerMetadata(Integer.parseInt(mediaItem.mediaId));
                    songDuration.setText(UtilPlayer.getReadableTime((int) UtilPlayer.getDuration(Integer.parseInt(mediaItem.mediaId))));
                    seekBar.setMax((int) UtilPlayer.getDuration(Integer.parseInt(mediaItem.mediaId)));
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                if(player.isPlaying()){
                    UpdateSeekBar upsk = new UpdateSeekBar();
                    handler.post(upsk);
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //allows the user to select a second of the song to play from.
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    UtilPlayer.getPlayer().seekTo((long)i);
                }
            }

            //Mandatory interface methods.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //By default loads all the song starting by the first one
        if(deviceSongs.size() > 0) {
            UtilPlayer.startPlayingList(0, deviceSongs, false);
            UtilPlayer.updatePlayerMetadata(0);
        }
    }

    //A thread that updates the seekbar with the current second the song is playing.
    public class UpdateSeekBar implements Runnable{

        @Override
        public void run() {
            seekBar.setProgress((int) player.getCurrentPosition());
            currentSecond.setText(UtilPlayer.getReadableTime((int)player.getCurrentPosition()));
            handler.postDelayed(this, 100);
        }
    }

    //Quite self descriptive
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
        //Overrides android back button to close the extended mini player without closing the app.
    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }
}