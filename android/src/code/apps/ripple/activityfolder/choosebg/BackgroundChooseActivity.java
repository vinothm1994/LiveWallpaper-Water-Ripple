package code.apps.ripple.activityfolder.choosebg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.image_gallery.OnImageSelection;
import com.mygdx.game.ui.image_gallery.WallpaperChooseAdapter;

import java.util.ArrayList;
import java.util.List;

import code.apps.ripple.logic.Assets;
import code.apps.ripple.logic.Wallpaper;
import code.apps.ripple.webcomponents.FileDownloader;
import code.apps.ripple.webcomponents.downloadimage.Download;
import code.apps.ripple.webcomponents.downloadimage.DownloadService;

public class BackgroundChooseActivity extends AppCompatActivity implements OnClickListener, OnImageSelection {
    public static final String KEY_DONWLOAD = "download";
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String device_id = "70682F79C44FC98C3B40BC5E621FCE35";
    private final String g = "file:///android_asset/data/images/";
    RecyclerView a;
    WallpaperChooseAdapter b;
    List<ImageModal> imageModals;
    SharedPreferences d;
    ProgressBar e;
    private BroadcastReceiver h = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BackgroundChooseActivity.MESSAGE_PROGRESS)) {
                Download download = (Download) intent.getParcelableExtra(BackgroundChooseActivity.KEY_DONWLOAD);
                BackgroundChooseActivity.this.e.setProgress(download.getProgress());
                if (download.getProgress() == 100) {
                    BackgroundChooseActivity.this.findViewById(R.id.btn_more).setVisibility(8);
                    BackgroundChooseActivity.this.e.setVisibility(8);
                    BackgroundChooseActivity.this.a();
                    BackgroundChooseActivity.this.b.notifyDataSetChanged();
                }
            }
        }
    };

    public void requestNewInterstitial() {

    }

    public void loadBannerAds() {
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bg_chooser);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.e = (ProgressBar) findViewById(R.id.progress);
        this.d = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        Wallpaper.background_selected = Integer.parseInt(this.d.getString("bg", "1"));
        if (this.d.getInt(FileDownloader.SPKEY_TOTAL_IMAGES, 0) > 5) {
            this.e.setVisibility(8);
            findViewById(R.id.btn_more).setVisibility(8);
        }
        this.imageModals = new ArrayList();
        a();
        c();
        loadBannerAds();
        requestNewInterstitial();
        findViewById(R.id.btn_more).setOnClickListener(this);
        d();
    }

    private void a() {
        this.imageModals.clear();
        try {
            String[] list = getResources().getAssets().list("data/images");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("file:///android_asset/data/images/");
                    stringBuilder.append(list[i]);
                    ImageModal imageModal = new ImageModal(stringBuilder.toString());
                    if (Wallpaper.background_selected == i) {
                        imageModal.isSelected = true;
                    }
                    this.imageModals.add(imageModal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        b();
        return;
    }

    private void b() {
        String[] split = this.d.getString(FileDownloader.SPKEY_IMAGE_NAMES, "").split(",");
        if (split.length > 1) {
            for (String imageModal : split) {
                ImageModal imageModal2 = new ImageModal(imageModal);
                imageModal2.setOnlineImage(true);
                this.imageModals.add(imageModal2);
            }
        }
    }

    private void c() {
        this.a = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.a.setLayoutManager(linearLayoutManager);
        this.b = new WallpaperChooseAdapter(this.imageModals, this);
        this.a.setAdapter(this.b);
        this.a.addItemDecoration(new DividerItemDecoration(this.a.getContext(), linearLayoutManager.getOrientation()));
        this.b.setListener(this);
    }

    public void selected(int i) {
        for (ImageModal imageModal : this.imageModals) {
            imageModal.isSelected = false;
        }
        ((ImageModal) this.imageModals.get(i)).isSelected = true;
        this.b.notifyDataSetChanged();
        if (((ImageModal) this.imageModals.get(i)).isOnlineImage()) {
            this.d.edit().putString(FileDownloader.SPKEY_SELECTED_IMAGE, this.d.getString(FileDownloader.SPKEY_IMAGE_NAMES, "").split(",")[i - 10]).commit();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(i);
            this.d.edit().putString("bg", stringBuilder.toString()).commit();
        }
        Assets.setTextureChanged(true);
        Assets.setOnlineImage(this.imageModals.get(i).isOnlineImage());
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_more) {
            this.e.setVisibility(0);
            startService(new Intent(this, DownloadService.class));
            findViewById(R.id.btn_more).setVisibility(8);
        }
    }

    private void d() {
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        instance.registerReceiver(this.h, intentFilter);
    }

    public void onBackPressed() {
        requestNewInterstitial();
        super.onBackPressed();
    }
}
