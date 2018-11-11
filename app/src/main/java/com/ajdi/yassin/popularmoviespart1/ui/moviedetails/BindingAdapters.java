package com.ajdi.yassin.popularmoviespart1.ui.moviedetails;

import android.view.View;
import android.widget.ImageView;

import com.ajdi.yassin.popularmoviespart1.R;
import com.ajdi.yassin.popularmoviespart1.utils.Constants;
import com.ajdi.yassin.popularmoviespart1.utils.GlideApp;

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
                .into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, Boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

}
