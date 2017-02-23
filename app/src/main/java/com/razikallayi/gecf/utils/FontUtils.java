package com.razikallayi.gecf.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by WHYTE5 on 22 Feb 2017.
 */

public class FontUtils {
    public static final String ROOT = "fonts/";
    public static final String FONTAWSOME = ROOT + "fontawesome-webfont.ttf";
    public static final String ROBOTO_SLAB_REGULAR = ROOT + "RobotoSlab-Regular.ttf";
    public static final String ROBOTO_SLAB_LIGHT = ROOT + "RobotoSlab-Light.ttf";
    public static final String MUSEO_LIGHT = ROOT + "Museo-300.otf";
    public static final String MUSEO_BOLD = ROOT + "Museo-500.otf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(),font);
    }

    //tv.setTypeface(getApplicationContext(),FontUtils.getTypeface(FontUtils.FONTAWSOME));

/*
    public static void markAsIconContainer(View v, Typeface typeface){
        if (v instanceof ViewGroup){
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++){
                View child = vg.getChildAt(i);
                markAsIconContainer(child,typeface);
            }
        }else if (v instanceof TextView){
            ((TextView) v).setTypeface(typeface);
        }
    }*/
}
