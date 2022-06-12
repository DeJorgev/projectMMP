package com.mmp.musemusicplayer.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.CustomAdapters.AdapterAlbumRV;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Artist;

import java.io.IOException;
import java.util.List;

/**
 * A fragment used to display the important information as well as all the albums contained
 * in an Artist. Uses a RecyclerView(using AdapterAlbumRV) to display each album.
 * Requires an Artist object to be instantiated.
 *
 * @author
 * <ul>
 *  <li>Borja Abalos</li>
 *  <li>Jorge García.</li>
 * </ul>
 * @version 1.2.0
 */

public class ArtistAlbumsFragment extends Fragment {

    private static Artist artist;
    private RecyclerView recycler;
    private TextView artistName, numberOfSongsTV, numberOfAlbumsTV;
    private ImageView artistImage;

    public ArtistAlbumsFragment() {
        // Required empty public constructor
    }

    public static ArtistAlbumsFragment newInstance(Artist artist) {
        ArtistAlbumsFragment fragment = new ArtistAlbumsFragment();
        Bundle args = new Bundle();
        args.putParcelable("artist", artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        artistName = view.findViewById(R.id.artist_name);
        artistName.setText(artist.getArtistName());

        List<Album> albums = artist.getAlbumList();

        int numberOfAlbums = albums.size();
        Resources res = getResources();
        String albumsString = res.getQuantityString(R.plurals.number_of_albums,numberOfAlbums, numberOfAlbums);
        numberOfAlbumsTV = view.findViewById(R.id.album_number);
        numberOfAlbumsTV.setText(albumsString);

        int numberOfSongs = getNumberOfSongs(albums);
        String songsString = res.getQuantityString(R.plurals.number_of_songs,numberOfSongs, numberOfSongs);
        numberOfSongsTV = view.findViewById(R.id.songs_number);
        numberOfSongsTV.setText(songsString);


        if(artist.getImageUri()!= null) {
            artistImage = view.findViewById(R.id.artist_image);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), artist.getImageUri());
                artistImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_albums, container, false);
        recycler = view.findViewById(R.id.album_rv);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        artist = args.getParcelable("artist");

        AdapterAlbumRV adapter = new AdapterAlbumRV(artist.getAlbumList(), this);
        recycler.setAdapter(adapter);

        return view;
    }

    /**
     * Custom method that given a list list of albums, iterates them adding
     * all the songs these contain.
     *
     * @param albums A list containing the albums
     * @return (int) The total number of songs in all the given albums
     */
    private int getNumberOfSongs(List<Album> albums){
        int numberOfSongs = 0;
        for (Album album: albums) {
            numberOfSongs += album.getSongs().size();
        }
        return numberOfSongs;
    }
}