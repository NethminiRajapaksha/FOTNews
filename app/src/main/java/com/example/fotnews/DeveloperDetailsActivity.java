package com.example.fotnews;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DeveloperDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_details);

        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnExit = findViewById(R.id.btnExit);

        ivBack.setOnClickListener(v -> finish());

        btnExit.setOnClickListener(v -> finish());
    }
}
