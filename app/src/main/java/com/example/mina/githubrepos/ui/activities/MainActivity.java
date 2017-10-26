package com.example.mina.githubrepos.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.mina.githubrepos.R;
import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.network.ApiInterfaces;
import com.example.mina.githubrepos.network.RetrofitSingleton;
import com.example.mina.githubrepos.ui.UiUtils;
import com.example.mina.githubrepos.ui.adapters.RepoAdapter;
import com.example.mina.githubrepos.ui.decoration.RepoDecoration;
import com.example.mina.githubrepos.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RepoAdapter.RepoItemCallback {

    private EditText repoEditText;
    private Button searchButton;
    private RecyclerView recycleView;
    private ProgressBar progressBar;

    private RecyclerView.Adapter adapter;
    private ArrayList<RepoModel> data = new ArrayList<>();

    private int pageNumber = 1;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private int previousTotal = 0;
    private int visibleThreshold = 4;
    private boolean loading = true;

    private ApiInterfaces.GetOrgRepo service;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoEditText = (EditText) findViewById(R.id.repo_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);
        recycleView = (RecyclerView) findViewById(R.id.recycle_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_dialog);

        searchButton.setOnClickListener(v -> {
                    if (!repoEditText.getText().toString().equals("")) {
                        pageNumber = 1;
                        data.clear();
                        requestRepos();
                    } else {
                        UiUtils.loadSnackBar(getString(R.string.repo_validation_message), MainActivity.this);
                    }

                }
        );
        adapter = new RepoAdapter(this, data, this);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new RepoDecoration(5));
        addRecyclePaging();

        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(ApiInterfaces.GetOrgRepo.class);

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

    private void requestRepos() {
        progressBar.setVisibility(View.VISIBLE);
        //Observable<List<RepoModel>> apiData = service.getApiData(repoEditText.getText().toString(), pageNumber + "");
        mCompositeDisposable.add(service.getApiData(repoEditText.getText().toString(), pageNumber + "")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(List<RepoModel> repoModels) {
        if (repoModels.size() == 0 && pageNumber == 1) {
            UiUtils.loadSnackBar(getString(R.string.no_repo) + " " + repoEditText.getText().toString(), this);
        } else {
            data.addAll(repoModels);
            adapter.notifyDataSetChanged();
        }

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void repoItemClicked(int position) {
        Intent intent = new Intent(this, RepoDetailsActivity.class);
        intent.putExtra(Constants.REPO_MODEL_KEY, data.get(position));
        startActivity(intent);
    }
}
