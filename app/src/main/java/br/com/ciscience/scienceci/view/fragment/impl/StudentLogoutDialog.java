package br.com.ciscience.scienceci.view.fragment.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.view.fragment.IDialog;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public class StudentLogoutDialog extends AppCompatDialogFragment implements DialogInterface.OnClickListener {

    private static final int DIALOG_ID = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.logout_dialog_title)
                .setMessage(R.string.logout_dialog_message)
                .setPositiveButton(R.string.logout_dialog_positive_button, this)
                .setNegativeButton(R.string.logout_dialog_negative_button, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Activity activity = getActivity();
        if (activity instanceof IDialog) {
            IDialog listener = (IDialog) activity;
            listener.onClickDialog(DIALOG_ID, which);
        }
    }
}
