package com.coretech.flashit;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList; //added

public class CardShapeAdapter extends RecyclerView.Adapter<CardShapeAdapter.ViewHolder> {
    private List<CardShape> cardShapes;
    private List<CardShape> originalCardShapes; //added

    public CardShapeAdapter(List<CardShape> cardShapes) {
        this.cardShapes = cardShapes;
        this.originalCardShapes = new ArrayList<>(cardShapes); //added
    }

    //added
    public void setCardShapes(List<CardShape> cardShapes) {
        this.cardShapes = cardShapes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_card_shape_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardShape cardShape = cardShapes.get(position);
        holder.bind(cardShape);
    }

    @Override
    public int getItemCount() {
        return cardShapes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectTextView;
        private Button reviewButton;
        private Button practiceButton;
        private ImageView editName_button;
        private LinearLayout layoutCardCreate;
        private ImageView deleteBTN;

        private long cardSetId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subject_update_view);
            reviewButton = itemView.findViewById(R.id.reviewButton);
//            practiceButton = itemView.findViewById(R.id.practiceButton);
            layoutCardCreate = itemView.findViewById(R.id.layoutCardCreate);
            editName_button = itemView.findViewById(R.id.editName_button);
            deleteBTN = itemView.findViewById(R.id.deleteBTN);

            //Delete Card Set
            deleteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = view.getContext();
                    AppDatabase appDatabase = AppDatabase.getInstance(context);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int position = getBindingAdapterPosition();
                            List<ModelCardSets> cardSets = appDatabase.cardSets().all();

                            if (position != RecyclerView.NO_POSITION) {
                                ModelCardSets cardSetToDelete = cardSets.get(position);

                                // Prompt the user with a confirmation dialog
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Delete CardSet")
                                                .setMessage("Are you sure you want to delete this CardSet?")
                                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        // Delete the cardSet from the database
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                appDatabase.cardSets().delete(cardSetToDelete);

                                                                // Remove the cardSetToDelete from the cardSets list
                                                                cardSets.remove(cardSetToDelete);

                                                                // Notify the adapter that data has changed
                                                                ((Activity) context).runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        cardShapes.remove(position);
                                                                        notifyItemRemoved(position);
                                                                        notifyItemRangeChanged(position, cardShapes.size());
                                                                    }
                                                                });
                                                            }
                                                        }).start();

                                                        // Display a success message
                                                        Toast.makeText(context, "CardSet deleted successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setNegativeButton("Cancel", null)
                                                .show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });

            editName_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Launch CreatingCardSetActivity
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, CreatingCardSetActivity.class);
                    context.startActivity(intent);
                }
            });

            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch ReviewActivity
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ReviewActivity.class);
                    context.startActivity(intent);
                }
            });

//            practiceButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Launch PracticeActivity
//                    Context context = itemView.getContext();
//                    Intent intent = new Intent(context, PracticeActivity.class);
//                    context.startActivity(intent);
//                }
//            });

            layoutCardCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                // Launch CardsViewingTab
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, CardsViewingTab.class);

                    intent.putExtra("card-set-id", cardSetId);

                    context.startActivity(intent);
                }
            });


        }

        public void bind(CardShape cardShape) {
            subjectTextView.setText(cardShape.getSubject());
            cardSetId = cardShape.getId();
        }
    }
}


