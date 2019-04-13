package com.payz24.utils;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by mahesh on 1/18/2018.
 */
public class TabInfo
{
    @SuppressWarnings("unused")
	private final String tag;
    public final Class<?> clss;
    public final Bundle args;

    public TabInfo(String _tag, Class<?> _class, Bundle _args) {
        tag = _tag;
        clss = _class;
        args = _args;
    }



}