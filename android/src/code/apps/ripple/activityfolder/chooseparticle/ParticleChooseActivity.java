package code.apps.ripple.activityfolder.chooseparticle;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.image_gallery.OnImageSelection;

import java.util.ArrayList;
import java.util.List;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Wallpaper;
import code.apps.ripple.parti.things.ThingRenderer;

public class ParticleChooseActivity extends AppCompatActivity implements OnImageSelection {
    RecyclerView a;
    ParticleChooseAdadpter b;
    List<ImageModal> c;
    SharedPreferences d;

    public void requestNewInterstitial() {

    }

    public void loadBannerAds() {

    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bg_chooser);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        a();
        findViewById(R.id.btn_more).setVisibility(8);
        b();
        c();
        loadBannerAds();
        requestNewInterstitial();
    }

    private void a() {
        this.d = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
    }

    private void b() {
        this.c = new ArrayList();
        try {
            String[] list = getResources().getAssets().list("data/particles");
            this.d.edit().putInt(Wallpaper.PREFKEY_TOTAL_PARTICLE, list.length).commit();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("file:///android_asset/data/particles/");
                    stringBuilder.append(list[i]);
                    ImageModal imageModal = new ImageModal(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(Wallpaper.PREFKEY_PARTICLE_PREFIX);
                    stringBuilder.append(i);
                    imageModal.setPref_key(stringBuilder.toString());
                    imageModal.isSelected = imageModal.isSelected(i, this.d);
                    this.c.add(imageModal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void c() {
        this.a = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.a.setLayoutManager(linearLayoutManager);
        this.b = new ParticleChooseAdadpter(this.c, this);
        this.a.setAdapter(this.b);
        this.a.addItemDecoration(new DividerItemDecoration(this.a.getContext(), linearLayoutManager.getOrientation()));
        this.b.setListener(this);
    }

    public void selected(int i) {
        this.c.get(i).setSelected(this.d, !this.c.get(i).isSelected(this.d));
        this.b.notifyDataSetChanged();
        ThingRenderer.isParticleLenUpdated = true;
    }

    public void onBackPressed() {
        requestNewInterstitial();
        super.onBackPressed();
    }
}
