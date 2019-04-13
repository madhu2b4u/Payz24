package com.payz24.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mahesh on 1/8/2018.
 */

public class DialogUtils {

    private static DialogUtils dialogUtils = null;

    private DialogUtils() {

    }

    public static DialogUtils getDialogUtilsInstance() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }

    /**
     * to display toast message
     *
     * @param context context of activity or fragment
     * @param message Toast message
     */

    public void displayToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        if (textView != null)
            textView.setGravity(Gravity.CENTER);
        toast.show();
    }

    /**
     * to show alertDialog just clicking on ok button to close
     *
     * @param context context
     */
    public Dialog alertDialogOk(final Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * to create and return the dialog
     *
     * @param context context as parameter
     * @param title   title of dialog
     * @return dialog object
     */

    public Dialog progressDialog(Context context, String title) {
        Dialog dialog = null;
        try {
            dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_dialog_builder);
            dialog.setCancelable(false);
            TextView textView = dialog.findViewById(R.id.progressMessage);
            textView.setText(title);
            dialog.show();
        } catch (Exception exception) {
            Logger.getLogger(DialogUtils.class.getName()).log(Level.OFF, null, exception);
        }
        return dialog;
    }

}
