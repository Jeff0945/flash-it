package com.coretech.flashit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadCardSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_card_set);

        ImageView goBack = findViewById(R.id.goBackButton);
        ;

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UploadCardSetActivity.this, "Close button was clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadCardSetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}