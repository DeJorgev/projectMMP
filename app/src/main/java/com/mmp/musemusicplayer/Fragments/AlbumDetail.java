package com.mmp.musemusicplayer.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.ListDisplayer;
import com.mmp.musemusicplayer.UtilPlayer;

import java.io.IOException;

/**
 * A fragment that displays the usable info and the songs contained in an album.
 * Recieves the album that is going to be displayed.
 *
 * @author
 * <ul>
 *  <li>Borja Avalos</li>
 *  <li>Jorge Garcia.</li>
 * </ul>
 * @version 1.2.0
 */

public class AlbumDetail extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private Album album;
    private TextView albumTitle, artistName, numberOfSongsTV;
    private ImageView artistImage;
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

        albumTitle = view.findViewById(R.id.album_title);
        albumTitle.setText(album.getAlbumName());

        artistName = view.findViewById(R.id.artist_name);
        artistName.setText(album.getArtistName());

        Resources res = getResources();
        int numberOfSongs = album.getSongs().size();
        String songsString = res.getQuantityString(R.plurals.number_of_songs,numberOfSongs, numberOfSongs);
        numberOfSongsTV = view.findViewById(R.id.songs_number);
        numberOfSongsTV.setText(songsString);


        artistImage = view.findViewById(R.id.artist_image);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), album.getSongs().get(0).getAlbumImageUri());
            artistImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        albumSongsLV = view.findViewById(R.id.album_songs_list_view);
        new ListDisplayer(this.getContext()).displaySongs(albumSongsLV, album.getSongs());

        albumSongsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UtilPlayer.startPlayingList(i, album.getSongs(),true);
            }
        });

        //Correctly updates currently select song in list view metadata

        UtilPlayer.getPlayer().addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);
                if(mediaItem != null) {
                    int newMediaID = Integer.parseInt(mediaItem.mediaId);

                    albumSongsLV.smoothScrollToPosition(newMediaID);
                    UtilPlayer.updatePlayerMetadata(newMediaID);
                    albumSongsLV.setItemChecked(newMediaID, true);
                }
            }

        });
    }
}