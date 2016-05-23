package com.example.testapp.listrepositories;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp.R;

import java.util.List;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.ViewHolder> {

    private static final String TAG = RepositoryListAdapter.class.getSimpleName();
    private final List<RepositoryItem> listRepositories;

    public RepositoryListAdapter(List<RepositoryItem> listRepositories) {
        this.listRepositories = listRepositories;
    }

    @Override
    public RepositoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repository_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int index) {
        RepositoryItem record = listRepositories.get(index);
        Log.d(TAG, "onBindViewHolder: record  "  +  record);
        viewHolder.repositoryTitle.setText(record.getRepositoryTitle() +
                "\n" + record.getLanguage());
        viewHolder.starsCount.setText(record.getStarsCount()+"");
        viewHolder.forksCount.setText(record.getForkCount()+"");
    }

    @Override
    public int getItemCount() {
        return listRepositories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView repositoryTitle;
        TextView repositoryLanguage;
        TextView starsCount;
        TextView forksCount;

        public ViewHolder(View itemView) {
            super(itemView);
            repositoryTitle = (TextView) itemView.findViewById(R.id.repository_title_language);
//            TextView repositoryLanguage;
            starsCount = (TextView) itemView.findViewById(R.id.stars_count);
            forksCount = (TextView) itemView.findViewById(R.id.forks_count);
        }
    }
}
