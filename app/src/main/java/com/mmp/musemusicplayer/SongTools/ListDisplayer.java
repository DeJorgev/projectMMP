package com.mmp.musemusicplayer.SongTools;

import android.content.Context;
import android.widget.ListView;

import com.mmp.musemusicplayer.SongTools.CustomAdapters.CustomSongAdapter;
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;

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
     * @version 1.0.0.
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
     * uses a custom adapter.
     *
     * @param songListView The List View where the songs are going to be displayed. Has to exist in the layout and be initialized in the view and code.
     * @param songsToDisplay the List of song item to display
     */
    public void displaySongs(ListView songListView, List<Song> songsToDisplay){
        String [] songNames = new String [songsToDisplay.size()];

        for (int i = 0; i < songNames.length; i++)
            songNames[i] = songsToDisplay.get(i).getName();

        CustomSongAdapter customSongAdapter = new CustomSongAdapter(songsToDisplay, actualClass);
        songListView.setAdapter(customSongAdapter);
    }
}
