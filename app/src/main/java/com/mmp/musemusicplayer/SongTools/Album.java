package com.mmp.musemusicplayer.SongTools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Album implements Parcelable {

    private long albumId;
    private String albumName;
    private List<Song> songs;
    private String artistName;

    public Album(long album_id, String albumName, Song song, String artistName) {
        this.albumId = album_id;
        this.albumName = albumName;
        this.artistName = artistName;
        songs = new ArrayList<>();
        songs.add(song);
    }

    protected Album(Parcel in) {
        albumId = in.readLong();
        albumName = in.readString();
        songs = new ArrayList<Song>();
        in.readList(songs, Song.class.getClassLoader());
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getArtistName() {
        return artistName;
    }

    public long getAlbumID() {
        return albumId;
    }

    public List<Song> getSongs() {
        return songs;
    }


    public String getAlbumName() {
        return albumName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(albumId);
        parcel.writeString(albumName);
        parcel.writeList(songs);
    }
}
