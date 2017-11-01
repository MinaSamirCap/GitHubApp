package com.example.mina.githubrepos.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mina.githubrepos.MyApplication;
import com.example.mina.githubrepos.R;
import com.example.mina.githubrepos.models.AccessTokenModel;
import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.models.UserModel;
import com.example.mina.githubrepos.network.ApiInterfaces;
import com.example.mina.githubrepos.network.ApiUrls;
import com.example.mina.githubrepos.network.RetrofitSingleton;
import com.example.mina.githubrepos.network.RetrofitSingleton2;
import com.example.mina.githubrepos.ui.UiUtils;
import com.example.mina.githubrepos.ui.adapters.RepoAdapter;
import com.example.mina.githubrepos.ui.decoration.RepoDecoration;
import com.example.mina.githubrepos.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RepoAdapter.RepoItemCallback {

    @Inject
    ArrayList<RepoModel> data;

    private EditText repoEditText;
    private Button searchButton, loginButton;
    private RecyclerView recycleView;
    private ProgressBar progressBar;
    private TextView userNameTextView, emailTextView;
    private ImageView profileImageView;

    private RecyclerView.Adapter adapter;

    private int pageNumber = 1;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private int previousTotal = 0;
    private int visibleThreshold = 4;
    private boolean loading = true;

    private boolean comeFromBrowser = true;
    private boolean loggedIn = false;
    private String accessToken;

    private ApiInterfaces service1, service2;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getComponent().inject(this);

        initViews();
        addRecyclePaging();

        Retrofit retrofit = RetrofitSingleton.getInstance();
        service1 = retrofit.create(ApiInterfaces.class);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_repos:
                validateLogin();
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_report_bug:
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void validateLogin() {
        if (loggedIn) {
            getUserPrivateRepos();
        } else {
            UiUtils.loadSnackBar(getString(R.string.login_first), this);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void repoItemClicked(int position) {
        Intent intent = new Intent(this, RepoDetailsActivity.class);
        intent.putExtra(Constants.REPO_MODEL_KEY, data.get(position));
        intent.putExtra(Constants.REPO_MODEL_POSITION_KEY, position);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(ApiUrls.CALLBACK_URL) && comeFromBrowser) {
            String code = uri.getQueryParameter(ApiUrls.CODE_KEY);
            UiUtils.loadSnackBar(getString(R.string.success_login), this);
            Retrofit retrofit = RetrofitSingleton2.getInstance();
            service2 = retrofit.create(ApiInterfaces.class);

            progressBar.setVisibility(View.VISIBLE);
            Observable<AccessTokenModel> apiData = service2.
                    getAccessToken(ApiUrls.GITHUB_CLIENT_ID, ApiUrls.GITHUB_CLIENT_SECRET, code);
            apiData.subscribeOn(Schedulers.io()) // "work" on io thread
                    .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                    .subscribe(this::handleAccessTokenResponse, this::handleError);

            comeFromBrowser = false;
        }
    }

    private void initViews() {

        repoEditText = findViewById(R.id.repo_edit_text);
        searchButton = findViewById(R.id.search_button);
        loginButton = findViewById(R.id.login_button);

        recycleView = findViewById(R.id.recycle_view);
        progressBar = findViewById(R.id.progress_dialog);

        searchButton.setOnClickListener(v -> searchButtonClicked());
        loginButton.setOnClickListener(v -> openGitHubLogin());

        /////// recycle and adapter
        adapter = new RepoAdapter(this, data, this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new RepoDecoration(5));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.user_name_text_view);
        emailTextView = navigationView.getHeaderView(0).findViewById(R.id.email_text_view);
        profileImageView = navigationView.getHeaderView(0).findViewById(R.id.profile_image_view);


        /*FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void searchButtonClicked() {
        if (!repoEditText.getText().toString().equals("")) {
            pageNumber = 1;
            data.clear();
            requestRepos();
        } else {
            UiUtils.loadSnackBar(getString(R.string.repo_validation_message), MainActivity.this);
        }

    }

    private void addRecyclePaging() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = recyclerView.getLayoutManager().getItemCount();
                firstVisibleItem = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    pageNumber++;
                    requestRepos();

                    loading = true;
                }
            }
        });
    }

    private void openGitHubLogin() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiUrls.O_AUTH_URL));
        startActivity(intent);
        comeFromBrowser = true;
    }

    private void getUserPrivateRepos() {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<RepoModel>> apiData = service1.getPrivateRepos(accessToken);
        apiData.subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(this::handlePrivateRepoResponse, this::handleError);
    }

    private void handlePrivateRepoResponse(List<RepoModel> repoModels) {
        data.addAll(repoModels);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }


    private void requestRepos() {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<RepoModel>> apiData = service1.getRepoData(repoEditText.getText().toString(), pageNumber + "");
        apiData.subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(this::handleReposResponse, this::handleError);
        /*mCompositeDisposable.add(service1.getApiData(repoEditText.getText().toString(), pageNumber + "")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(this::handleReposResponse, this::handleError)
        );*/
    }

    private void handleReposResponse(List<RepoModel> repoModels) {
        if (repoModels.size() == 0 && pageNumber == 1) {
            UiUtils.loadSnackBar(getString(R.string.no_repo) + " " + repoEditText.getText().toString(), this);
        } else {
            data.addAll(repoModels);
            adapter.notifyDataSetChanged();
        }

        progressBar.setVisibility(View.GONE);
    }

    private void handleAccessTokenResponse(AccessTokenModel accessTokenModel) {
        //UiUtils.loadSnackBar(accessTokenModel.getAccessToken(), this);
        //progressBar.setVisibility(View.GONE);
        accessToken = accessTokenModel.getAccessToken();
        Observable<UserModel> apiData = service1.getProfile(accessToken);
        apiData.subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(this::handleProfileResponse, this::handleError);
    }

    private void handleProfileResponse(UserModel userModel) {
        progressBar.setVisibility(View.GONE);
        loggedIn = true;
        UiUtils.loadSnackBar(getString(R.string.success_load_profile), this);
        emailTextView.setText(userModel.getEmail());
        userNameTextView.setText(userModel.getName());
        Picasso.with(this).load(userModel.getAvatarUrl()).into(profileImageView);
        profileImageView.setOnClickListener(view -> ProfileActivity.startActivity(this, userModel));
    }


    private void handleError(Throwable throwable) {
        UiUtils.loadSnackBar(throwable.getMessage(), this);
        progressBar.setVisibility(View.GONE);
    }

}
