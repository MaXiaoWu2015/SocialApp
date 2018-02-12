package com.example.maxiaowu.societyapp.permission.dialog;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by matingting on 2018/2/6.
 */

public class RationaleDialogFragmentCompat extends AppCompatDialogFragment {
    public static final String TAG = "RationaleDialogFragment";


    public static RationaleDialogFragmentCompat newInstance(String rationale, String positiviButtonText,
                                                            String negativeButtonText, int theme,
                                                            int requestCode, String[] perms){

        RationaleDialogFragmentCompat dialogFragment = new RationaleDialogFragmentCompat();

        RationaleDialogConfig dialogConfig = new RationaleDialogConfig(positiviButtonText,
                negativeButtonText,rationale,theme,requestCode,perms);

        dialogFragment.setArguments(dialogConfig.toBundle());

        return dialogFragment;
    }


    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag){
            if (manager.isStateSaved()){
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
}
