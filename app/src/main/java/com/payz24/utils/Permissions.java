package com.payz24.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.payz24.activities.recharge.NewMobileRechargeActivity;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


/**
 * Created by mahesh on 1/8/2018.
 */

public class Permissions {


    public static boolean checkPermissionForAccessExternalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        else
            return true;
    }

    public static void requestPermissionForAccessExternalStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.REQUEST_CODE_FOR_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    public static boolean checkPermissionForReadContacts(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
        else
            return true;
    }

    public static void requestPermissionForReadContacts(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, Constants.REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION);


        }
    }
}
