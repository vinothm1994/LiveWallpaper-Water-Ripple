package code.apps.ripple.activityfolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.mygdx.game.R;
import com.mygdx.game.ui.home.HomeActivity;

import code.apps.ripple.fragments.FragLaunch;
import code.apps.ripple.logic.Utils;


public class Launcher extends AppCompatActivity {
    private SectionsPagerAdapter b;
    private ViewPager c;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((int) R.string.full_name);
        setSupportActionBar(toolbar);
        this.b = new SectionsPagerAdapter(getSupportFragmentManager());
        this.c = (ViewPager) findViewById(R.id.container);
        this.c.setAdapter(this.b);
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(this.c);
        startActivity(new Intent(this, HomeActivity.class));

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.action_rate /*2131296279*/:
                Utils.rateThisApp(this);
                break;
            case R.id.action_settings /*2131296280*/:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        b();
    }

    private void b() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want close this app?");
        builder.setPositiveButton("YES", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Launcher.this.finish();
            }
        });
        builder.setNegativeButton("NO", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public int getCount() {
            return 1;
        }

        public CharSequence getPageTitle(int i) {
            switch (i) {
                case 0:
                    return "Wallpaper";
                case 1:
                    return "Top Apps";
                default:
                    return null;
            }
        }

        public Fragment getItem(int i) {
            return FragLaunch.newInstance();
        }
    }
}
