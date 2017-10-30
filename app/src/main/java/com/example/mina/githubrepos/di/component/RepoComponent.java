package com.example.mina.githubrepos.di.component;

import com.example.mina.githubrepos.di.models.RepoListModel;
import com.example.mina.githubrepos.ui.activities.MainActivity;
import com.example.mina.githubrepos.ui.activities.RepoDetailsActivity;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by mina on 29/10/17.
 */

@Singleton
@Component(modules = RepoListModel.class)
public interface RepoComponent {

    void inject(MainActivity mainActivity);

    void inject(RepoDetailsActivity repoDetailsActivity);

}
