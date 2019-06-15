package code.apps.ripple.parti.things;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Thing {
    public boolean alive;
    public int id;
    public float initPos;
    public Vector2 pos;
    public float rot;
    public Vector2 speed;
    float a = 100.0f;
    private float b;
    private float c;
    private int d;
    private float e;
    private Vector2 f;

    public Thing(int i, Vector2 vector2, int i2, float f) {
        this.id = i;
        this.f = vector2;
        this.d = i2;
        this.f = vector2;
        this.alive = true;
        this.e = f;
        this.b = MathUtils.randomBoolean() ? 0.5f : -0.5f;
        this.pos = new Vector2(MathUtils.random(-20.0f, this.f.x), 0.0f);
        this.initPos = this.pos.x;
        if (i2 == -1) {
            f *= (float) i2;
            this.pos.y = vector2.y;
        } else if (i2 == 1) {
            f *= (float) i2;
            this.pos.y = -90.0f;
        } else if (MathUtils.randomBoolean()) {
            f *= -1.0f;
            this.pos.y = vector2.y;
        } else {
            f *= 1.0f;
            this.pos.y = -90.0f;
        }
        this.c = a(0.0f, 360.0f);
        this.a = a(70.0f, 150.0f);
        this.speed = new Vector2(this.pos.x, 1.2f * f);
    }

    private float a(float f, float f2) {
        return MathUtils.random(f, f2);
    }

    public void update() {
        this.rot += this.b;
        if (this.d == -1) {
            this.c += 0.1f;
            this.pos.x = this.initPos + ((-((float) Math.sin((double) (this.pos.y / this.a)))) * this.a);
        } else if (this.d == 1) {
            this.c = (float) (((double) this.c) - 0.2d);
            this.pos.x = this.initPos - ((-((float) Math.cos((double) (this.pos.y / this.a)))) * this.a);
        }
        Vector2 vector2 = this.pos;
        vector2.y += this.speed.y;
        if (this.pos.y > this.f.y + 100.0f || this.pos.y < -100.0f) {
            this.alive = false;
        }
    }
}
