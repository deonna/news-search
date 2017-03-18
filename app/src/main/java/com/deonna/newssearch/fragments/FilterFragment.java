package com.deonna.newssearch.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.listeners.ArticleFilterListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterFragment extends DialogFragment {

    private static final String KEY_TITLE = "title";

    @BindView(R.id.dpBeginDate) DatePicker dpBeginDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;

    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;

    @BindView(R.id.tvArts) CheckBox tvArts;
    @BindView(R.id.tvFashion) CheckBox tvFashion;
    @BindView(R.id.tvSports) CheckBox tvSports;

    @BindView(R.id.btnApply) Button btnApply;

    public static FilterFragment newInstance(String title) {

        FilterFragment filterFragment = new FilterFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);

        filterFragment.setArguments(args);

        return filterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return getActivity().getLayoutInflater().inflate(R.layout.fragment_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
 }

    @OnClick(R.id.btnApply)
    public void saveFilterChanges() {

        ArticleFilterListener listener = (ArticleFilterListener) getActivity();

        String year = String.valueOf(dpBeginDate.getYear());
        String month = String.valueOf(dpBeginDate.getMonth());
        String day = String.valueOf(dpBeginDate.getDayOfMonth());

        String beginDate = String.format(Locale.US, "%s%s%s", year, month, day);

        Map<String, Boolean> topics = new HashMap<>();

        TextView textView = (TextView) spSortOrder.getSelectedView();
        String sortOrder = textView.getText().toString();

        String sortOrderParam;

        if (sortOrder.toLowerCase() == "newest first") {
            sortOrderParam = "newest";
        } else {
            sortOrderParam = "oldest";
        }

        topics.put(tvArts.getText().toString().toLowerCase(), cbArts.isChecked());
        topics.put(tvFashion.getText().toString().toLowerCase(), cbFashion.isChecked());
        topics.put(tvSports.getText().toString().toLowerCase(), cbSports.isChecked());

        listener.onApplyFilters(beginDate, sortOrderParam, topics);

    }
}
