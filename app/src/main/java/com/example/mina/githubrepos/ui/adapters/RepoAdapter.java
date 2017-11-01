package com.example.mina.githubrepos.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mina.githubrepos.R;
import com.example.mina.githubrepos.models.RepoModel;
import com.example.mina.githubrepos.ui.viewholders.RepoViewHolder;
import java.util.ArrayList;

/**
 * Created by Mina on 2/15/2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoViewHolder> {

    private Context context;
    private ArrayList<RepoModel> data;
    private RepoItemCallback repoItemCallback;

    public RepoAdapter(Context context, ArrayList<RepoModel> data, RepoItemCallback repoItemCallback) {
        this.data = data;
        this.context = context;
        this.repoItemCallback = repoItemCallback;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_repo, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new RepoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, final int position) {

        holder.nameTextView.setText(data.get(position).getName());
        holder.descriptionTextView.setText(data.get(position).getDescription());
        holder.itemView.setOnClickListener(v ->
                repoItemCallback.repoItemClicked(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface RepoItemCallback {
        void repoItemClicked(int position);
    }
}
