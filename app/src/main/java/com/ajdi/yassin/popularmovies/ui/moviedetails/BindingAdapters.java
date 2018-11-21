package com.ajdi.yassin.popularmovies.ui.moviedetails;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajdi.yassin.popularmovies.R;
import com.ajdi.yassin.popularmovies.data.local.model.Genre;
import com.ajdi.yassin.popularmovies.utils.Constants;
import com.ajdi.yassin.popularmovies.utils.GlideApp;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import androidx.databinding.BindingAdapter;

/**
 * @author Yassin Ajdi
 * @since 11/11/2018.
 */
public class BindingAdapters {

    @BindingAdapter({"imageUrl", "isBackdrop"})
    public static void bindImage(ImageView imageView, String imagePath, boolean isBackdrop) {
        String baseUrl;
        if (isBackdrop) {
            baseUrl = Constants.BACKDROP_URL;
        } else {
            baseUrl = Constants.IMAGE_URL;
        }

        GlideApp.with(imageView.getContext())
                .load(baseUrl + imagePath)
                .placeholder(R.color.md_grey_200)
                .apply(new RequestOptions().transforms(
                        new CenterCrop(), new RoundedCorners(16)))
                .into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, Boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @BindingAdapter("items")
    public static void setItems(FlexboxLayout view, List<Genre> genres) {
        if (genres == null)
            return;

        // Programmatically create & add genres
        for (Genre genre : genres) {
            TextView textView = new TextView(view.getContext());
            textView.setText(genre.getName());
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 8, 8);
            textView.setLayoutParams(layoutParams);
            view.addView(textView);
        }
    }

}
