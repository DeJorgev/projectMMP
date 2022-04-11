package com.mmp.musemusicplayer.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mmp.musemusicplayer.AlbumActivity;
import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.SongTools.SongDisplayer;

import java.util.List;

public class AllAlbumFragment extends Fragment {

    private static List<Album> deviceAlbums = MainActivity.getDeviceAlbums();
    private ListView albumListView;

    public AllAlbumFragment() {
        // Required empty public constructor
    }

    public static AllAlbumFragment newInstance() {
        AllAlbumFragment fragment = new AllAlbumFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumListView = getView().findViewById(R.id.albumListView);
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Album al = deviceAlbums.get(i);
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                intent.putExtra("album",al);
                Toast.makeText(getContext(),al.getSongs().size()+"", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_album, container, false);
        albumListView  =  view.findViewById(R.id.albumListView);
        new SongDisplayer(getActivity()).displayAlbums(albumListView,deviceAlbums);

        return view;
    }
}