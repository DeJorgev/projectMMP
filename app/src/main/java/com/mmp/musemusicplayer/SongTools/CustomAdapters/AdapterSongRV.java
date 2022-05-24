package com.mmp.musemusicplayer.SongTools.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mmp.musemusicplayer.Fragments.AlbumDetail;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.Album;
import com.mmp.musemusicplayer.SongTools.Song;
import com.mmp.musemusicplayer.UtilPlayer;

import java.util.List;



public class AdapterSongRV extends RecyclerView.Adapter <AdapterSongRV.ViewHolderSong> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private List<Song> songs;
    private Fragment f;



    public AdapterSongRV(List<Song> songs, Fragment f) {
        this.songs = songs;
        this.f = f;
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
        holder.itemView.setSelected(selectedPos == position);
        int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilPlayer.startPlayingList(i, songs);
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
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
