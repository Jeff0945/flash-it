package com.coretech.flashit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardShapeAdapter extends RecyclerView.Adapter<CardShapeAdapter.ViewHolder> {
    private List<CardShape> cardShapes;

    public CardShapeAdapter(List<CardShape> cardShapes) {
        this.cardShapes = cardShapes;
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

        private long cardSetId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subject_update_view);
            reviewButton = itemView.findViewById(R.id.reviewButton);
//            practiceButton = itemView.findViewById(R.id.practiceButton);
            layoutCardCreate = itemView.findViewById(R.id.layoutCardCreate);
            editName_button = itemView.findViewById(R.id.editName_button);

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


