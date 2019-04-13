package com.payz24.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.payz24.R;
import com.payz24.activities.ChangePasswordActivity;
import com.payz24.activities.EditProfileActivity;
import com.payz24.activities.LoginActivity;
import com.payz24.activities.MyProfileActivity;
import com.payz24.activities.ScratchCard;
import com.payz24.utils.PreferenceConnector;

/**
 * Created by mahesh on 3/6/2018.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvProfile, tvEditProfile, tvChangePassword, tvLogout;
    private TextView tvVersion,refer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile_fragment, container, false);
        initializeUi(view);
        initializeListeners();
        return view;
    }

    private void initializeUi(View view) {
        tvProfile = view.findViewById(R.id.tvProfile);
        tvEditProfile = view.findViewById(R.id.tvEditProfile);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);
        refer = view.findViewById(R.id.refer);
        tvLogout = view.findViewById(R.id.tvLogout);
        tvVersion = view.findViewById(R.id.tvVersion);
        tvVersion.setText(getString(R.string.version) + "  :" + "  " + "1");
    }

    private void initializeListeners() {
        tvProfile.setOnClickListener(this);
        tvEditProfile.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        refer.setOnClickListener(this);
        tvLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvProfile:
                Intent myProfileIntent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(myProfileIntent);
                break;
            case R.id.tvEditProfile:
                Intent editProfileIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(editProfileIntent);
                break;
            case R.id.tvChangePassword:
                Intent changePasswordIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;

            case R.id.refer:
                refer();
                break;

            /*case R.id.scratchCard:

            *//*    Intent scratch = new Intent(getActivity(), ScratchCard.class);
                startActivity(scratch);*//*


                break;*/


            case R.id.tvLogout:
                showLogoutAlertDialog();
                break;
            default:
                break;
        }
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public void refer()
    {


        String refer = PreferenceConnector.readString(getActivity(), getString(R.string.promo), "");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String sAux = "This is test with unique id "+refer+"/n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.payz24";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void showLogoutAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(getResources().getString(R.string.Are_you_sure_do_you_want_to_logout));
        alertDialogBuilder.setPositiveButton(" Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                logoutFromApplication();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void logoutFromApplication() {
        PreferenceConnector.clearData(getActivity());
        goToLoginScreen();
    }

    private void goToLoginScreen() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }
}
