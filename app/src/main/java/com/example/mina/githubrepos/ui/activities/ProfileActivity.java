package com.example.mina.githubrepos.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mina.githubrepos.R;
import com.example.mina.githubrepos.models.UserModel;
import com.example.mina.githubrepos.utils.Constants;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserModel userModel = (UserModel) getIntent().getExtras().getSerializable(Constants.USER_MODEL_KEY);
        initViews(userModel);
    }

    private void initViews(UserModel userModel) {

        ImageView profileImageView;

        TextView bioTextView, loginNameTextView, idTextView, urlTextView, htmlUrlTextView,
                nameTextView, emailTextView, publicReposTextView, publicGistsTextView,
                followersTextView, followingTextView, createdAtTextView, updatedAtTextView;

        profileImageView = findViewById(R.id.profile_image_view);
        Picasso.with(this).load(userModel.getAvatarUrl()).into(profileImageView);

        bioTextView = findViewById(R.id.bio_text_view);
        bioTextView.setText(userModel.getBio());

        loginNameTextView = findViewById(R.id.login_name_text_view);
        loginNameTextView.setText(userModel.getLogin());

        idTextView = findViewById(R.id.id_text_view);
        idTextView.setText(getString(R.string.id) + " " + userModel.getId());

        urlTextView = findViewById(R.id.url_text_view);
        urlTextView.setText(userModel.getUrl());

        htmlUrlTextView = findViewById(R.id.html_url_text_view);
        htmlUrlTextView.setText(userModel.getHtmlUrl());

        nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(userModel.getName());

        emailTextView = findViewById(R.id.email_text_view);
        emailTextView.setText(userModel.getEmail());

        publicReposTextView = findViewById(R.id.public_repos_text_view);
        publicReposTextView.setText(getString(R.string.public_repos) + " " + userModel.getPublicRepos());

        publicGistsTextView = findViewById(R.id.public_gists_text_view);
        publicGistsTextView.setText(getString(R.string.public_gists) + " " + userModel.getPublicGists());

        followersTextView = findViewById(R.id.followers_text_view);
        followersTextView.setText(getString(R.string.followers) + " " + userModel.getFollowers());

        followingTextView = findViewById(R.id.following_text_view);
        followingTextView.setText(getString(R.string.following) + " " + userModel.getFollowing());

        createdAtTextView = findViewById(R.id.created_at_text_view);
        createdAtTextView.setText(getString(R.string.created_at) + " " + userModel.getCreatedAt());

        updatedAtTextView = findViewById(R.id.updated_at_text_view);
        updatedAtTextView.setText(getString(R.string.updated_at) + " " + userModel.getUpdatedAt());

    }

    public static void startActivity(Activity activity, UserModel userModel) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(Constants.USER_MODEL_KEY, userModel);
        activity.startActivity(intent);
    }
}
