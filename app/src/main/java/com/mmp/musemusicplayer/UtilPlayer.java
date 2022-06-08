package com.mmp.musemusicplayer;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.mmp.musemusicplayer.SongTools.DataContainers.Song;

import java.util.List;

/**
 * Different utilities to solve problems with exoplayer and machine reads
 *
 * @author Borja Avalos, Jorge Garcia
 * @version 1.0.0
 */

public class UtilPlayer {

    private static ExoPlayer player;
    private static ImageButton buttonPlay[];
    private static TextView[] songTitlesTv;
    private static TextView songInfo;
    private static List<Song> playingList;

    public UtilPlayer(ExoPlayer iPlayer, ImageButton[] iButtonPlay, TextView[] iSongTitlesTv, TextView iSongInfo) {
        player = iPlayer;
        buttonPlay = iButtonPlay;
        songTitlesTv = iSongTitlesTv;
        songInfo = iSongInfo;
    }

    public static ExoPlayer getPlayer() {
        return player;
    }

    /**
     * Recibes a songList and a index and starts playing from that index.
     *
     * @param index    the song selected
     * @param songList
     */
    public static void startPlayingList(int index, List<Song> songList) {
        playingList = songList;
        if (player.isPlaying()) {
            player.clearMediaItems();
        }
        loadMediaItems(playingList);
        player.prepare();
        player.seekToDefaultPosition(index);
        player.play();
        for (ImageButton ib : buttonPlay) {
            ib.setImageResource(R.drawable.ic_stop);
        }
    }

    /**
     * Loads the songlist adding an id.
     *
     * @param songList
     */
    private static void loadMediaItems(List<Song> songList) {
        for (int i = 0; i < songList.size(); ++i) {
            MediaItem item = new MediaItem.Builder().setUri(songList.get(i).getSongUri()).setMediaId(String.valueOf(i)).build();
            player.addMediaItem(item);
        }
    }

    /**
     * @param songId
     * @return a song duration long in mileseconds.
     */
    public static long getDuration(int songId) {
        return playingList.get(songId).getDuration();
    }

    public static void updatePlayerMetadata(int index) {
        Song song = playingList.get(index);
        for (TextView songTitle : songTitlesTv)
            songTitle.setText(song.getName());
        songInfo.setText(song.getArtistName());
    }

    /**
     * Transforms a computer time in miliseconds to a hour:min:sec time.
     *
     * @param duration time in miliseconds to convert
     * @return a hour:min:sec time.
     */

    static public String getReadableTime(int duration) {
        String time;
        int hours = duration / (1000 * 60 * 60);
        int minutes = duration % (1000 * 60 * 60) / (1000 * 60);
        int seconds = (((duration % (1000 * 60 * 60))) % (1000 * 60 * 60) % (1000 * 60)) / 1000;

        if (String.valueOf(seconds).length() == 1)
            if (hours < 1) time = minutes + ":0" + seconds;
            else time = hours + ":" + minutes + ":0" + seconds;
        else if (hours < 1) time = minutes + ":" + seconds;
        else time = hours + ":" + minutes + ":" + seconds;

        return time;
    }
}
