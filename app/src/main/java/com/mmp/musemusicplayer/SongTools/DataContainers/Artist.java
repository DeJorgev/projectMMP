package com.mmp.musemusicplayer.SongTools.DataContainers;

import java.util.ArrayList;
import java.util.List;

public class Artist {

    private List<Album> albumList;
    private String artistName;

    public Artist(Album album) {
        this.albumList = new ArrayList<>();
        albumList.add(album);
        this.artistName = album.getArtistName();
    }

    public void addAlbum(Album album){
        albumList.add(album);
    }

    public String getArtistName() {
        return artistName;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }
}
