package vn.edu.usth.outlook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.outlook.Email_Sender;
import vn.edu.usth.outlook.KeyboardVisibilityUtils;
import vn.edu.usth.outlook.R;
import vn.edu.usth.outlook.fragment.ArchiveFragment;
import vn.edu.usth.outlook.fragment.DeletedFragment;
import vn.edu.usth.outlook.fragment.DraftFragment;
import vn.edu.usth.outlook.fragment.JunkFragment;
import vn.edu.usth.outlook.fragment.SentFragment;
import vn.edu.usth.outlook.fragment.SettingsFragment;
import vn.edu.usth.outlook.fragment.UnwantedFragment;
import vn.edu.usth.outlook.listener.SelectListener;
import vn.edu.usth.outlook.adapter.CustomAdapter;
import vn.edu.usth.outlook.fragment.InboxFragment;

public class MainActivity extends AppCompatActivity implements SelectListener, KeyboardVisibilityUtils.OnKeyboardVisibilityListener {

    RecyclerView recyclerView;
    //    List<Email> List;
    public static List<Email_Sender> emailList = new ArrayList<>();
    CustomAdapter customAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ExtendedFloatingActionButton compose_button;
    CoordinatorLayout coordinatorLayout;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    KeyboardVisibilityUtils keyboardVisibilityUtils;
    EditText editText;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    private Fragment currentFragment;
    String userid_sender;
    TextView textView;
    ExtendedFloatingActionButton floatingActionButton;



    private boolean isMeetItemSelected = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Change status bar background to the corresponding
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background_all));

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        compose_button = findViewById(R.id.Compose);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageView searchIcon = findViewById(R.id.search_icon);
        searchView = findViewById(R.id.search_view);
        searchIcon.bringToFront();
        // Set onClickListener for the search icon to toggle SearchView visibility
        searchIcon.setOnClickListener(v -> {
            if (searchView.getVisibility() == View.GONE) {
                searchView.setVisibility(View.VISIBLE);
                searchView.requestFocus();// Focus on the SearchView when it becomes visible
                textView.setVisibility(View.GONE);
            } else {
                searchView.setVisibility(View.GONE);
                searchView.clearFocus();
                textView.setVisibility(View.VISIBLE);
            }
        });
        searchIcon.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Trigger the click behavior manually
                v.performClick();
                return true;
            }
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        TextView textView_meetings = new TextView(this);

        // Enable the Menu Icon to toggle the Menu Bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        View rootView = findViewById(android.R.id.content);
        keyboardVisibilityUtils = new KeyboardVisibilityUtils(rootView, this);


        // Set a click listener for the navigation button in the toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the navigation drawer on the start (left) side is open
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    // If it's open, close it
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // If it's not open, open it
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        // Side-bar navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                if (item.getItemId() == R.id.inbox){
                    recyclerView.setVisibility(View.VISIBLE);

                    openFragment(new InboxFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.draft){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new DraftFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.settings){
                    recyclerView.setVisibility(View.GONE);
                    compose_button.setVisibility(View.GONE);
                    openFragment(new SettingsFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.sent){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new SentFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.archive){
                    recyclerView.setVisibility(View.VISIBLE);
                    openFragment(new ArchiveFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.junk){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new JunkFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.sent){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new SentFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.deleted){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new DeletedFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.unwanted){
                    recyclerView.setVisibility(View.GONE);
                    openFragment(new UnwantedFragment());
                    return true;
                }
                return false;
            }
        });




    }
    //  Search bar
    private void filter(String newText) {
        List<Email_Sender> filteredList = new ArrayList<>();
        for (Email_Sender item : emailList){
            if (item.getSender().toLowerCase().startsWith(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        customAdapter.filterList(filteredList);
    }


    @Override
    public void onKeyboardVisibilityChanged(boolean isVisible) {
        if (isVisible) {
            // Keyboard is open, hide the BottomAppBar and FAB
            bottomAppBar.setVisibility(View.GONE);
            compose_button.setVisibility(View.GONE);
        } else {
            // Keyboard is closed, show the BottomAppBar and FAB
            bottomAppBar.setVisibility(View.VISIBLE);
            if (isMeetItemSelected == true){
                compose_button.setVisibility(View.GONE);
            }
            else{
                compose_button.setVisibility(View.VISIBLE);
            }

        }}



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }}
    @Override
    public void onLongItemClick(int position) {

    }


    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }
}