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
    private static final String KEY_ARTICLES_FILTER = "articles_filter";

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

    public static FilterFragment newInstance(ArticlesFilter articlesFilter) {

        FilterFragment filterFragment = new FilterFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_ARTICLES_FILTER, articlesFilter);

        filterFragment.setArguments(args);

        return filterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        articlesFilter = getArguments().getParcelable(KEY_ARTICLES_FILTER);

        View fragmentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_filter,
                container);
        unbinder = ButterKnife.bind(this, fragmentView);

        intializeViewsFromPreviousFilter();

        btnApply.setOnClickListener((view) -> {
            saveFilterChanges();
        });

        return fragmentView;
    }

    public void intializeViewsFromPreviousFilter() {

        spSortOrder.setSelection(articlesFilter.sortOrder);

        cbArts.setChecked(articlesFilter.isTopicSelected(tvArts.getText().toString()));
        cbFashion.setChecked(articlesFilter.isTopicSelected(tvFashion.getText().toString()));
        cbSports.setChecked(articlesFilter.isTopicSelected(tvSports.getText().toString()));
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

        articlesFilter.addTopic(tvArts.getText().toString(), cbArts.isChecked());
        articlesFilter.addTopic(tvFashion.getText().toString(), cbFashion.isChecked());
        articlesFilter.addTopic(tvSports.getText().toString(), cbSports.isChecked());

        setSortOrder();

        listener.onApplyFilters(articlesFilter);

        dismiss();
    }

    private void setSortOrder() {

        TextView textView = (TextView) spSortOrder.getSelectedView();
        String sortOrder = textView.getText().toString();

        String sortOrderParam;

        final String NEWEST_FIRST = getResources().getString(R.string.newest_first).toLowerCase();

        if (sortOrder.toLowerCase().equals(NEWEST_FIRST)) {
            sortOrderParam = ArticleLoader.KEY_NEWEST;
            articlesFilter.setSortOrder(ArticlesFilter.SORT_NEWEST, sortOrderParam);
        } else {
            sortOrderParam = ArticleLoader.KEY_OLDEST;
            articlesFilter.setSortOrder(ArticlesFilter.SORT_OLDEST, sortOrderParam);
        }

    }
}
