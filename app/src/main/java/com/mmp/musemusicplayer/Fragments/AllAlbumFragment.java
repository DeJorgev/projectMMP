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
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.CustomAdapters.AdapterAlbumRV;

import java.util.ArrayList;
import java.util.List;

public class AllAlbumFragment extends Fragment {

    private static List<Album> deviceAlbums = MainActivity.getDeviceAlbums();
    private RecyclerView recycler;

    public AllAlbumFragment() {
        // Required empty public constructor
    }

    public static AllAlbumFragment newInstance() {
        AllAlbumFragment fragment = new AllAlbumFragment();
        Bundle args = new Bundle();
        ArrayList<Album> listAlbums = new ArrayList<>();
        listAlbums.addAll(deviceAlbums);
        args.putParcelableArrayList("albums", listAlbums);
        fragment.setArguments(args);
        return fragment;
    }

    public static AllAlbumFragment newInstance(List<Album> albums) {
        AllAlbumFragment fragment = new AllAlbumFragment();
        Bundle args = new Bundle();
        ArrayList<Album> listAlbums = new ArrayList<>();
        listAlbums.addAll(albums);
        args.putParcelableArrayList("albums", listAlbums);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_album, container, false);
        recycler = view.findViewById(R.id.album_rv);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        List<Album> albums = args.getParcelableArrayList("albums");

        AdapterAlbumRV adapter = new AdapterAlbumRV(albums, this);
        recycler.setAdapter(adapter);

        return view;
    }
}