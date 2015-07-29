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

/**
 * Created by GASwipper Gcc on 7/28/2015.
 */
public class HelpDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.help_dialog, null))
                .setNeutralButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
