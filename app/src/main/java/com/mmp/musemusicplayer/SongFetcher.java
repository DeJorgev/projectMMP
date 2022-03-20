package com.mmp.musemusicplayer;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//Provides Methods for fetching Songs
public class SongFetcher {
    int sortTipe;
    Context actualClass;
    String [] songNames;

    public SongFetcher(int sortTipe, Context actualClass) {
        this.sortTipe = sortTipe;
        this.actualClass = actualClass;
    }

    //Displays the results of manageSongsFetch() on a list view item using (For the moment) a default adapter.
    public void displaySongs(ListView songListView){
        List<Song> songsInDevice = manageSongsFetch();

        songNames = new String [songsInDevice.size()];

        for (int i = 0; i < songNames.length; i++)
            songNames[i] = songsInDevice.get(i).getName();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(actualClass, android.R.layout.simple_list_item_1,songNames);
        songListView.setAdapter(arrayAdapter);
    }

    //Groups the necessary code in a more readable and comprehensive way, or so I hope.
    public List<Song> manageSongsFetch(){
        //Prepares the Query statement values;
        Uri songFolderUri = checkDeviceVersion();
        String [] projection = projectionFabric();
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";

        //Executes the Query and saves the selected song´s data in a List
        List<Song> songsList = fetchSongs(songFolderUri, projection, sortOrder);

        Toast debug = Toast.makeText(actualClass, "Number of Songs: " + songsList.size(), Toast.LENGTH_SHORT);
        debug.show();

        return songsList;
    }

    private Uri checkDeviceVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return  MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        else
            return  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    //Creates a String array with the column´s name of song´s data to get
    private String [] projectionFabric(){
        String [] projection = new String[]{
                //Meter aqui los metadatos a sacar 2/4.
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        return projection;
    }

    //TODO: Add a switch with different types of sorts dependant of the parameter value
    private String generateSortOrder(){
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + "ASC";

        return sortOrder;
    }

    private List<Song> fetchSongs(Uri songFolderUri,String [] projection,String sortOrder){
        List<Song> songsList = new ArrayList<>();

        //Querying
        try (Cursor cursor =  actualClass.getContentResolver().query(songFolderUri, projection,null,null,sortOrder)) {
            //Columns in the Query
            //Meter aqui los metadatos a sacar 3/4.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn= cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int albumIDColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            //getting the actual values for each column of each file read and applied
            while(cursor.moveToNext()){
                //Meter aqui los metadatos a sacar 4/4.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                long albumID = cursor.getLong(albumIDColumn);

                Uri songuri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                Uri albumImageUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumID);

                //Removing extension on name.
                name = name.substring(0,name.lastIndexOf("."));

                //Creating and adding song Item to List
                Song song = new Song(id, albumID, duration, name, songuri,albumImageUri);
                songsList.add(song);
            }
        }
        return songsList;
    }

}
