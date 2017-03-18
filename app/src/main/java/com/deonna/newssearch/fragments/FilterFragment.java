package com.deonna.newssearch.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.deonna.newssearch.R;

import butterknife.ButterKnife;

public class FilterFragment extends DialogFragment {

    private static final String KEY_TITLE = "title";

    public static FilterFragment newInstance(String title) {

        FilterFragment filterFragment = new FilterFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);

        filterFragment.setArguments(args);

        return filterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.fragment_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox cbArts = ButterKnife.findById(view, R.id.cbArts);
        CheckBox cbFashion = ButterKnife.findById(view, R.id.cbFashion);
        CheckBox cbSports = ButterKnife.findById(view, R.id.cbSports);
    }
}
