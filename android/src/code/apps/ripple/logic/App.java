package code.apps.ripple.logic;

import android.content.Context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.android.InputProcessorLW;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.lang.reflect.Array;

import code.apps.ripple.parti.ObjectRenderer;

public class App implements ApplicationListener, InputProcessorLW {
    final float e = 0.7f;
    public float height;
    public int n = 2;
    public float width;
    public float x_offset = 0.0f;
    short a = (short) 50;
    final float c = (1.0f / ((float) this.a));
    short b = (short) 50;
    final float d = (1.0f / ((float) this.b));
    long f;
    float g = 0.045f;
    float h;
    Mesh mesh;
    Plane plane = new Plane(new Vector3(), new Vector3(1.0f, 0.0f, 0.0f), new Vector3(0.0f, 1.0f, 0.0f));
    Vector3 k = new Vector3();
    float[][] l;
    float[][] m;
    float[][] o;
    float[] p;
    Vector3 vector31;
    Sound sound;
    Context context;
    Assets assets;
    SpriteBatch u;
    ObjectRenderer objectRenderer;
    ShaderProgram shaderProgram;
    Vector3 vector3 = new Vector3();
    private PerspectiveCamera perspectiveCamera;


    public App(Context context) {
        this.context = context;
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i2) {
        return false;
    }

