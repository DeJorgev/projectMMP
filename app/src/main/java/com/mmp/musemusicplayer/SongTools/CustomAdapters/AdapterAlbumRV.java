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
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;

import java.util.List;

// TODO pensar si unificar los adapter
public class AdapterAlbumRV extends RecyclerView.Adapter<AdapterAlbumRV.ViewHolderAlbum> {

    List<Album> allAlbums;
    Fragment fragment;

    public AdapterAlbumRV(List<Album> allAlbums, Fragment fragment) {
        this.allAlbums = allAlbums;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolderAlbum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_album_list_item, parent , false);
        return new ViewHolderAlbum(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlbum holder, int position) {
        holder.populateItem(allAlbums.get(position));
        int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album album = allAlbums.get(i);
                AlbumDetail ad_fragment = AlbumDetail.newInstance(album);
                fragment.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_placeholder, ad_fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allAlbums.size();
    }

    public class ViewHolderAlbum extends RecyclerView.ViewHolder {

        TextView albumName;
        TextView artistName;
        ImageView albumImage;

        public ViewHolderAlbum(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.item_album_name);
            artistName = itemView.findViewById(R.id.item_artist_name);
            albumImage = itemView.findViewById(R.id.item_album_image);
        }

        public void populateItem(Album album){
            albumName.setText(album.getAlbumName());
            artistName.setText(album.getArtistName());

            if (album.getSongs().get(0).getAlbumImageUri()!=null) {
                Glide.with(fragment)
                        .load(album.getSongs().get(0).getAlbumImageUri())
                        .fitCenter()
                        .placeholder(R.drawable.ic_default_artimage)
                        .into(albumImage);
            }

        }
    }
}
