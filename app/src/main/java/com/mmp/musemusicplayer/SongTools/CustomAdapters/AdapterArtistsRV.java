package com.mmp.musemusicplayer.SongTools.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mmp.musemusicplayer.Fragments.AllAlbumFragment;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Artist;

import java.util.List;

// TODO pensar si unificar los adapter
public class AdapterArtistsRV extends RecyclerView.Adapter<AdapterArtistsRV.ViewHolderArtists> {

    List<Artist> allArtists;
    Fragment fragment;

    public AdapterArtistsRV(List<Artist> allArtists, Fragment fragment) {
        this.allArtists = allArtists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolderArtists onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_artists_list_item, parent , false);
        return new ViewHolderArtists(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArtists holder, int position) {
        holder.populateItem(allArtists.get(position));
        int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Album> albums = allArtists.get(i).getAlbumList();
                AllAlbumFragment ad_fragment = AllAlbumFragment.newInstance(albums);
                fragment.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_placeholder, ad_fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allArtists.size();
    }

    public class ViewHolderArtists extends RecyclerView.ViewHolder {

        TextView artistName;

        public ViewHolderArtists(@NonNull View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.item_album_name);
        }

        public void populateItem(Artist artist){
            artistName.setText(artist.getArtistName());

            /*if (album.getSongs().get(0).getAlbumImageUri()!=null) {
                Glide.with(f)
                        .load(album.getSongs().get(0).getAlbumImageUri())
                        .fitCenter()
                        .placeholder(R.drawable.ic_default_artimage)
                        .into(albumImage);
            }*/

        }
    }
}