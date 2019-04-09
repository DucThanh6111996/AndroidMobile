package com.mta.studyenglish.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mta.studyenglish.R;

/**
 *
 */

public class ExercisesFragment extends Fragment {

    private View rootView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exercise, null);
        mContext = getActivity();

        initViews();

        return rootView;
    }

    private void initViews() {

    }

}
