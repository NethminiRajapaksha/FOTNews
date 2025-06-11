package com.example.fotnews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fotnews.adapters.CarouselAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView ivMenu, ivProfile;
    private TabLayout tabLayout;
    private ViewPager2 carouselViewPager;
    private Handler sliderHandler = new Handler();

    // NEW: Container for the single card under each tab
    private FrameLayout tabContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        ivMenu = findViewById(R.id.ivMenu);
        ivProfile = findViewById(R.id.ivProfile);
        tabLayout = findViewById(R.id.tabLayout);
        carouselViewPager = findViewById(R.id.carouselViewPager);
        tabContentContainer = findViewById(R.id.tabContentContainer); // NEW

        // Set drawer width to half the screen
        int width = getResources().getDisplayMetrics().widthPixels / 2;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = width;
        navigationView.setLayoutParams(params);

        // Hamburger icon opens drawer from the left
        ivMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Set up navigation drawer menu selection
        navigationView.setNavigationItemSelectedListener(this);


        ivProfile.setOnClickListener(v -> {

        });

        // Setup Tabs
        tabLayout.addTab(tabLayout.newTab().setText("Sports").setContentDescription("Sports Tab"));
        tabLayout.addTab(tabLayout.newTab().setText("Academic").setContentDescription("Academic Tab"));
        tabLayout.addTab(tabLayout.newTab().setText("Events").setContentDescription("Events Tab"));

        // Setup Carousel below Tabs
        setupCarousel();

        int selectedTab = getIntent().getIntExtra("tabIndex", 0);
        TabLayout.Tab tab = tabLayout.getTabAt(selectedTab);
        if (tab != null) tab.select();

        showTabContent(selectedTab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showTabContent(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showTabContent(int tabIndex) {
        tabContentContainer.removeAllViews();
        int layoutId;
        switch (tabIndex) {
            case 0: layoutId = R.layout.item_news_tab; break;
            case 1: layoutId = R.layout.item_academic_tab; break;
            case 2: layoutId = R.layout.item_events_tab; break;
            default: layoutId = R.layout.item_news_tab;
        }
        View itemView = getLayoutInflater().inflate(layoutId, tabContentContainer, false);
        tabContentContainer.addView(itemView);

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewsDetailActivity.class);
            intent.putExtra("tabIndex", tabIndex); // Pass tab index for detail
            startActivity(intent);
        });
    }

    private void setupCarousel() {
        List<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarouselItem(
                R.drawable.sample_news1,
                "University of Colombo's Faculty of Technology Signs MoU with RCS2 Technologies",
                "Lanka Dewmini"));
        carouselItems.add(new CarouselItem(
                R.drawable.sample_news4,
                "NoorFeast 2025: A Heartwarming Celebration of Unity and Culture at UoC Tech Faculty",
                "Avishka Yasiru"));
        carouselItems.add(new CarouselItem(
                R.drawable.sample_news7,
                "TECHNOVA-X 2025 Robotics Workshop by UoC Tech Faculty held at Rajasinghe MV",
                "Sham Perera"));

        CarouselAdapter carouselAdapter = new CarouselAdapter(carouselItems);
        carouselViewPager.setAdapter(carouselAdapter);

        // Set up carousel effect
        carouselViewPager.setClipToPadding(false);
        carouselViewPager.setClipChildren(false);
        carouselViewPager.setOffscreenPageLimit(3);
        carouselViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(24));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.92f + r * 0.08f);
        });
        carouselViewPager.setPageTransformer(transformer);

        // Auto-scroll carousel
        carouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 4000); // Slide every 4 seconds
            }
        });
    }

    // Runnable for auto sliding carousel
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (carouselViewPager.getAdapter() != null) {
                int currentItem = carouselViewPager.getCurrentItem();
                int totalItems = carouselViewPager.getAdapter().getItemCount();
                carouselViewPager.setCurrentItem((currentItem + 1) % totalItems);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 4000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        if (id == R.id.nav_user_details) {
            startActivity(new Intent(this, UserDetailsActivity.class));
            return true;
        } else if (id == R.id.nav_developer_details) {
            startActivity(new Intent(this, DeveloperDetailsActivity.class));
            return true;
        } else if (id == R.id.nav_settings) {
            // Handle settings navigation
            return true;
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
