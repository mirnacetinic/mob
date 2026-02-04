package com.example.kvizznanja;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final List<Result> results;

    public ResultsAdapter(List<Result> results){
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
        holder.email.setText(r.getEmail());
        holder.score.setText(String.valueOf(r.getScore()) + " bod");
        holder.time.setText(String.valueOf(r.getTime()) + 's');
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView email, score, time;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.resultEmail);
            score = itemView.findViewById(R.id.resultScore);
            time = itemView.findViewById(R.id.resultTime);
        }
    }
}
