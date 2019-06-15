package com.mygdx.game.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mygdx.game.R;
import com.mygdx.game.ui.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void addFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(fragment.getTag()).commit();
    }
}
