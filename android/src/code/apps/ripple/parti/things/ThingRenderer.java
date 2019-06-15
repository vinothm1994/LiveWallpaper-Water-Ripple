package code.apps.ripple.parti.things;

import android.content.Context;
import android.content.SharedPreferences;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Iterator;

import code.apps.ripple.logic.Wallpaper;

public class ThingRenderer implements Disposable {
    public static boolean isParticleLenUpdated = true;
    public static boolean updateParams = true;
    public int[] selected_things;
    Context a;
    SharedPreferences b;
    private OrthographicCamera c;
    private SpriteBatch d;
    private Vector2 e;
    private Texture[] f;
    private TextureRegion[] g;
    private ArrayList<Thing> h;
    private long i;
    private long j = System.currentTimeMillis();

    public ThingRenderer(Context context, OrthographicCamera orthographicCamera, SpriteBatch spriteBatch, Vector2 vector2) {
        this.a = context;
        this.c = orthographicCamera;
        this.d = spriteBatch;
        this.e = vector2;
        this.h = new ArrayList();
        loadThings();
    }

    public void loadThings() {
        FileHandle internal;
        if (Gdx.app.getType() == ApplicationType.Android) {
            internal = Gdx.files.internal("data/particles");
            this.f = new Texture[internal.list().length];
            this.g = new TextureRegion[internal.list().length];
        } else {
            internal = null;
        }
        for (int i = 0; i < this.f.length; i++) {
            this.f[i] = new Texture(internal.list()[i]);
            this.g[i] = new TextureRegion(this.f[i]);
        }
        this.b = this.a.getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        this.b.edit().putInt(Wallpaper.PREFKEY_TOTAL_PARTICLE, this.f.length).commit();
        a();
    }

    private void a() {
        int i = 0;
        int i2 = i;
        while (i < this.f.length) {
            boolean z;
            SharedPreferences sharedPreferences;
            StringBuilder stringBuilder;
            if (i > 4) {
                sharedPreferences = this.b;
                stringBuilder = new StringBuilder();
                stringBuilder.append(Wallpaper.PREFKEY_PARTICLE_PREFIX);
                stringBuilder.append(i);
                z = sharedPreferences.getBoolean(stringBuilder.toString(), false);
            } else {
                sharedPreferences = this.b;
                stringBuilder = new StringBuilder();
                stringBuilder.append(Wallpaper.PREFKEY_PARTICLE_PREFIX);
                stringBuilder.append(i);
                z = sharedPreferences.getBoolean(stringBuilder.toString(), true);
            }
            if (z) {
                i2++;
            }
            i++;
        }
        if (i2 != 0) {
            int[] iArr = new int[i2];
            i2 = 0;
            int i3 = i2;
            while (i2 < this.f.length) {
                boolean z2;
                SharedPreferences sharedPreferences2;
                StringBuilder stringBuilder2;
                if (i2 > 4) {
                    sharedPreferences2 = this.b;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Wallpaper.PREFKEY_PARTICLE_PREFIX);
                    stringBuilder2.append(i2);
                    z2 = sharedPreferences2.getBoolean(stringBuilder2.toString(), false);
                } else {
                    sharedPreferences2 = this.b;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(Wallpaper.PREFKEY_PARTICLE_PREFIX);
                    stringBuilder2.append(i2);
                    z2 = sharedPreferences2.getBoolean(stringBuilder2.toString(), true);
                }
                if (z2) {
                    int i4 = i3 + 1;
                    iArr[i3] = i2;
                    i3 = i4;
                }
                i2++;
            }
            this.selected_things = iArr;
        } else {
            this.selected_things = null;
        }
        isParticleLenUpdated = false;
    }

    public void updateAndRender() {
        if (isParticleLenUpdated) {
            a();
        }
        if (this.selected_things != null && this.selected_things.length != 0) {
            Thing thing;
            this.j = System.currentTimeMillis();
            if (this.j - this.i > Wallpaper.particle_density) {
                this.i = this.j;
                this.h.add(new Thing(this.selected_things[MathUtils.random(0, this.selected_things.length - 1)], this.e, Wallpaper.particle_animation, (float) Wallpaper.particle_speed));
                System.out.println(this.h.size());
            }
            this.c.update();
            this.d.setProjectionMatrix(this.c.combined);
            this.d.begin();
            Iterator it = this.h.iterator();
            while (it.hasNext()) {
                thing = (Thing) it.next();
                thing.update();
                if (!thing.alive) {
                    it.remove();
                }
            }
            it = this.h.iterator();
            while (it.hasNext()) {
                thing = (Thing) it.next();
                thing.update();
                this.d.draw(this.g[thing.id], thing.pos.x, thing.pos.y, (float) (this.g[thing.id].getRegionWidth() / 2), (float) (this.g[thing.id].getRegionHeight() / 2), (float) this.g[thing.id].getRegionWidth(), (float) this.g[thing.id].getRegionHeight(), 1.0f, 1.0f, thing.rot);
            }
            this.d.end();
        }
    }

    public void dispose() {
        for (Texture dispose : this.f) {
            dispose.dispose();
        }
    }
}
