package com.mmp.musemusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.SongDisplayer;
import com.mmp.musemusicplayer.SongTools.SongFetcher;

public class AlbumActivity extends AppCompatActivity {

    private Album album;
    private TextView albumTitle;
    private ListView albumSongsLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        loadAlbum(getIntent());
        albumSongsLV = findViewById(R.id.album_songs_list_view);
        new SongDisplayer(this).displaySongs(albumSongsLV, album.getSongs());


    }

    private void loadAlbum(Intent i){
        album = i.getParcelableExtra("album");
        albumTitle = findViewById(R.id.albumTitle);
        albumTitle.setText(album.getAlbumName());
    }
}