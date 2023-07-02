package com.coretech.flashit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

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
        ModelCards card = cards.get(position);
        holder.termsTextView.setText(card.question);
        holder.descriptionTextView.setText(card.answer);
        holder.cardId = card.id;
    }

    @Override
    public int getItemCount() {
        return cards.size(); // Number of items in the grid
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView termsTextView;
        TextView descriptionTextView;

        private ImageView deleteButton;
        private ImageView editButton;

        private long cardId;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            termsTextView = itemView.findViewById(R.id.termsTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);

            //Delete card
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = view.getContext();
                    AppDatabase appDatabase = AppDatabase.getInstance(context);

                    int position = getBindingAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
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
                                                appDatabase.cards().delete(appDatabase.cards().find(cardId));
                                            });

                                            // Notify the adapter that data has changed
                                            cards.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, cards.size());

                                            // Display a success message
                                            Toast.makeText(context, "Card deleted.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        });
                    }
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUpdateCardDialog(view.getContext(), getBindingAdapterPosition());
                }
            });
        }

        private boolean isDialogShown = false;

        private void showUpdateCardDialog(final Context context, final int position) {
            if (isDialogShown) {
                return; // Dialog is already showing, exit the method
            }

            isDialogShown = true; // Set the flag to indicate dialog is showing

            final Dialog dialog = new Dialog(context);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isDialogShown = false; // Reset the flag when the dialog is dismissed
                }
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.modal_update_card);

            ImageView cancelButton = dialog.findViewById(R.id.updateCardCloseButton);
            Button confirmButton = dialog.findViewById(R.id.updateCardConfirmButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            final Context appContext = context.getApplicationContext();

            TextInputEditText termEditText = dialog.findViewById(R.id.updateTermInputEditText);
            TextInputEditText descriptionEditText = dialog.findViewById(R.id.updateDescriptionInputEditText);
            AppDatabase appDatabase = AppDatabase.getInstance(appContext);

            AsyncTask.execute(() -> {
                ModelCards card = appDatabase.cards().find(cardId);

                ((Activity) context).runOnUiThread(() -> {
                    termEditText.setText(card.question);
                    descriptionEditText.setText(card.answer);
                });
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Retrieve the name entered by the user
                    String newTerm = termEditText.getText().toString();
                    String newDescription = descriptionEditText.getText().toString();

                    if (newTerm.length() > 0 && newDescription.length() > 0) {
                        AsyncTask.execute(() -> {
                            ModelCards card = appDatabase.cards().find(cardId);

                            if (position != RecyclerView.NO_POSITION) {
                                card.question = newTerm;
                                card.answer = newDescription;

                                appDatabase.cards().update(card);

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        cards.set(position, card);
                                        notifyItemChanged(position);
                                        Toast.makeText(context, "Card updated.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        dialog.dismiss(); // Dismiss the dialog before performing database operation
                    } else {
                        Toast.makeText(context, "Term and description fields are required.", Toast.LENGTH_LONG).show();
                    }
                }
            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }
}
