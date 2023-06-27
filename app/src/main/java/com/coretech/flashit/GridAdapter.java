package com.coretech.flashit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


// for display of items at the recycler view in the CardsViewingTab

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private String[] termsArray;
    private String[] descriptionArray;

    public GridAdapter(String[] termsArray, String[] descriptionArray) {
        this.termsArray = termsArray;
        this.descriptionArray = descriptionArray;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.termsTextView.setText(termsArray[position]);
        holder.descriptionTextView.setText(descriptionArray[position]);
    }

    @Override
    public int getItemCount() {
        return 12; // Number of items in the grid
    }


    public static class GridViewHolder extends RecyclerView.ViewHolder {
        TextView termsTextView;
        TextView descriptionTextView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            termsTextView = itemView.findViewById(R.id.termsTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
