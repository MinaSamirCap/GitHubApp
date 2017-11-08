package com.example.mina.githubrepos.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mina.githubrepos.MyApplication;
import com.example.mina.githubrepos.R;
import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.utils.Constants;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import javax.inject.Inject;

public class RepoDetailsActivity extends AppCompatActivity {

    @Inject
    ArrayList<RepoModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);

        ((MyApplication) getApplication()).getComponent().inject(this);

        RepoModel repoModel = data.get(getIntent().getIntExtra(Constants.REPO_MODEL_POSITION_KEY, 0));

        initViews(repoModel);


    }

    private void initViews(RepoModel repoModel) {

        ImageView ownerImageView;

        TextView nameTextView, descriptionTextView, defaultBranchTextView,
                forkTextView, openIssueTextView, watchersTextView, sizeTextView, urlTextView;

        nameTextView = (TextView) findViewById(R.id.name_value);
        nameTextView.setText(repoModel.getName());

        descriptionTextView = (TextView) findViewById(R.id.desc_value);
        descriptionTextView.setText(repoModel.getDescription());

        defaultBranchTextView = (TextView) findViewById(R.id.default_branch_value);
        defaultBranchTextView.setText(repoModel.getDefaultBranch());

        forkTextView = (TextView) findViewById(R.id.forks_key);
        forkTextView.setText(getString(R.string.forks) + ": " + repoModel.getForks() + "");

        openIssueTextView = (TextView) findViewById(R.id.open_issues_key);
        openIssueTextView.setText(getString(R.string.open_issues) + ": " + repoModel.getOpenIssues() + "");

        watchersTextView = (TextView) findViewById(R.id.watchers_key);
        watchersTextView.setText(getString(R.string.watchers) + ": " + repoModel.getWatchers() + "");

        sizeTextView = (TextView) findViewById(R.id.size_key);
        sizeTextView.setText(getString(R.string.size) + ": " + repoModel.getSize() + "");

        urlTextView = (TextView) findViewById(R.id.url_value);
        urlTextView.setText(repoModel.getUrl());

        ownerImageView = (ImageView) findViewById(R.id.owner_avatar);
        Picasso.with(this).load(repoModel.getOwner().getAvatarUrl()).into(ownerImageView);
    }
}
