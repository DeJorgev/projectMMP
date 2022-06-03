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
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.ListDisplayer;
import com.mmp.musemusicplayer.UtilPlayer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumDetail extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private Album album;
    private TextView albumTitle;
    private ListView albumSongsLV;


    public AlbumDetail() {
        // Required empty public constructor
    }

    public static AlbumDetail newInstance(Album album) {
        AlbumDetail fragment = new AlbumDetail();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            album = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumTitle = view.findViewById(R.id.albumTitle);
        albumTitle.setText(album.getAlbumName());

        albumSongsLV = view.findViewById(R.id.album_songs_list_view);
        new ListDisplayer(this.getContext()).displaySongs(albumSongsLV, album.getSongs());

        albumSongsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UtilPlayer.startPlayingList(i, album.getSongs());
            }
        });

        UtilPlayer.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                if(mediaItem != null) {
                    UtilPlayer.updatePlayerMetadata(Integer.parseInt(mediaItem.mediaId));
                    albumSongsLV.setItemChecked(Integer.parseInt(mediaItem.mediaId), true);
                }
            }

        });
    }
}