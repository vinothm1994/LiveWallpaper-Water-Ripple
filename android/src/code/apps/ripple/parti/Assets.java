package code.apps.ripple.parti;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public Texture[] particle;
    public TextureRegion[] particlesRegion;

    public void loadParticle() {
        FileHandle internal;
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            internal = Gdx.files.internal("data/particles");
            this.particle = new Texture[internal.list().length];
            this.particlesRegion = new TextureRegion[internal.list().length];
        } else {
            internal = null;
        }
        for (int i = 0; i < this.particle.length; i++) {
            this.particle[i] = new Texture(internal.list()[i]);
            this.particlesRegion[i] = new TextureRegion(this.particle[i]);
        }
    }
}
