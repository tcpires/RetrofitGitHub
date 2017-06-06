package com.kleber.retrofitgithub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kleber.retrofitgithub.R;
import com.kleber.retrofitgithub.models.Repository;

import java.util.List;

/**
 * Created by kleber on 06/06/17.
 */
public class GitHubApapter extends RecyclerView.Adapter<GitHubApapter.ViewHolder> {

    private List<Repository> repositories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.github_repo_text_view);
        }
    }

    public GitHubApapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public GitHubApapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_repo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.textView = (TextView) view.findViewById(R.id.github_repo_text_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textView.setText(this.repositories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.repositories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
