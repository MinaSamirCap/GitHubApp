package com.example.mina.githubrepos.di.models;

import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.network.ApiInterfaces;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

/**
 * Created by mina on 30/10/17.
 */

@Module
public class RepoListModel {

    private ArrayList<RepoModel> repoModelList;

    public RepoListModel(ArrayList<RepoModel> repoModelList) {
        this.repoModelList = repoModelList;
    }

    @Provides
    ArrayList<RepoModel> provideRepoList(){
        return repoModelList;
    }
}
