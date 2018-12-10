package com.ajdi.yassin.popularmovies.ui.moviedetails.cast;

import android.view.ViewGroup;

import com.ajdi.yassin.popularmovies.data.local.model.Cast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cast> castList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CastViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cast cast = castList.get(position);
        ((CastViewHolder) holder).bindTo(cast);
    }

    @Override
    public int getItemCount() {
        return castList != null ? castList.size() : 0;
    }

    public void submitList(List<Cast> casts) {
        castList = casts;
        notifyDataSetChanged();
    }
}

