package com.mmp.musemusicplayer;

import android.os.Handler;
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
        player.seekToDefaultPosition(index);
        player.play();
        for (ImageButton ib: buttonPlay) {
            ib.setImageResource(R.drawable.ic_stop);
        }
    }

    //Añade la lista de canciones en orden desde el indice indicado
    private static void loadMediaItems(int selectedMediaItem, List<Song> songList){
        for(int i = 0;i < songList.size(); ++i){
            MediaItem item = new MediaItem.Builder().setUri(songList.get(i).getSongUri()).setMediaId(String.valueOf(i)).build();
            player.addMediaItem(item);
        }
    }

    public static long getDuration(int songId){
        return playingList.get(songId).getDuration();
    }

    public static void updatePlayerMetadata(int index){
        Song song = playingList.get(index);
        for(TextView songTitle : songTitlesTv)
            songTitle.setText(song.getName());
        songInfo.setText(song.getArtistName());
    }

    static public String getReadableTime(int duration) {
        String time;
        int hours = duration / (1000 * 60 * 60);
        int minutes = duration % (1000 * 60 * 60) / (1000 * 60);
        int seconds = (((duration % (1000 * 60 * 60))) % (1000 * 60 * 60) % (1000 * 60)) / 1000;

        if (String.valueOf(seconds).length() == 1)
            if (hours < 1) time = minutes + ":0" + seconds;
            else time = hours + ":" + minutes + ":0" + seconds;
        else
        if (hours < 1) time = minutes + ":" + seconds;
        else time = hours + ":" + minutes + ":" + seconds;

        return time;
    }
/*

    TODO Buscar evento para ejecutar esta mierda
    static private void updatePlayerPositionProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying())
                    songCurrentSecondText.setText(getReadableTime(duration));
            }
        }, 1000);
        if(currentTime < duration ) {
            updatePlayerPositionProgress();
        }
    }

    public static void getProgress(){
        //Todo: texto con duracion total de la cancion, texto con segundo actual de reproduccion de la cancion, barra con duracion en porcentaje actual de la cancion.
        //Barra de duración

        //Texto duración
        long totalDuration = player.getContentDuration();
        songTotalDurationText.setText(getReadableTime((int) player.getContentDuration()));
        //texto segundo actual
        songCurrentSecondText.setText(getReadableTime((int) player.getCurrentPosition()));
        updatePlayerPositionProgress();
    }

    static private String getReadableTime(int duration) {
        String time;
        int hours = duration / (1000 * 60 * 60);
        int minutes = duration % (1000 * 60 * 60) / (1000 * 60);
        int seconds = (((duration % (1000 * 60 * 60))) % (1000 * 60 * 60) % (1000 * 60)) / 1000;

        if (String.valueOf(seconds).length() == 1)
            if (hours < 1) time = minutes + ":0" + seconds;
            else time = hours + ":" + minutes + ":0" + seconds;
        else
        if (hours < 1) time = minutes + ":" + seconds;
        else time = hours + ":" + minutes + ":" + seconds;

        return time;
    }*/
}
