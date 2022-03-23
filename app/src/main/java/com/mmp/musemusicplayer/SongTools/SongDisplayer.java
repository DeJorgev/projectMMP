package com.mmp.musemusicplayer.SongTools;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * A common Class to display the songs in a ListView or RecycleView.
 * requires a Context to work.
 *
 * @author Jorge García.
 * @version 0.1.0.
 */

//todo: A lo mejor me toca crear un displaySongs para Recicle view, supuestamente es mejor y da mas espacio a la creatividad.
public class SongDisplayer {
    private Context actualClass;

    /**
     * Usual constructor
     *
     * @param actualClass The context in witch the object will be created and used.
     *                    The activity where it will be used.
     */

    public SongDisplayer(Context actualClass) {
        this.actualClass = actualClass;
    }

    /**
     * Displays in a List View the name of the songs saved in a provided List, each one on a separate list view item.
     * uses (For the moment) a default adapter.
     *
     * @param listView The List View where the songs are going to be displayed. Has to exist in the layout and be initialized in the view and code.
     * @param songsToDisplay the List of song item to display
     */

    //Displays the arrayList of songs on a list view item using (For the moment) a default adapter.
    public void displaySongs(ListView listView, List<Song> songsToDisplay){
        String [] songNames = new String [songsToDisplay.size()];

        for (int i = 0; i < songNames.length; i++)
            songNames[i] = songsToDisplay.get(i).getName();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(actualClass, android.R.layout.simple_list_item_1,songNames);
        listView.setAdapter(arrayAdapter);
    }
}
