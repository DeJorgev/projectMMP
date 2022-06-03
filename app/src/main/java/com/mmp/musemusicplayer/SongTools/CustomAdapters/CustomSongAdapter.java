package com.mmp.musemusicplayer.SongTools.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;

import java.util.List;

 /**
  *  An adapter that loads a customized item view to use to display the songs in a list views.
  *  Needs a List and an inflater.
 **/
public class CustomSongAdapter extends BaseAdapter {
    List<Song> songs;
    LayoutInflater inflater;
    Context context;

    public CustomSongAdapter(List songs, Context context) {
        this.songs = songs;
        this.context = context;
        Activity activity = (Activity) context;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return songs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

     /**
      * Loads the data from the item songns into the view.
      * needs optimization.
      * @param i The current item at the List.
      * @return A custom view.
      */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView = inflater.inflate(R.layout.itemlist_item, null);

        TextView textSongName = myView.findViewById(R.id.itemsongname);
        textSongName.setText(songs.get(i).getName());

        TextView textSongInfo = myView.findViewById(R.id.iteminfoname);
        textSongInfo.setText(songs.get(i).getAlbumName() + " - " + songs.get(i).getArtistName());

        ImageView albumArt = myView.findViewById(R.id.itemalbumart);
        if (songs.get(i).getAlbumImageUri()!=null) {
            Glide.with(context)
                    .load(songs.get(i).getAlbumImageUri())
                    .placeholder(R.drawable.ic_default_artimage)
                    .into(albumArt);
        }
        /*
        if (songs.get(i).getAlbumImageUri()!=null) {
            ImageView albumArt = myView.findViewById(R.id.itemalbumart);
            albumArt.setImageURI(songs.get(i).getAlbumImageUri());

            if (albumArt.getDrawable() == null)
                albumArt.setImageResource(R.drawable.ic_default_artimage);
        }*/
        return myView;
    }


}
