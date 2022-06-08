package com.mmp.musemusicplayer.SongTools.DataContainers;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Song  implements Parcelable {
    private long id,albumID,artistID;
    private int duration;
    private String name, albumName , artistName;
    private Uri songUri,albumImageUri;

    public Song(long id, long albumID,long artistID, int duration, String name, String albumName, String artist, Uri songUri, Uri albumImageUri ) {
        this.id = id;
        this.albumID = albumID;
        this.artistID = artistID;
        this.duration = duration;
        this.name = name;
        this.albumName = albumName;
        this.artistName = artist;
        this.songUri = songUri;
        this.albumImageUri = albumImageUri;
    }

    protected Song(Parcel in) {
        id = in.readLong();
        albumID = in.readLong();
        artistID = in.readLong();
        duration = in.readInt();
        name = in.readString();
        albumName = in.readString();
        albumImageUri = Uri.parse(in.readString());
        artistName = in.readString();
        songUri = Uri.parse(in.readString());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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

    public String getArtistName() {
        return artistName;
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
        parcel.writeLong(id);
        parcel.writeLong(albumID);
        parcel.writeLong(artistID);
        parcel.writeInt(duration);
        parcel.writeString(name);
        parcel.writeString(albumName);
        parcel.writeString(albumImageUri.toString());
        parcel.writeString(artistName);
        parcel.writeString(songUri.toString());
    }
}
