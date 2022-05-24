package com.mmp.musemusicplayer.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmp.musemusicplayer.MainActivity;

import com.mmp.musemusicplayer.SongTools.CustomAdapters.AdapterSongRV;
import com.mmp.musemusicplayer.UtilPlayer;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;
import com.google.android.exoplayer2.*;

import java.util.List;

public class AllSongsFragment extends Fragment{

    private static List<Song> deviceSongs = MainActivity.getDeviceSongs();
    private RecyclerView songRV;
    private ExoPlayer player = MainActivity.getExoPlayer();
    private boolean playing = MainActivity.isPlaying();

    public static AllSongsFragment newInstance() {
        AllSongsFragment fragment = new AllSongsFragment();
        return fragment;
    }


    public AllSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        songRV = view.findViewById(R.id.song_recycler_view);
        songRV.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterSongRV adapter = new AdapterSongRV(deviceSongs, this);
        songRV.setAdapter(adapter);

        player.addListener(new Player.Listener() {
            int oldmedia = -1;


            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);

                if (mediaItem != null) {
                    if (oldmedia < Integer.parseInt(mediaItem.mediaId))
                        songRV.scrollToPosition(Integer.parseInt(mediaItem.mediaId) + 3);
                    else
                        songRV.scrollToPosition(Integer.parseInt(mediaItem.mediaId) - 3);

                    if (oldmedia != -1 && songRV.findViewHolderForAdapterPosition(oldmedia) != null)
                        songRV.findViewHolderForAdapterPosition(oldmedia).itemView.setSelected(false);

                    UtilPlayer.updatePlayerMetadata(Integer.parseInt(mediaItem.mediaId));

                    songRV.post(new Runnable() {
                        @Override
                        public void run() {
                            songRV.findViewHolderForAdapterPosition(Integer.parseInt(mediaItem.mediaId)).itemView.setSelected(true);
                        }
                    });
                    oldmedia = Integer.parseInt(mediaItem.mediaId);
                }
            }
        });


    }
}