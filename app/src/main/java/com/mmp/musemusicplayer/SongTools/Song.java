package com.mmp.musemusicplayer.SongTools;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Song  implements Parcelable {
    //Meter aqui los metadatos a sacar 1/4.
    private long id,albumID;
    private int duration;
    private String name, albumName;
    private Uri songUri,albumImageUri;

    public Song(long id, long albumID, int duration, String name, String albumName, Uri songUri, Uri albumImageUri) {
        this.id = id;
        this.albumID = albumID;
        this.duration = duration;
        this.name = name;
        this.albumName = albumName;
        this.songUri = songUri;
        this.albumImageUri = albumImageUri;
    }

    protected Song(Parcel in) {
        id = in.readLong();
        albumID = in.readLong();
        duration = in.readInt();
        name = in.readString();
        albumName = in.readString();
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
        parcel.writeInt(duration);
        parcel.writeString(name);
        parcel.writeString(albumName);
        parcel.writeString(songUri.toString());
    }
}
