package com.payz24.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.payz24.R;


/**
 * Created by mahesh on 10/22/2017.
 */

public class CustomButton extends Button {
    /**
     * @param context context as parameter
     */
    public CustomButton(Context context) {
        super(context);
    }

    /**
     * @param context context as parameter
     * @param attrs   AttributeSet as parameter
     */
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    /**
     * @param context      context as parameter
     * @param attrs        AttributeSet as parameter
     * @param defStyleAttr attribute style as parameter
     */
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    /**
     * @param ctx   context
     * @param attrs attributes
     */
    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.payz24);
        String customFont = typedArray.getString(R.styleable.payz24_custom_font);
        setCustomFontAsset(ctx, customFont);
        typedArray.recycle();
    }

    /**
     * to set custom font to button
     *
     * @param ctx   context as parameter
     * @param asset custom font style from assets
     * @return custom font
     */
    public boolean setCustomFontAsset(Context ctx, String asset) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(typeface);
        return true;
    }
}