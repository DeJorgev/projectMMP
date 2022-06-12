package com.mmp.musemusicplayer.SongTools.DataContainers;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Artist implements Parcelable {

    private List<Album> albumList;
    private String artistName;
    private Uri imageUri;

    public Artist(Album album) {
        this.albumList = new ArrayList<>();
        albumList.add(album);
        this.artistName = album.getArtistName();
    }

    protected Artist(Parcel in) {
        artistName = in.readString();
        albumList = new ArrayList<Album>();
        in.readList(albumList, Album.class.getClassLoader());
        imageUri = Uri.parse(in.readString());
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public void addAlbum(Album album){
        albumList.add(album);
    }

    public String getArtistName() {
        return artistName;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artistName);
        parcel.writeList(albumList);
        parcel.writeString(imageUri.toString());
    }
}
