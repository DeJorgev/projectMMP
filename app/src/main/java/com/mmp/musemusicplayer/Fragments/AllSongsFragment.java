package com.mmp.musemusicplayer.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.exoplayer2.util.Log;
import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.UtilPlayer;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.ListDisplayer;
import com.google.android.exoplayer2.*;

import java.util.List;

public class AllSongsFragment extends Fragment {

    private static List<Song> deviceSongs = MainActivity.getDeviceSongs();
    private ListView songListView;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        songListView  =  view.findViewById(R.id.songListView);
        new ListDisplayer(getActivity()).displaySongs(songListView, deviceSongs);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listview = getView().findViewById(R.id.songListView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UtilPlayer.startPlayingList(i, deviceSongs);
            }
        });

        UtilPlayer.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                if(mediaItem != null) {
                    listview.setItemChecked(Integer.parseInt(mediaItem.mediaId), true);
                }
            }

        });
    }
}