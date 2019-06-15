package code.apps.ripple.parti;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import code.apps.ripple.logic.Wallpaper;
import code.apps.ripple.parti.particles.ParticleRenderer;
import code.apps.ripple.parti.things.ThingRenderer;

public class ObjectRenderer implements Disposable {
    private SpriteBatch b;
    private Vector2 c = new Vector2(640.0f, 1136.0f);
    private OrthographicCamera a = new OrthographicCamera(this.c.x, this.c.y);
    private ParticleRenderer d;
    private ThingRenderer e;

    public ObjectRenderer(Context context) {
        a();
        this.b = new SpriteBatch();
        this.d = new ParticleRenderer(this.a, this.b, this.c);
        this.e = new ThingRenderer(context, this.a, this.b, this.c);
    }

    private void a() {
        this.c.y = (this.c.x * ((float) Gdx.graphics.getHeight())) / ((float) Gdx.graphics.getWidth());
        this.a.position.set(this.c.x / 2.0f, this.c.y / 2.0f, 0.0f);
    }

    public void render() {
        if (Wallpaper.floating_particle) {
            this.d.updateAndRender();
        }
        if (Wallpaper.particle_effect) {
            this.e.updateAndRender();
        }
    }

    public void dispose() {
        this.b.dispose();
        this.d.dispose();
        this.e.dispose();
    }
}
