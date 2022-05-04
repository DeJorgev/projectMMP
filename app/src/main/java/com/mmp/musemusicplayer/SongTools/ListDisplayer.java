package com.mmp.musemusicplayer.SongTools;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListDisplayer {

    /**
     * A common Class to display items in a ListView or RecycleView.
     * requires a Context to work.
     *
     * @author
     *  <ul>
     *      <li>Borja Abalos</li>
     *      <li>Jorge García.</li>
     *  </ul>
     * @version 0.1.0.
     */

    private Context actualClass;

    /**
     * Usual constructor
     *
     * @param actualClass The context in witch the object will be created and used.
     *                    The activity where it will be used.
     */
    public ListDisplayer(Context actualClass) {
        this.actualClass = actualClass;
    }

    /**
     * Displays in a List View the name of the songs saved in a provided List, each one on a separate list view item.
     * uses (For the moment) a default adapter.
     *
     * @param songListView The List View where the songs are going to be displayed. Has to exist in the layout and be initialized in the view and code.
     * @param songsToDisplay the List of song item to display
     */
    public void displaySongs(ListView songListView, List<Song> songsToDisplay){
        String [] songNames = new String [songsToDisplay.size()];

        for (int i = 0; i < songNames.length; i++)
            songNames[i] = songsToDisplay.get(i).getName();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(actualClass, android.R.layout.simple_list_item_1,songNames);
        songListView.setAdapter(arrayAdapter);
    }

    //Displays the arrayList of songs on a list view item using (For the moment) a default adapter.
    public void displayAlbums(ListView albumListView, List<Album> albumsToDisplay){
        String [] albumNames = new String [albumsToDisplay.size()];

        for (int i = 0; i < albumNames.length; i++)
            albumNames[i] = albumsToDisplay.get(i).getAlbumName();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(actualClass, android.R.layout.simple_list_item_1,albumNames);
        albumListView.setAdapter(arrayAdapter);
    }
}
