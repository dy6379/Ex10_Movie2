package com.busanit.ex10_movie2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    ArrayList<Movie> items = new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie item = items.get(position);
        holder.textTitle.setText(item.movieNm);
        holder.textRank.setText(item.rank);
        holder.textOpen.setText(item.openDt);
        holder.textAudi.setText(item.audiAcc+" 명");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Movie> items){
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle, textRank, textOpen, textAudi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textRank = itemView.findViewById(R.id.textRank);
            textOpen = itemView.findViewById(R.id.textOpen);
            textAudi = itemView.findViewById(R.id.textAudi);
        }
    }
}
