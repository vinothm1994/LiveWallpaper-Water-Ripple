package code.apps.ripple.parti.particles;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticleRenderer implements Disposable {
    private final int a = 50;
    private Texture particleDot;
    private TextureRegion particleDottextureRegion;
    private ArrayList<Particle> d;
    private Vector2 e = new Vector2();
    private OrthographicCamera f;
    private SpriteBatch g;
    private Vector2 h;

    public ParticleRenderer(OrthographicCamera orthographicCamera, SpriteBatch spriteBatch, Vector2 vector2) {
        this.f = orthographicCamera;
        this.g = spriteBatch;
        this.h = vector2;
        this.particleDot = new Texture("data/particle.png");
        this.particleDottextureRegion = new TextureRegion(this.particleDot);
        this.e = new Vector2(((float) this.particleDottextureRegion.getRegionWidth()) / 2.0f, ((float) this.particleDottextureRegion.getRegionHeight()) / 2.0f);
        a();
    }

    private void a() {
        this.d = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            this.d.add(new Particle(this.h));
        }
    }

    public void updateAndRender() {
        this.f.update();
        this.g.setProjectionMatrix(this.f.combined);
        this.g.begin();
        ArrayList<Particle> d1 = this.d;
        for (int i = 0, d1Size = d1.size(); i < d1Size; i++) {
            Particle particle = d1.get(i);
            particle.update();
            this.g.draw(this.particleDottextureRegion, particle.pos.x, particle.pos.y, this.e.x, this.e.y, (float) this.particleDottextureRegion.getRegionWidth(), (float) this.particleDottextureRegion.getRegionHeight(), particle.scale, particle.scale, 0.0f);
        }
        this.g.end();
    }

    public void dispose() {
        this.particleDot.dispose();
    }
}
