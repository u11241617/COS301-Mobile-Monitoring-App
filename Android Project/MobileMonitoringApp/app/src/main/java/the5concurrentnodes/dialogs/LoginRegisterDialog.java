package the5concurrentnodes.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import the5concurrentnodes.mobilemonitoringapp.AboutActivity;
import the5concurrentnodes.mobilemonitoringapp.R;

public class LoginRegisterDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.login_register_dialog, null))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        getActivity().finish();
                    }
                });

        return builder.create();
    }
}
