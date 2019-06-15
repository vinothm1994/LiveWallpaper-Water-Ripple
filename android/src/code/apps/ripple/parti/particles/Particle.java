package code.apps.ripple.parti.particles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Particle {
    public Vector2 pos = new Vector2();
    public float scale;
    public Vector2 speed = new Vector2();
    private Vector2 a;
    private float b;

    public Particle(Vector2 vector2) {
        this.a = vector2;
        a();
    }

    private void a() {
        float f = -1.0f;
        float f2 = MathUtils.randomBoolean() ? 1.0f : -1.0f;
        if (MathUtils.randomBoolean()) {
            f = 1.0f;
        }
        Vector2 vector2 = new Vector2(f2, f);
        this.pos.set(a(this.a.x / 5.0f, this.a.x / 1.2f), a(this.a.y / 5.0f, this.a.y / 1.2f));
        this.speed.set(a(0.5f, 1.0f), a(0.5f, 1.0f)).scl(vector2);
        this.scale = (float) MathUtils.random(0, 1);
        this.b = 0.01f;
    }

    private float a(float f, float f2) {
        return MathUtils.random(f, f2);
    }

    public void update() {
        b();
        c();
    }

    private void b() {
        Vector2 vector2;
        this.pos.add(this.speed);
        if (this.pos.x > this.a.x - 17.0f || this.pos.x < 0.0f) {
            vector2 = this.speed;
            vector2.x *= -1.0f;
        }
        if (this.pos.y > this.a.y - 17.0f || this.pos.y < 0.0f) {
            vector2 = this.speed;
            vector2.y *= -1.0f;
        }
    }

    private void c() {
        this.scale += this.b;
        if (this.scale <= 0.0f) {
            this.b = 0.01f;
        } else if (this.scale >= 1.0f) {
            this.b = -0.01f;
        }
    }
}
