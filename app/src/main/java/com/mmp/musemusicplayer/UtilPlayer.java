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
    private static TextView songTitleTv;
    private static List<Song> playingList;

    public UtilPlayer(ExoPlayer iPlayer, ImageButton[] iButtonPlay, TextView iSongTitleTv){
        player = iPlayer;
        buttonPlay = iButtonPlay;
        songTitleTv = iSongTitleTv;
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
        loadMediaItems(index, songList);
        player.prepare();
        player.seekToDefaultPosition(0);
        player.play();
        for (ImageButton ib: buttonPlay) {
            ib.setImageResource(R.mipmap.pause3x);
        }
    }

    //Añade la lista de canciones en orden desde el indice indicado
    private static void loadMediaItems(int selectedMediaItem, List<com.mmp.musemusicplayer.SongTools.Song> songList){
        for(int i = 0;i < songList.size(); ++i){
            int selec = i + selectedMediaItem;
            if(selec < songList.size()){
                MediaItem item = MediaItem.fromUri(songList.get(selec).getSongUri());
                player.addMediaItem(MediaItem.fromUri(songList.get(selec).getSongUri()));
            }else{
                player.addMediaItem(MediaItem.fromUri(songList.get(i-selectedMediaItem).getSongUri()));
            }
        }
    }

    public static void updatePlayerMetadata(int index){
        Song song = playingList.get(index);
        songTitleTv.setText(song.getName());
    }
}
