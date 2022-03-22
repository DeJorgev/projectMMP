package com.mmp.musemusicplayer.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongDisplayer;

import java.util.List;

public class AllSongsFragment extends Fragment {

    private static List<Song> deviceSongs = MainActivity.getDeviceSongs();
    private ListView songListView;

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

}