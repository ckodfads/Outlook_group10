package vn.edu.usth.outlook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.content.Intent;
import vn.edu.usth.outlook.R;

public class ComposeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change status bar background color to the corresponding color
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background_all));

        setContentView(R.layout.activity_compose);
        ImageButton btnSend = findViewById(R.id.btnSend);
        ImageButton backBtn = findViewById(R.id.backBtn);


        // Send button click listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your send email logic here
                Toast.makeText(ComposeActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle back button click
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Handle popup menu item clicks here
        return false;
    }
}