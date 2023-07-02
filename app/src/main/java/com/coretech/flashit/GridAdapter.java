package com.coretech.flashit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// for display of items at the recycler view in the CardsViewingTab

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private List<ModelCards> cards;

    public GridAdapter(List<ModelCards> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.termsTextView.setText(cards.get(position).question);
        holder.descriptionTextView.setText(cards.get(position).answer);
    }

    @Override
    public int getItemCount() {
        return cards.size(); // Number of items in the grid
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
