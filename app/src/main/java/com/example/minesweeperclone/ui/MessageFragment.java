package com.example.minesweeperclone.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.minesweeperclone.R;

public class MessageFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

       View v = LayoutInflater.from(getActivity())
               .inflate(R.layout.message_layout,null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*TextView tv = (TextView) getActivity().findViewById(R.id.message);
                tv.setText ("CONGRATULATIONS!");*/
                getActivity().finish();
            }

        };


        return new AlertDialog.Builder(getActivity()).setTitle("").setView(v).setPositiveButton(android.R.string.ok, listener).create();














   //     return super.onCreateDialog(savedInstanceState);
    }
}
