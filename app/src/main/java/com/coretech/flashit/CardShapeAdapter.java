package com.coretech.flashit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

//            deleteBTN.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    AppDatabase appDatabase = AppDatabase.getInstance(deleteBTN.getContext());
//
//                    AsyncTask.execute(() -> {
//                        List<ModelCardSets> cardSets = appDatabase.cardSets().all();
//
//                    // Prompt the user with a confirmation dialog
//                        AlertDialog.Builder builder = new AlertDialog.Builder(CardShapeAdapter.this);
//                        builder.setTitle("Delete Category")
//                                .setMessage("Are you sure you want to delete this category?")
//                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        String selectedCategory = cardSets.get(position);
//                                        removeCategory(selectedCategory);
//                                        Toast.makeText(CardShapeAdapter.this, "Card deleted", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .setNegativeButton("Cancel", null)
//                                .show();
//
//                        return true;
//                    });
//                }
//            });

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


