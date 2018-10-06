package com.ajdi.yassin.popularmoviespart1.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import androidx.annotation.ColorRes;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @author Yassin Ajdi.
 */
public class UiUtils {

    // this method is used to tint menu icons for toolbars
    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable itemIcon = item.getIcon();
        Drawable iconWrapper = DrawableCompat.wrap(itemIcon);
        DrawableCompat.setTint(iconWrapper, context.getResources().getColor(color));

        item.setIcon(iconWrapper);
    }
}
