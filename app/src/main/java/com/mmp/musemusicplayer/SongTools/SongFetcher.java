package com.mmp.musemusicplayer.SongTools;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides various methods to get the songs on all data devices on a phone,
 * hidden or protected directories are NOT scanned. It requires a Context to work.
 *
 * @author jorge García
 * @version 1.0.0
 */

public class SongFetcher {
   private Context classContext;

    /**
     * Usual constructor.
     *
     * @param classContext The context in witch the object will be created and used.
     *                    The activity where it will be used.
     */

    public SongFetcher(Context classContext) {
        this.classContext = classContext;
    }

    /**
     * A method that groups all the necessary code to fetch songs in a more readable and encapsulated way.
     * uses all the private methods in this class to construct a Query, get the data and return it in a Song List.
     *
     * @return A List of objects song filled with all the audio files inside the data devices.
     */

    public List<Song> manageSongsFetch(){
        //Prepares the Query statement values;
        Uri songFolderUri = checkDeviceVersion();
        String [] projection = projectionFabric();
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";

        //Executes the Query and saves the selected song´s data in a List
        List<Song> songsList = fetchSongs(songFolderUri, projection, sortOrder);

        Toast debug = Toast.makeText(classContext, "Number of Songs: " + songsList.size(), Toast.LENGTH_SHORT);
        debug.show();

        return songsList;
    }

    /**
     * Between newer and older android versions the method to get the Media directory differs,
     * this method checks the device version and returns the correct one.
     * This method will probably be subject to changes as new Android Versions emerge.
     *
     * @return An Uri direction with the correct media directories where songs are, according to Android device version.
     */

    private Uri checkDeviceVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return  MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        else
            return  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * Creates a projection needed to execute a query using context.getContentResolver. A projection
     * is basically a String array containing the rows that are going to be requested in a query.
     *
     *
     * @return A projection, a String array with the rows that are going to be requested in a query.
     */

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

    /**
     * This method does the actual Query expecified in the projection, extracts the metadata from the media audio file
     * and saves it in an List of Songs.
     *
     * @param songFolderUri The uri direction of the song folder. Got at checkDeviceVersion()
     * @param projection A string array with the rows to request in the query, Got at projectionFabric()
     * @param sortOrder The order in witch the songs are going to be saved in the song list,
     * @return  A list of objects song filled with all the audio files inside the data devices.
     */

    private List<Song> fetchSongs(Uri songFolderUri,String [] projection,String sortOrder){
        List<Song> songsList = new ArrayList<>();

        //Querying
        try (Cursor cursor =  classContext.getContentResolver().query(songFolderUri, projection,null,null,sortOrder)) {
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
