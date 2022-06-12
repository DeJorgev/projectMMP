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
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;

import java.util.List;

 /**
  *  An adapter that loads a customized item view to use to display the songs in a list views.
  *  Needs a List and an inflater.
  *
  * @author Borja Avalos, Jorge García
  * @version 1.1.0
 **/
public class AdapterSongLV extends BaseAdapter {
    List<Song> songs;
    LayoutInflater inflater;
    Context context;

    public AdapterSongLV(List songs, Context context) {
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
      * Loads the data from the item songs into the view.
      * Uses Glide to minimize resources consumption while loading items.
      * @param i The current item at the List.
      * @return A custom view.
      */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView = inflater.inflate(R.layout.itemlist_item, null);

        TextView textSongName = myView.findViewById(R.id.item_song_name);
        textSongName.setText(songs.get(i).getName());

        TextView textSongInfo = myView.findViewById(R.id.item_info_name);
        textSongInfo.setText(songs.get(i).getAlbumName() + " - " + songs.get(i).getArtistName());

        ImageView albumArt = myView.findViewById(R.id.item_albumart);
        if (songs.get(i).getAlbumImageUri()!=null) {
            Glide.with(context)
                    .load(songs.get(i).getAlbumImageUri())
                    .placeholder(R.drawable.ic_default_artimage)
                    .into(albumArt);
        }
        return myView;
    }


}
