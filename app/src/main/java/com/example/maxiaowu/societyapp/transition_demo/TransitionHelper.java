package com.example.maxiaowu.societyapp.transition_demo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by matingting on 2018/1/26.
 */

class TransitionHelper {
    public static Pair<View, String>[] createTransitionParticipants(@NonNull Activity activity, boolean includeStatusBar, Pair<View,String>... otherParticipants) {

        View decorView = activity.getWindow().getDecorView();
        View statusBar = null;
        if (includeStatusBar){
             statusBar = decorView.findViewById(android.R.id.statusBarBackground);
        }

        View navBar = decorView.findViewById(android.R.id.navigationBarBackground);

        List<Pair<View,String>> pairs = new ArrayList<>();
        addNonNullViewToTransitionParticipants(statusBar,pairs);
        addNonNullViewToTransitionParticipants(navBar,pairs);

        if (otherParticipants != null && !(otherParticipants.length ==1 && otherParticipants[0]!=null)){
            pairs.addAll(Arrays.asList(otherParticipants));
        }

        return pairs.toArray(new Pair[pairs.size()]);
    }

    private static void addNonNullViewToTransitionParticipants(View view, List<Pair<View, String>> pairs) {
        if (view != null) {

            pairs.add(new Pair<View, String>(view, ViewCompat.getTransitionName(view)));

        }
    }
}