    @Override
    public void pause() {
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    @Override

    public void touchDrop(int i, int i2) {
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public void create() {
        this.u = new SpriteBatch();
        this.sound = Gdx.audio.newSound(Gdx.files.internal("data/sounds").list()[Wallpaper.sound]);
        this.perspectiveCamera = new PerspectiveCamera(90.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.width = (float) Gdx.graphics.getWidth();
        this.height = (float) Gdx.graphics.getHeight();
        this.perspectiveCamera.position.set(((float) this.a) / 2.0f, ((float) this.b) / 2.0f, ((float) this.a) / 2.0f);
        this.perspectiveCamera.near = 0.1f;
        this.perspectiveCamera.far = 1000.0f;
        this.l = (float[][]) Array.newInstance(float.class, new int[]{this.a + 1, this.b + 1});
        this.m = (float[][]) Array.newInstance(float.class, new int[]{this.a + 1, this.b + 1});
        this.o = (float[][]) Array.newInstance(float.class, new int[]{this.a + 1, this.b + 1});
        this.p = new float[(((this.a + 1) * (this.b + 1)) * 5)];
        if (this.mesh == null) {
            this.mesh = new Mesh(false, (this.a + 1) * (this.b + 1), (this.a * this.b) * 6, new VertexAttribute(1, 3, "v_pos"), new VertexAttribute(16, 2, "v_tex"));
            this.assets = new Assets(this.context);
            d();
            a(this.m);
            a();
        }
        this.vector31 = new Vector3();
        try {
            this.assets.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gdx.input.setInputProcessor(this);
        this.objectRenderer = new ObjectRenderer(this.context);
    }

    private void a() {
        FileHandle internal = Gdx.files.internal("data/shader.glsl");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#define VERTEX \n");
        stringBuilder.append(internal.readString());
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("#define FRAGMENT \n");
        stringBuilder3.append(internal.readString());
        this.shaderProgram = new ShaderProgram(stringBuilder2, stringBuilder3.toString());
        if (this.shaderProgram.isCompiled()) {
            System.out.println("compiled Successfully");
            return;
        }
        System.out.println(this.shaderProgram.getLog());
        internal = Gdx.files.internal("data/lowpshader.glsl");
        stringBuilder = new StringBuilder();
        stringBuilder.append("#define VERTEX \n");
        stringBuilder.append(internal.readString());
        stringBuilder2 = stringBuilder.toString();
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("#define FRAGMENT \n");
        stringBuilder3.append(internal.readString());
        this.shaderProgram = new ShaderProgram(stringBuilder2, stringBuilder3.toString());
    }

    private void b() {
        if (Wallpaper.ripple_random && System.currentTimeMillis() - this.f > Wallpaper.ripple_random_freq) {
            this.f = System.currentTimeMillis();
            this.n = 2;
            rippleDraw(b(0, Gdx.graphics.getWidth()), b(0, Gdx.graphics.getHeight()));
        }
    }

    @Override
    public void render() {
        GL20 gl20 = Gdx.graphics.getGL20();
        if (gl20==null)
            return;
        gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Assets.isTextureChanged) {
            try {
                this.assets.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Assets.setTextureChanged(false);
        }
        this.perspectiveCamera.update();
        this.h += Gdx.graphics.getDeltaTime();
        while (this.h > this.g) {
            e();
            float[][] fArr = this.m;
            this.m = this.l;
            this.l = fArr;
            this.h -= this.g;
        }
        a(this.h / this.g);
        a(this.o);
        gl20.glEnable(GL20.GL_TEXTURE_2D);
        this.shaderProgram.begin();
        this.assets.texture.bind();
        this.shaderProgram.setUniformMatrix("combined", this.perspectiveCamera.combined);
        this.mesh.render(this.shaderProgram, 4);
        this.shaderProgram.end();
        playBubbleSound();
        b();
        this.objectRenderer.render();
    }

    private void playBubbleSound() {
        if (Wallpaper.ripple_effect && Wallpaper.ripple_sound && Gdx.input.justTouched()) {
            this.sound.setVolume(this.sound.play(), 0.9f);
        }
    }

    /* Access modifiers changed, original: 0000 */
    public void rippleDraw(int i, int i2) {
        if (Wallpaper.ripple_effect) {
            this.vector3.set((float) i, (float) i2, 0.0f);
            Intersector.intersectRayPlane(this.perspectiveCamera.getPickRay(this.vector3.x, this.vector3.y), this.plane, this.k);
            a(this.k);
        }
    }

    private void d() {
        short[] sArr = new short[((this.a * this.b) * 6)];
        short s = (short) 0;
        int i = s;
        while (s < this.b) {
            short s2 = (short) ((this.a + 1) * s);
            int i2 = i;
            short s3 = (short) 0;
            while (s3 < this.a) {
                int i3 = i2 + 1;
                sArr[i2] = s2;
                i2 = i3 + 1;
                short s4 = (short) (s2 + 1);
                sArr[i3] = s4;
                i3 = i2 + 1;
                sArr[i2] = (short) ((this.a + s2) + 1);
                i2 = i3 + 1;
                sArr[i3] = s4;
                i3 = i2 + 1;
                sArr[i2] = (short) ((this.a + s2) + 2);
                i2 = i3 + 1;
                sArr[i3] = (short) ((s2 + this.a) + 1);
                s3++;
                s2 = s4;
            }
            s++;
            i = i2;
        }
        this.mesh.setIndices(sArr);
    }

    private void a(float[][] fArr) {
        short s = (short) 0;
        int i = s;
        while (s <= this.b) {
            int i2 = i;
            short s2 = (short) 0;
            while (s2 <= this.a) {
                float f;
                float f2;
                if (s2 <= (short) 0 || s2 >= this.a || s <= (short) 0 || s >= this.b) {
                    f = 0.0f;
                    f2 = f;
                } else {
                    f = fArr[s2 - 1][s] - fArr[s2 + 1][s];
                    f2 = fArr[s2][s - 1] - fArr[s2][s + 1];
                }
                int i3 = i2 + 1;
                float f3 = (float) s2;
                this.p[i2] = f3;
                int i4 = i3 + 1;
                float f4 = (float) s;
                this.p[i3] = f4;
                i3 = i4 + 1;
                this.p[i4] = 0.0f;
                int i5 = i3 + 1;
                this.p[i3] = (f3 + f) * this.c;
                int i6 = i5 + 1;
                this.p[i5] = 1.0f - ((f4 + f2) * this.d);
                s2++;
                i2 = i6;
            }
            s++;
            i = i2;
        }
        this.mesh.setVertices(this.p);
    }

    private void e() {
        short s = (short) 0;
        while (s < this.b + 1) {
            short s2 = (short) 0;
            while (s2 < this.a + 1) {
                if (s2 > (short) 0 && s2 < this.a && s > (short) 0 && s < this.b) {
                    this.m[s2][s] = ((((this.l[s2 - 1][s] + this.l[s2 + 1][s]) + this.l[s2][s + 1]) + this.l[s2][s - 1]) / ((float) this.n)) - this.m[s2][s];
                }
                float[] fArr = this.m[s2];
                fArr[s] = fArr[s] * 0.7f;
                s2++;
            }
            s++;
        }
    }

    private void a(float f) {
        for (short s = (short) 0; s < this.b; s++) {
            for (short s2 = (short) 0; s2 < this.a; s2++) {
                this.o[s2][s] = (this.l[s2][s] * f) + ((1.0f - f) * this.m[s2][s]);
            }
        }
    }

    private void a(Vector3 vector3) {
        int i = Wallpaper.ripple_radius;
        float f = (float) (Wallpaper.ripple_displacement * -10);
        for (int max = Math.max(0, ((int) vector3.y) - i); max < Math.min(this.b, ((int) vector3.y) + i); max++) {
            for (int max2 = Math.max(0, ((int) vector3.x) - i); max2 < Math.min(this.a, ((int) vector3.x) + i); max2++) {
                float max3 = this.m[max2][max] + (Math.max(0.0f, (float) Math.cos((1.5707963267948966d * Math.sqrt((double) vector3.dst2((float) max2, (float) max, 0.0f))) / ((double) i))) * f);
                if (max3 < f) {
                    max3 = f;
                } else {
                    float f2 = -f;
                    if (max3 > f2) {
                        max3 = f2;
                    }
                }
                this.m[max2][max] = max3;
            }
        }
    }

    @Override
    public void resize(int i, int i2) {
        Gdx.gl20.glViewport(0, 0, i, i2);
    }

    @Override
    public void resume() {
        Assets.setTextureChanged(true);
    }

    /* Access modifiers changed, original: 0000 */
    public int b(int i, int i2) {
        return (int) (((double) i) + (Math.random() * ((double) ((i2 - i) + 1))));
    }

    @Override
    public boolean touchDown(int i, int i2, int i3, int i4) {
        this.f = System.currentTimeMillis();
        this.n = 2;
        rippleDraw(i, i2);
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i2, int i3) {
        if (Wallpaper.ripple_water_tail && System.currentTimeMillis() - this.f > 150) {
            this.n = 4;
            rippleDraw(i, i2);
        }
        return false;
    }
}
