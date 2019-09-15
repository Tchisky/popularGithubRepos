package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.Recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.R;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.ViewHolder.RepoHolder;

public class RepoPagedAdapter extends PagedListAdapter<Repo, RepoHolder> {

    public RepoPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.recycler_item_repo,
                                parent, false),
                parent.getContext()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder holder, int position) {

        holder.bindData(getItem(position));
    }


    private static DiffUtil.ItemCallback<Repo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Repo>() {

                @Override
                public boolean areItemsTheSame(Repo oldItem, Repo newItem) {
                    // The ID property identifies when items are the same.
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Repo oldItem, Repo newItem) {
                    // Don't use the "==" operator here. Either implement and use .equals(),
                    // or write custom data comparison logic here.
                    return oldItem.getDescription().equals(newItem.getDescription())
                            && oldItem.getId() == newItem.getId()
                            && oldItem.getName().equals(newItem.getName())
                            && oldItem.getOwnerAvatar().equals(newItem.getOwnerAvatar())
                            && oldItem.getOwnerName().equals(newItem.getOwnerName())
                            && oldItem.getStars() == newItem.getStars();
                }
            };
}
