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
import com.deonna.newssearch.utilities.ArticleLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterFragment extends DialogFragment {

    private static final String KEY_TITLE = "title";

    private static final String FORMAT_PATTERN = "yyyyMMdd";

    @BindView(R.id.dpBeginDate) DatePicker dpBeginDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;

    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;

    @BindView(R.id.tvArts) TextView tvArts;
    @BindView(R.id.tvFashion) TextView tvFashion;
    @BindView(R.id.tvSports) TextView tvSports;

    @BindView(R.id.btnApply) Button btnApply;

    private Unbinder unbinder;

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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_filter, container);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnApply)
    public void saveFilterChanges() {

        ArticleFilterListener listener = (ArticleFilterListener) getActivity();

        String beginDate = getFormattedDate(dpBeginDate); //TODO: give option to make begin date null (have no begin date)

        Map<String, Boolean> topics = new HashMap<>();

        String sortOrderParam = getSortOrderParam(spSortOrder);

        topics.put(tvArts.getText().toString().toLowerCase(), cbArts.isChecked());
        topics.put(tvFashion.getText().toString().toLowerCase(), cbFashion.isChecked());
        topics.put(tvSports.getText().toString().toLowerCase(), cbSports.isChecked());

        listener.onApplyFilters(beginDate, sortOrderParam, topics);

        dismiss();
    }

    private String getSortOrderParam(Spinner spSort) {

        TextView textView = (TextView) spSort.getSelectedView();
        String sortOrder = textView.getText().toString();

        String sortOrderParam;

        if (sortOrder.toLowerCase() == getResources().getString(R.string.newest_first).toLowerCase()) {
            sortOrderParam = ArticleLoader.KEY_NEWEST;
        } else {
            sortOrderParam = ArticleLoader.KEY_OLDEST;
        }

        return sortOrderParam;
    }

    private static String getFormattedDate(DatePicker dpDate) {

        int year = dpDate.getYear();
        int month = dpDate.getMonth();
        int day = dpDate.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Date date = new Date(calendar.getTimeInMillis());

        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_PATTERN, Locale.US);

        return formatter.format(date);
    }
}
