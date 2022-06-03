package com.mmp.musemusicplayer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Artist;
import com.mmp.musemusicplayer.SongTools.CustomAdapters.AdapterAlbumRV;
import com.mmp.musemusicplayer.SongTools.CustomAdapters.AdapterArtistsRV;

import java.util.List;

public class AllArtistsFragment extends Fragment {


    private RecyclerView recycler;
    private List<Artist> allArtists = MainActivity.getDeviceArtist();
    public AllArtistsFragment() {
        // Required empty public constructor
    }

    public static AllArtistsFragment newInstance() {
        AllArtistsFragment fragment = new AllArtistsFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_album, container, false);
        /*albumListView  =  view.findViewById(R.id.albumListView);
        new ListDisplayer(getActivity()).displayAlbums(albumListView,deviceAlbums);*/
        recycler = view.findViewById(R.id.album_rv);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterArtistsRV adapter = new AdapterArtistsRV(allArtists, this);
        recycler.setAdapter(adapter);

        return view;
    }
}