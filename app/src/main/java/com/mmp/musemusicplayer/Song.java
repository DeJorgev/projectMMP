package com.mmp.musemusicplayer;

import android.net.Uri;

public class Song {
    //Meter aqui los metadatos a sacar 1/4.
    private long id,albumID;
    private int duration;
    private String name;
    private Uri songUri,albumImageUri;

    public Song(long id, long albumID, int duration, String name, Uri songUri, Uri albumImageUri) {
        this.id = id;
        this.albumID = albumID;
        this.duration = duration;
        this.name = name;
        this.songUri = songUri;
        this.albumImageUri = albumImageUri;
    }

    public long getId() {
        return id;
    }

    public long getAlbumID() {
        return albumID;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public Uri getAlbumImageUri() {
        return albumImageUri;
    }
}
