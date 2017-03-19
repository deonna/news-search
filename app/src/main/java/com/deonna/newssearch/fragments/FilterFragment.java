package com.deonna.newssearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.deonna.newssearch.R;
import com.deonna.newssearch.listeners.ArticlesFilterListener;
import com.deonna.newssearch.models.ArticlesFilter;
import com.deonna.newssearch.utilities.ArticleLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FilterFragment extends DialogFragment {

    private static final String KEY_TITLE = "title";
    public static final String NAME = "fragment_filter";

    @BindView(R.id.dpBeginDate) DatePicker dpBeginDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;

    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;

    @BindView(R.id.tvArts) TextView tvArts;
    @BindView(R.id.tvFashion) TextView tvFashion;
    @BindView(R.id.tvSports) TextView tvSports;

    @BindView(R.id.btnApply) Button btnApply;

    private ArticlesFilter articlesFilter;

    private Unbinder unbinder;

    public static FilterFragment newInstance() {

        FilterFragment filterFragment = new FilterFragment();

        return filterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        articlesFilter = new ArticlesFilter();

        View fragmentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_filter,
                container);
        unbinder = ButterKnife.bind(this, fragmentView);

//        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout
//                .fragment_filter, container, false);
//
        btnApply.setOnClickListener((view) -> {
            saveFilterChanges();
        });

        return fragmentView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }

    public void saveFilterChanges() {

        ArticlesFilterListener listener = (ArticlesFilterListener) getActivity();

        articlesFilter.setFormattedDate(
                dpBeginDate.getYear(),
                dpBeginDate.getMonth(),
                dpBeginDate.getDayOfMonth()
        );

        //TODO: give option
        // to
        // make begin
        // date null
        // (have no
        // begin date)

        articlesFilter.addTopic(tvArts.getText().toString(), cbArts.isChecked());
        articlesFilter.addTopic(tvFashion.getText().toString(), cbFashion.isChecked());
        articlesFilter.addTopic(tvSports.getText().toString(), cbSports.isChecked());

        articlesFilter.sortOrder = getSortOrderParam(spSortOrder);

        listener.onApplyFilters(articlesFilter);

        dismiss();
    }

    private String getSortOrderParam(Spinner spSort) {

        TextView textView = (TextView) spSort.getSelectedView();
        String sortOrder = textView.getText().toString();

        String sortOrderParam;

        final String NEWEST_FIRST = getResources().getString(R.string.newest_first).toLowerCase();

        if (sortOrder.toLowerCase().equals(NEWEST_FIRST)) {
            sortOrderParam = ArticleLoader.KEY_NEWEST;
        } else {
            sortOrderParam = ArticleLoader.KEY_OLDEST;
        }

        return sortOrderParam;
    }
}
