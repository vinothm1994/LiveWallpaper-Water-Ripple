package code.apps.ripple.logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;

import code.apps.ripple.webcomponents.FileDownloader;

public class Assets {
    public static boolean isOnlineImage = false;
    public static boolean isTextureChanged = false;
    public Texture texture;
    Context context;
    SharedPreferences sharedPreferences;

    public Assets(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
    }

    public static boolean isTextureChanged() {
        return isTextureChanged;
    }

    public static void setTextureChanged(boolean z) {
        isTextureChanged = z;
    }

    public static boolean isOnlineImage() {
        return isOnlineImage;
    }

    public static void setOnlineImage(boolean z) {
        isOnlineImage = z;
    }

    public void load() throws IOException {
        setTextureChanged(true);
        if (isTextureChanged()) {
            if (this.texture != null) {
                this.texture.dispose();
                this.texture = null;
            }
            if (Gdx.app.getType() != ApplicationType.Android) {
                return;
            }
            if (isOnlineImage()) {
                this.texture = new Texture(Gdx.files.absolute(this.context.getFileStreamPath(this.sharedPreferences.getString(FileDownloader.SPKEY_SELECTED_IMAGE, "")).getAbsolutePath()));
                return;
            }
            FileHandle internal = Gdx.files.internal("data/images");
            this.texture = new Texture(internal.list()[Integer.parseInt(this.sharedPreferences.getString("bg", "1"))]);
        }
    }

    public void dispose() {
        if (this.texture != null) {
            this.texture.dispose();
        }
    }
}
