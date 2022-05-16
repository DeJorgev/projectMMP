package com.mmp.musemusicplayer;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.mmp.musemusicplayer.SongTools.Song;

import java.util.List;

public class UtilPlayer {

    private static ExoPlayer player;
    private static ImageButton buttonPlay[];
    private static TextView[] songTitlesTv;
    private static TextView songInfo;
    private static List<Song> playingList;

    public UtilPlayer(ExoPlayer iPlayer, ImageButton[] iButtonPlay, TextView[] iSongTitlesTv, TextView iSongInfo){
        player = iPlayer;
        buttonPlay = iButtonPlay;
        songTitlesTv = iSongTitlesTv;
        songInfo = iSongInfo;
    }

    public static ExoPlayer getPlayer(){
        return player;
    }

    // Recibe una lista y un indice.
    // Comienza a reproducir toda la lista desde el indice seleccionado,
    // añadiendo al final las canciones que estuviesen antes del indice
    public static void startPlayingList(int index, List<Song> songList){
        playingList = songList;
        if(player.isPlaying()){
            player.clearMediaItems();
        }
        loadMediaItems(index, playingList);
        player.prepare();
        player.seekToDefaultPosition(0);
        player.play();
        for (ImageButton ib: buttonPlay) {
            ib.setImageResource(R.drawable.ic_stop);
        }
    }

    //Añade la lista de canciones en orden desde el indice indicado
    private static void loadMediaItems(int selectedMediaItem, List<Song> songList){
        for(int i = 0;i < songList.size(); ++i){
            int selec = i + selectedMediaItem;
            MediaItem item;
            if(selec < songList.size()){
                item = new MediaItem.Builder().setUri(songList.get(selec).getSongUri()).setMediaId(String.valueOf(selec)).build();
            }else{
                item = new MediaItem.Builder().setUri(songList.get(selec-songList.size()).getSongUri()).setMediaId(String.valueOf(selec-songList.size())).build();
            }
            player.addMediaItem(item);
        }
    }

    public static void updatePlayerMetadata(int index){
        Song song = playingList.get(index);
        for(TextView songTitle : songTitlesTv)
            songTitle.setText(song.getName());
        songInfo.setText(song.getArtistName());
    }
}
