package com.ajdi.yassin.popularmovies.ui.moviedetails.cast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.local.model.Cast;
import com.ajdi.yassin.popularmovies.databinding.ItemCastBinding;
import com.ajdi.yassin.popularmovies.utils.Constants;
import com.ajdi.yassin.popularmovies.utils.GlideApp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
public class CastViewHolder extends RecyclerView.ViewHolder {

    private ItemCastBinding binding;

    private Context context;

    public CastViewHolder(@NonNull ItemCastBinding binding, Context context) {
        super(binding.getRoot());

        this.binding = binding;
        this.context = context;
    }

    public void bindTo(final Cast cast) {
        String proifleImage =
                Constants.IMAGE_BASE_URL + Constants.PROFILE_SIZE_W185 + cast.getProfileImagePath();
        GlideApp.with(context)
                .load(proifleImage)
                .placeholder(R.color.md_grey_200)
                .into(binding.imageCastProfile);

        binding.textCastName.setText(cast.getActorName());
        binding.textCharacterName.setText(cast.getCharacterName());

        binding.executePendingBindings();
    }

    public static CastViewHolder create(ViewGroup parent) {
        // Inflate
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Create the binding
        ItemCastBinding binding =
                ItemCastBinding.inflate(layoutInflater, parent, false);
        return new CastViewHolder(binding, parent.getContext());
    }
}
