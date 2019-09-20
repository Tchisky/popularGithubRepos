package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Fragments.TrendingRepositories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.R;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Recycler.RepoPagedAdapter;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.ViewModel.RepoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TrendingReposFragment extends Fragment {

    private RepoPagedAdapter recyclerAdapter;
    private RepoViewModel viewModel;
    private Context context;

    private Unbinder unbinder;

    public TrendingReposFragment() {
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        context = container.getContext();

        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.fragment_trending_repos, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // adding line separator between each item of the recycler
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        // adding animation for each item in the recycler while being displayed
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set recycler layoutManager to be linear vertically
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(false);

        // init adapter
        recyclerAdapter = new RepoPagedAdapter();
    }


    @Override
    public void onResume() {
        super.onResume();

        // init view model
        viewModel = ViewModelProviders.of(this).get(RepoViewModel.class);

        // observe changes on data
        viewModel.getAllReposPaged().observe(this,
                repos -> recyclerAdapter.submitList(repos));

        recyclerView.setAdapter(recyclerAdapter);

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * swipe left|right to delete item
     */
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                viewModel.delete(recyclerAdapter.getCurrentList().get(viewHolder.getAdapterPosition()));
            }
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        recyclerAdapter = null;
        recyclerView = null;
        viewModel = null;

    }
}
