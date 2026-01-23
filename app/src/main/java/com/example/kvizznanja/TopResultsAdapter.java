package com.example.kvizznanja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopResultsAdapter extends RecyclerView.Adapter<TopResultsAdapter.ViewHolder> {

    private final List<Result> results;

    public TopResultsAdapter(List<Result> results){
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Result r = results.get(position);
        holder.name.setText(r.getName());
        holder.score.setText(String.valueOf(r.getScore()));
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, score;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.resultName);
            score = itemView.findViewById(R.id.resultScore);
        }
    }
}
