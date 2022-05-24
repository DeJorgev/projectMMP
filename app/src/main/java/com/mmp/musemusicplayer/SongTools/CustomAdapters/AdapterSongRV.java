package com.mmp.musemusicplayer.SongTools.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.mmp.musemusicplayer.MainActivity;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.UtilPlayer;

import java.util.List;



public class AdapterSongRV extends RecyclerView.Adapter <AdapterSongRV.ViewHolderSong> {
    private List<Song> songs;
    private Fragment f;
    private ExoPlayer player = MainActivity.getExoPlayer();
    private RecyclerView rv;

    public AdapterSongRV(List<Song> songs, Fragment f,RecyclerView rv) {
        this.songs = songs;
        this.f = f;
        this.rv = rv;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, parent , false);
        return new ViewHolderSong(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSong holder, int position) {
        holder.populateItem(songs.get(position));
        int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilPlayer.startPlayingList(i, songs);
            }
        });
        player.addListener(new Player.Listener() {
            int oldmedia = -1;

            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                Player.Listener.super.onMediaItemTransition(mediaItem, reason);

                if (mediaItem != null) {
                    if (oldmedia < Integer.parseInt(mediaItem.mediaId))
                        rv.scrollToPosition(Integer.parseInt(mediaItem.mediaId) + 3);
                    else
                        rv.scrollToPosition(Integer.parseInt(mediaItem.mediaId) - 3);

                    if (oldmedia != -1 && rv.findViewHolderForAdapterPosition(oldmedia) != null)
                        rv.findViewHolderForAdapterPosition(oldmedia).itemView.setSelected(false);

                    UtilPlayer.updatePlayerMetadata(Integer.parseInt(mediaItem.mediaId));

                    rv.post(new Runnable() {
                        @Override
                        public void run() {
                            rv.findViewHolderForAdapterPosition(Integer.parseInt(mediaItem.mediaId)).itemView.setSelected(true);
                        }
                    });

                    oldmedia = Integer.parseInt(mediaItem.mediaId);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolderSong extends RecyclerView.ViewHolder {

        TextView songName;
        TextView songInfo;
        ImageView albumImage;

        public ViewHolderSong(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.item_song_name);
            songInfo = itemView.findViewById(R.id.item_info_name);
            albumImage = itemView.findViewById(R.id.item_album_art);
        }

        public void populateItem(Song song){
            songName.setText(song.getName());
            songInfo.setText(song.getAlbumName() + "-" + song.getArtistName())  ;

            if (song.getAlbumImageUri()!=null) {
                Glide.with(f)
                        .load(song.getAlbumImageUri())
                        .fitCenter()
                        .placeholder(R.drawable.ic_default_artimage)
                        .into(albumImage);
            }


        }
    }

}
