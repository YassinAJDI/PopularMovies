package com.ajdi.yassin.popularmovies.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;

import androidx.annotation.ColorRes;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @author Yassin Ajdi.
 */
public class UiUtils {

    // this method is used to tint menu icons for toolbars and other components
    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable itemIcon = item.getIcon();
        Drawable iconWrapper = DrawableCompat.wrap(itemIcon);
        DrawableCompat.setTint(iconWrapper, context.getResources().getColor(color));

        item.setIcon(iconWrapper);
    }

    // convert dip to float
    public static float dipToPixels(Context context, float dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  dipValue, metrics);
    }
}
