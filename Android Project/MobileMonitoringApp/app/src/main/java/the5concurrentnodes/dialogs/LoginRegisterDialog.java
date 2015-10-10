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

        builder.setMessage(R.string.login_register_dialog_content)
                .setTitle(R.string.login_register_dialog_title)
                .setNeutralButton(R.string.got_it_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        getActivity().finish();
                    }
                });

        return builder.create();
    }
}
