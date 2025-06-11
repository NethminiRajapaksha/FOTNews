package com.example.fotnews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {
    private int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        tabIndex = getIntent().getIntExtra("tabIndex", 0);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewsActivity.class);
            intent.putExtra("tabIndex", tabIndex);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Set detail content based on tabIndex
        TextView tvTitle = findViewById(R.id.tvNewsDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvNewsDetailAuthor);
        TextView tvBody = findViewById(R.id.tvNewsDetailBody);
        TextView tvDate = findViewById(R.id.tvNewsDate);
        ImageView ivImage = findViewById(R.id.ivNewsDetailImage);

        if (tabIndex == 0) {
            // News tab
            tvTitle.setText("TechSports 2025 – Faculty of Technology Sports Fiesta");
            tvAuthor.setText("Nethmini Rajapaksha");
            tvBody.setText("TechSports 2025, the annual sports event organized by the Faculty of Technology, University of Colombo, was held with great enthusiasm and team spirit. The event brought together students, staff, and faculty members for a vibrant celebration of athleticism and camaraderie.\n" +
                    "\n" +
                    "A wide range of sports activities including track and field events, cricket, football, volleyball, and indoor games were conducted throughout the day. The event served as a platform to showcase the sporting talents of students while promoting unity, fitness, and friendly competition.\n" +
                    "\n" +
                    "Cheered on by an energetic crowd, participants gave their best performances, and winning teams were awarded trophies and certificates. The Dean and staff members joined in encouraging the athletes and celebrating the spirit of sportsmanship.");
            tvDate.setText("May 30th • 3 days ago");
            ivImage.setImageResource(R.drawable.sample_news8);
        } else if (tabIndex == 1) {
            // Academic tab
            tvTitle.setText("Curriculum and Research Strategy Meeting – FOT UOC");
            tvAuthor.setText("Avishka Yasiru");
            tvBody.setText("An insightful academic discussion was recently held by the Dean’s Board of the Faculty of Technology, University of Colombo, bringing together academic leaders, department heads, and senior staff members.\n" +
                    "\n" +
                    "The session focused on key strategic topics such as curriculum development, research initiatives, academic quality enhancement, and fostering industry collaborations. Valuable input was shared regarding interdisciplinary program integration, student engagement, and upcoming faculty projects aimed at aligning with global technological trends.");
            tvDate.setText("April 30th • 3 days ago");
            ivImage.setImageResource(R.drawable.sample_news2);
        } else if (tabIndex == 2) {
            // Events tab
            tvTitle.setText("TECHNOVA-X 2025 Robotics Workshop Ignites Young Minds");
            tvAuthor.setText("Lanka Dewmini");
            tvBody.setText("The TECHNOVA-X 2025 Robotics Workshop, proudly organized by the Innovation Club of the Faculty of Technology, University of Colombo, was successfully conducted at Rajasinghe Maha Vidyalaya, Imbulgoda. This dynamic outreach event aimed to introduce school students to the fascinating world of robotics and emerging technologies.\n" +
                    "\n" +
                    "Participants were given hands-on experience with basic robotics concepts, sensor integration, and simple coding using microcontrollers. The interactive sessions were led by university undergraduates who shared their knowledge and passion for innovation. Students were encouraged to think creatively and work collaboratively in teams, sparking their curiosity and problem-solving skills.");
            tvDate.setText("April 30th • 3 days ago");
            ivImage.setImageResource(R.drawable.sample_news5);
        }
    }
}
