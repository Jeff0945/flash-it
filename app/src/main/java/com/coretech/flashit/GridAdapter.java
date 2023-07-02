package com.coretech.flashit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView termsTextView;
        TextView descriptionTextView;

        private ImageView deleteButton;

        private long cardSetId;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            termsTextView = itemView.findViewById(R.id.termsTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            //Delete card
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = view.getContext();
                    AppDatabase appDatabase = AppDatabase.getInstance(context);

                    int position = getBindingAdapterPosition();

                    AsyncTask.execute(() -> {
                        List<ModelCards> card = appDatabase.cards().all();

                        if (position != RecyclerView.NO_POSITION) {
                            ModelCards cardToDelete = card.get(position);

                            // Prompt the user with a confirmation dialog
                            ((Activity) context).runOnUiThread(() -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete Card")
                                        .setMessage("Are you sure you want to delete this card?")
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // Delete the cardSet from the database
                                                AsyncTask.execute(() -> {
                                                    appDatabase.cards().delete(cardToDelete);
                                                    //appDatabase.cards().getCards(cardSetId);
                                                });

                                                // Remove the cardSetToDelete from the cardSets list
                                                card.remove(cardToDelete);

                                                // Notify the adapter that data has changed
                                                cards.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, cards.size());

                                                // Display a success message
                                                Toast.makeText(context, "Card deleted successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            });
                        }
                    });

                }
            });
        }
    }
}
