package com.mmp.musemusicplayer.SongTools;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Artist;
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides various methods to get the songs on all data devices on a phone,
 * hidden or protected directories are NOT scanned. It requires a Context to work.
 *
 * @author
 * <ul>
 *  <li>Borja Abalos</li>
 *  <li>Jorge García.</li>
 * </ul>
 * @version 1.2.0
 */

public class SongFetcher {
    private Context actualClass;
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();

    /**
     * Usual constructor.
     *
     * @param actualClass The context in witch the object will be created and used.
     *                    The activity where it will be used.
     */
    public SongFetcher(Context actualClass) {
        this.actualClass = actualClass;
    }


    /**
     * A method that groups all the necessary code to fetch songs in a more readable and encapsulated way.
     * uses all the private methods in this class to construct a Query, get the data and return it in a Song List.
     *
     * @return A List of objects song filled with all the audio files inside the data devices.
     */
    public List<Song> manageSongsFetch() {
        //Prepares the Query statement values;
        Uri songFolderUri = checkDeviceVersion();
        String[] projection = projectionFabric();
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";

        //Executes the Query and saves the selected song´s data in a List
        List<Song> songsList = fetchSongs(songFolderUri, projection, sortOrder);
        if(songsList != null)
            Log.e("Hasta aqui",songsList.size()+"");
        return songsList;
    }

    /**
     * Between newer and older android versions the method to get the Media directory differs,
     * this method checks the device version and returns the correct one.
     * This method will probably be subject to changes as new Android Versions emerge.
     *
     * @return An Uri direction with the correct media directories where songs are, according to Android device version.
     */
    private Uri checkDeviceVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        else
            return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * Creates a projection needed to execute a query using context.getContentResolver. A projection
     * is basically a String array containing the rows that are going to be requested in a query.
     *
     * @return A projection, a String array with the rows that are going to be requested in a query.
     */
    private String[] projectionFabric() {
        String[] projection = new String[]{
                //Meter aqui los metadatos a sacar 2/4.
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
        };

        return projection;
    }

    /**
     * This method does the actual Query expecified in the projection, extracts the metadata from the media audio file
     * and saves it in an List of Songs.
     *
     * @param songFolderUri The uri direction of the song folder. Got at checkDeviceVersion()
     * @param projection    A string array with the rows to request in the query, Got at projectionFabric()
     * @param sortOrder     The order in witch the songs are going to be saved in the song list,
     * @return A list of objects song filled with all the audio files inside the data devices.
     */
    private List<Song> fetchSongs(Uri songFolderUri, String[] projection, String sortOrder) {
        List<Song> songsList = new ArrayList<>();

        //Querying
        try (Cursor cursor = actualClass.getContentResolver().query(songFolderUri, projection, null, null, sortOrder)) {
            //Columns in the Query
            //Meter aqui los metadatos a sacar 3/4.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int albumIDColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);
            int albumNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int artistIDColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID);
            int artistNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

            //getting the actual values for each column of each file read and applied
            while (cursor.moveToNext()) {
                //Meter aqui los metadatos a sacar 4/4.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                long albumID = cursor.getLong(albumIDColumn);
                String albumName = cursor.getString(albumNameColumn);
                long artistID = cursor.getLong(artistIDColumn);
                String artistName = cursor.getString(artistNameColumn);

                Uri songuri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                Uri albumImageUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumID);

                //Removing extension on name.
                name = name.substring(0, name.lastIndexOf("."));

                //Creating and adding song Item to List
                Song song = new Song(id, albumID,artistID, duration, name, albumName,artistName , songuri, albumImageUri);
                Album album = addAlbum(song);
                addArtist(album);
                songsList.add(song);

            }
        }
        return songsList;
    }


    private Album addAlbum(Song song) {
        int index = -1;
        for (int i = 0; i < albums.size() && index == -1; ++i) {
            if (albums.get(i).getAlbumID() == song.getAlbumID())
                index = i;
        }
        Album album;
        if (index != -1) {
            albums.get(index).getSongs().add(song);
            album = albums.get(index);

        }else {
            album = new Album(song.getAlbumID(), song.getAlbumName(), song, song.getArtistName());
            albums.add(album);
        }
        return album;
    }

    private void addArtist(Album album){
        int index = -1;
        for (int i = 0; i < artists.size() && index == -1; ++i){
            if(artists.get(i).getArtistName().equals(album.getArtistName()))
                index = i;
        }
        if(index != -1) {
            if (!artists.get(index).getAlbumList().contains(album)) {
                artists.get(index).addAlbum(album);
            }
        }
        else {
            artists.add(new Artist(album));
        }
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Artist> getArtists() {
        return artists;
    }
}
