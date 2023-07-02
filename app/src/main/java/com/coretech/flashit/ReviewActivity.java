package com.coretech.flashit;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    ImageView close_button;
    ImageView previousButton;
    ImageView nextButton;
    TextView description;
    TextView indication;
    Button answerButton;
    TextView numbersTextView;
    long cardSetId;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashit_review_activity);

        close_button = findViewById(R.id.close_button);
        previousButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);
        description = findViewById(R.id.updatedTextview);
        indication = findViewById(R.id.indicationTextview);
        answerButton = findViewById(R.id.button4);
        numbersTextView = findViewById(R.id.numbers);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cardSetId = getIntent().getExtras().getLong("card-set-id");

        previousButton.setColorFilter(Color.parseColor("#179495"));

        defaultIndicators();

        AsyncTask.execute(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(ReviewActivity.this);
            List<ModelCards> cards = appDatabase.cardSets().getCards(cardSetId);

            Collections.shuffle(cards);

            changeCard(cards.size() != 0 ? cards.get(index) : null, index, cards.size());

            runOnUiThread(() -> {
                if (cards.size() == 0) {
                    nextButton.setColorFilter(Color.parseColor("#179495"));
                    answerButton.setEnabled(false);
                } else {
                    nextButton.setColorFilter(Color.parseColor("#28bcb5"));
                }

                previousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (--index >= 0) {
                            defaultIndicators();
                            changeCard(cards.get(index), index, cards.size());

                            if (index == 0) {
                                previousButton.setColorFilter(Color.parseColor("#179495"));
                            }

                            if (cards.size() > index) {
                                nextButton.setColorFilter(Color.parseColor("#28bcb5"));
                            }
                        } else {
                            index = 0;
                            previousButton.setColorFilter(Color.parseColor("#179495"));
                        }
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (++index < cards.size()) {
                            defaultIndicators();
                            changeCard(cards.get(index), index, cards.size());

                            if (index == cards.size() - 1) {
                                nextButton.setColorFilter(Color.parseColor("#179495"));
                            }

                            if (index > 0) {
                                previousButton.setColorFilter(Color.parseColor("#28bcb5"));
                            }
                        } else {
                            index = cards.size() - 1;
                            nextButton.setColorFilter(Color.parseColor("#179495"));
                        }
                    }
                });

                answerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (answerButton.getText().toString() == "Show Question") {
                            description.setText(cards.get(index).question);

                            answerButton.setText("Show Answer");
                            indication.setText("Question");
                        } else {
                            description.setText(cards.get(index).answer);

                            answerButton.setText("Show Question");
                            indication.setText("Answer");
                        }
                    }
                });
            });
        });
    }

    private void defaultIndicators() {
        answerButton.setText("Show Answer");
        indication.setText("Question");
    }

    private void changeCard(@Nullable ModelCards card, int index, int max) {
        if (card != null) {
            description.setText(card.question);
            numbersTextView.setText(index + 1 + "/" + max);
        } else {
            description.setText("Add cards to review.");
            numbersTextView.setText("00/00");
        }
    }
}
