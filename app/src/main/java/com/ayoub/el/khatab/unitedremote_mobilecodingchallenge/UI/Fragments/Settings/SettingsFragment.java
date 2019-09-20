package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Fragments.Settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.R;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.ViewModel.RepoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.getItemsPerPage;
import static com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Utility.Utility.saveValueInSharedPreferences;

public class SettingsFragment extends Fragment {

    private Context context;
    private int itemsPerPage;
    private Unbinder unbinder;
    private RepoViewModel viewModel;

    @BindView(R.id.iterms_per_page_picker)
    NumberPicker numberPicker;
    @BindView(R.id.clear_cache)
    ImageView clearCache;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = container.getContext();

        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_settings, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        itemsPerPage = getItemsPerPage(getActivity());

        numberPicker.setMaxValue(30);
        numberPicker.setMinValue(10);
        numberPicker.setValue(itemsPerPage);

        // init view model
        viewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        numberPicker.setOnScrollListener((numberPicker, scrollState) -> {

            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                saveValueInSharedPreferences(
                        getActivity(),
                        SHARED_PREFERENCES_ITEMS_PER_PAGE_KEY,
                        numberPicker.getValue());
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
            }

        });

        clearCache.setOnClickListener(clearCacheClick);

    }

    /**
     * clear the cache database
     */
    private View.OnClickListener clearCacheClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewModel.deleteAllRepos();
            Toast.makeText(context, "All repos deleted!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context = null;
        unbinder.unbind();
        viewModel = null;
    }
}
