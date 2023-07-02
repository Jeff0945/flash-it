package com.coretech.flashit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity {

    ImageView close_button;
    TextView description;
    TextView indication;
    Button answerButton;
    List<ModelCards> cardsList;
    int currentIndex;
    long cardSetId;
    TextView numbersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashit_review_activity);

        close_button = findViewById(R.id.close_button);
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(ReviewActivity.this);
                final List<ModelCards> cards = appDatabase.cardSets().getCards(cardSetId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showRandomCard(cards);
                    }
                });
            }
        });
    }

    private void showRandomCard(List<ModelCards> cards) {
        if (cards != null && !cards.isEmpty()) {
            int randomIndex = new Random().nextInt(cards.size());
            ModelCards card = cards.get(randomIndex);

            description.setText(card.answer);
            setButtonText("Answer");
            indication.setText("Question");

            answerButton.setOnClickListener(new View.OnClickListener() {
                boolean isQuestionShown = true;
                @Override
                public void onClick(View view) {
                    if (isQuestionShown) {
                        description.setText(card.question);
                        setButtonText("Question");
                        indication.setText("Answer");
                    } else {
                        description.setText(card.answer);
                        setButtonText("Answer");
                        indication.setText("Question");
                    }
                    isQuestionShown = !isQuestionShown;
                }
            });

            // Calculate and set the card count
            int currentCardIndex = randomIndex + 1;
            int totalCards = cards.size();
            String cardCountText = String.format(Locale.getDefault(), "%d/%d", currentCardIndex, totalCards);
            numbersTextView.setText(cardCountText);
        }
    }
    private void setButtonText(String text) {
        answerButton.setText(text);
    }
}
