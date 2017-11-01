package com.example.mina.githubrepos;

import android.app.Application;

import com.example.mina.githubrepos.di.component.DaggerRepoComponent;
import com.example.mina.githubrepos.di.component.RepoComponent;
import com.example.mina.githubrepos.di.models.RepoListModel;
import com.example.mina.githubrepos.models.RepoModel;
import java.util.ArrayList;

/**
 * Created by mina on 29/10/17.
 */

public class MyApplication extends Application {

    RepoComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerRepoComponent
                .builder()
                .repoListModel(new RepoListModel(new ArrayList<>())).build();
    }

    public RepoComponent getComponent(){
        return component;
    }


}
