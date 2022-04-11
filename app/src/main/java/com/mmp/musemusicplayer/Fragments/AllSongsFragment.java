package com.mmp.musemusicplayer.Fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongDisplayer;
import com.google.android.exoplayer2.*;

import java.util.List;

public class AllSongsFragment extends Fragment {

    private static List<Song> deviceSongs = MainActivity.getDeviceSongs();
    private ListView songListView;
    private ExoPlayer player = MainActivity.getExoPlayer();
    private boolean playing = false;

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
        new SongDisplayer(getActivity()).displaySongs(songListView, deviceSongs);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listview = getView().findViewById(R.id.songListView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(playing){
                    player.clearMediaItems();
                }
                loadMediaItems(i);
                player.prepare();
                player.play();
                player.seekToDefaultPosition(0);
                playing = true;
            }
        });
    }

    private void loadMediaItems(int selectedMediaItem){
        for(int i = 0;i < deviceSongs.size(); ++i){
            int selec = i + selectedMediaItem;
            if(selec < deviceSongs.size()){
                player.addMediaItem(MediaItem.fromUri(deviceSongs.get(selec).getSongUri()));
            }else{
                player.addMediaItem(MediaItem.fromUri(deviceSongs.get(i-selectedMediaItem).getSongUri()));
            }
        }
    }
}