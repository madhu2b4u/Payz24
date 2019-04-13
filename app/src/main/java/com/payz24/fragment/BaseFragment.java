package com.payz24.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.payz24.R;
import com.payz24.utils.DialogUtils;

import java.util.Map;

/**
 * Created by mahesh on 1/19/2018.
 */

public class BaseFragment extends Fragment {

    private Dialog progressDialog;

    public void showProgressBar() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(getActivity(), getString(R.string.loading_please_wait));
        } else {
            closeProgressbar();
            progressDialog = null;
            progressDialog = DialogUtils.getDialogUtilsInstance().progressDialog(getActivity(), getString(R.string.loading_please_wait));
        }
    }

    public void closeProgressbar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
