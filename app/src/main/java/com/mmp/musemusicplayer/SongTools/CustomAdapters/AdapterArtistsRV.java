package com.mmp.musemusicplayer.SongTools.CustomAdapters;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mmp.musemusicplayer.Fragments.AllAlbumFragment;
import com.mmp.musemusicplayer.Fragments.ArtistAlbumsFragment;
import com.mmp.musemusicplayer.R;
import com.mmp.musemusicplayer.SongTools.DataContainers.Album;
import com.mmp.musemusicplayer.SongTools.DataContainers.Artist;

import java.io.IOException;
import java.util.List;

/**
 *  An adapter that loads a customized item view to use to display the artists into a recycler view.
 *  Needs a List of artists and the fragment in which is inflated.
 *
 * @author Borja Avalos, Jorge Garcia
 * @version 1.1.0
 **/
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
                ArtistAlbumsFragment ad_fragment = ArtistAlbumsFragment.newInstance(allArtists.get(i));
                fragment.getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                        )
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
        ImageView artistImage;

        public ViewHolderArtists(@NonNull View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.item_album_name);
            artistImage = itemView.findViewById(R.id.item_artist_image);
        }

        public void populateItem(Artist artist){
            artistName.setText(artist.getArtistName());

            if (artist.getImageUri()!= null) {
                Glide.with(fragment)
                        .load(artist.getImageUri())
                        .fitCenter()
                        .placeholder(R.drawable.ic_default_artimage)
                        .into(artistImage);
            }

        }
    }
}