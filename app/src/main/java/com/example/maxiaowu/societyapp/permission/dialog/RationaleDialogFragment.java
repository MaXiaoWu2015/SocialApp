package com.example.maxiaowu.societyapp.permission.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


/**
 * Created by matingting on 2018/2/6.
 */

public class RationaleDialogFragment extends DialogFragment {
    public static final String TAG = "RationaleDialogFragment";

    private boolean mStateSaved = false;

    public static RationaleDialogFragment newInstance(String rationale, String positiviButtonText,
                            String negativeButtonText, int theme,
                            int requestCode, String[] perms){

        RationaleDialogFragment dialogFragment = new RationaleDialogFragment();

        RationaleDialogConfig dialogConfig = new RationaleDialogConfig(positiviButtonText,
                negativeButtonText,rationale,theme,requestCode,perms);

        dialogFragment.setArguments(dialogConfig.toBundle());

        return dialogFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mStateSaved = true;
        super.onSaveInstanceState(outState);
    }

    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            if (manager.isStateSaved()){
                return;
            }
        }

        if (mStateSaved){
            return;
        }

        show(manager,tag);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);

        RationaleDialogConfig config = new RationaleDialogConfig(getArguments());
        RationaleDialogClickListener listener = new RationaleDialogClickListener(this,config.requestCode,config.permissions);

        return config.createSupportDialog(getActivity(),listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
