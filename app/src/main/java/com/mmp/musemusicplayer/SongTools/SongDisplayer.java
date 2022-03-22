package com.mmp.musemusicplayer.SongTools;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SongDisplayer {
    private Context actualClass;

    public SongDisplayer(Context actualClass) {
        this.actualClass = actualClass;
    }

    //Displays the arrayList of songs on a list view item using (For the moment) a default adapter.
    public void displaySongs(ListView songListView, List<Song> songsToDisplay){
        String [] songNames = new String [songsToDisplay.size()];

        for (int i = 0; i < songNames.length; i++)
            songNames[i] = songsToDisplay.get(i).getName();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(actualClass, android.R.layout.simple_list_item_1,songNames);
        songListView.setAdapter(arrayAdapter);
    }
}
