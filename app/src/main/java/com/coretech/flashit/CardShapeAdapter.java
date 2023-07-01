package com.coretech.flashit;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

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
//        private Button practiceButton;
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

                    int position = getBindingAdapterPosition();

                    AsyncTask.execute(() -> {
                        List<ModelCardSets> cardSets = appDatabase.cardSets().all();

                        if (position != RecyclerView.NO_POSITION) {
                            ModelCardSets cardSetToDelete = cardSets.get(position);

                            // Prompt the user with a confirmation dialog
                            ((Activity) context).runOnUiThread(() -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete CardSet")
                                        .setMessage("Are you sure you want to delete this CardSet?")
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // Delete the cardSet from the database
                                                AsyncTask.execute(() -> {
                                                    appDatabase.cardSets().delete(cardSetToDelete);
                                                    appDatabase.cardSets().getCards(cardSetId);
                                                });

                                                // Remove the cardSetToDelete from the cardSets list
                                                cardSets.remove(cardSetToDelete);

                                                // Notify the adapter that data has changed
                                                cardShapes.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, cardShapes.size());

                                                // Display a success message
                                                Toast.makeText(context, "CardSet deleted successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            });
                        }
                    });
                }
            });

            editName_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Launch showUpdateNameDialog
//                    showAddCategoryDialog();
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

//    private boolean isUpdateNameDialogShowing = false; // Add this variable
//
//    private void showUpdateNameDialog() {
//        if (isUpdateNameDialogShowing) {
//            return; // Dialog is already showing, exit the method
//        }
//
//        isUpdateNameDialogShowing = true; // Set the flag to indicate dialog is showing
//
//        final Dialog dialog = new Dialog(this);
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                isUpdateNameDialogShowing = false; // Reset the flag when the dialog is dismissed
//            }
//        });
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.modal_update_cardshapelayoutname);
//
//        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
//        Button confirmButton = dialog.findViewById(R.id.confirmButton);
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Retrieve the category entered by the user
//                TextInputEditText categoryEditText = dialog.findViewById(R.id.updateNameInputEditText);
//                String subject = categoryEditText.getText().toString();
//
//                // Check if the category is empty
//                if (!subject.isEmpty()) {
//                    // Save the Name to shared preferences
//                    list.add(subject);
//                    arrayAdapter.notifyDataSetChanged();
//                    saveCategories(list);
//                    Toast.makeText(CardShapeAdapter.this, "Updated Name: " + subject, Toast.LENGTH_SHORT).show();
//
//                    // Dismiss the dialog
//                    dialog.dismiss();
//                } else {
//                    // Name is empty, display an error message
//                    Toast.makeText(CardShapeAdapter.this, "Please enter a Name", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.CENTER);
//    }

}


