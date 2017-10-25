package com.example.mina.githubrepos.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mina.githubrepos.R;

/**
 * Created by mina on 25/10/17.
 */

public class RepoViewHolder extends RecyclerView.ViewHolder{

    public TextView nameTextView;
    public TextView descriptionTextView;

    public RepoViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name_value);
        descriptionTextView = itemView.findViewById(R.id.desc_value);
    }
}
