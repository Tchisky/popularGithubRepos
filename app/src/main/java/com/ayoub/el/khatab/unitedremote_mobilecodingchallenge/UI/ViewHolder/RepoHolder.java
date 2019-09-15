package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.UI.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model.Repo;
import com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RepoHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.repo_name)           TextView repoName;
    @BindView(R.id.repo_description)    TextView repoDescription;
    @BindView(R.id.repo_stars)          TextView repoStars;
    @BindView(R.id.owner_name)          TextView ownerName;
    @BindView(R.id.owner_avatar)        CircleImageView ownerAvatar;

    // to be used with Glide down below inside bindData()
    private Context context;

    public RepoHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.context = context;

        // bind views
        ButterKnife.bind(this, itemView);
    }

    /**
     * bind data to views
     *
     * @param repo to display in the view
     */
    public void bindData(Repo repo) {

        if (repo != null) {
            repoName.setText(repo.getName());
            repoDescription.setText(repo.getDescription());
            repoStars.setText(String.valueOf(repo.getStars()));
            ownerName.setText(repo.getOwnerName());

            Glide
                    .with(context)
                    .load(repo.getOwnerAvatar())
                    .placeholder(R.drawable.avatar_place_holder)
                    .centerCrop()
                    .into(ownerAvatar);
        }
    }
}
