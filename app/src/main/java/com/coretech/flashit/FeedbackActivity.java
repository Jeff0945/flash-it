package com.coretech.flashit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    EditText commentsButton;
    Button sendButton;
    ImageView cancel_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feedback);

        cancel_Button = findViewById(R.id.cancel_Button);
        sendButton = findViewById(R.id.sendButton);
        commentsButton = findViewById(R.id.commentsButton);

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedbackActivity.this, "Send button was clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
