package code.apps.ripple.activityfolder.choosebg;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageModal implements Parcelable {
    public String filepath;
    public boolean isSelected = false;
    private String prefKey;
    private boolean isOnline;

    public ImageModal(String str) {
        this.filepath = str;
    }

    protected ImageModal(Parcel in) {
        filepath = in.readString();
        isSelected = in.readByte() != 0;
        prefKey = in.readString();
        isOnline = in.readByte() != 0;
    }

    public static final Creator<ImageModal> CREATOR = new Creator<ImageModal>() {
        @Override
        public ImageModal createFromParcel(Parcel in) {
            return new ImageModal(in);
        }

        @Override
        public ImageModal[] newArray(int size) {
            return new ImageModal[size];
        }
    };

    public void setPref_key(String str) {
        this.prefKey = str;
    }

    public boolean isSelected(SharedPreferences sharedPreferences) {
        this.isSelected = sharedPreferences.getBoolean(this.prefKey, true);
        return this.isSelected;
    }

    public boolean isSelected(int i, SharedPreferences sharedPreferences) {
        if (i > 4) {
            this.isSelected = sharedPreferences.getBoolean(this.prefKey, false);
        } else {
            this.isSelected = sharedPreferences.getBoolean(this.prefKey, true);
        }
        setSelected(sharedPreferences, this.isSelected);
        return this.isSelected;
    }

    public void setSelected(SharedPreferences sharedPreferences, boolean pos) {
        sharedPreferences.edit().putBoolean(this.prefKey, pos).commit();
        this.isSelected = pos;
    }

    public boolean isOnlineImage() {
        return this.isOnline;
    }

    public void setOnlineImage(boolean z) {
        this.isOnline = z;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filepath);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeString(prefKey);
        dest.writeByte((byte) (isOnline ? 1 : 0));
    }
}
