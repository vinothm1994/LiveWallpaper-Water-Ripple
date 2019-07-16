package code.apps.ripple.activityfolder.choosebg;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageModal implements Parcelable {
    public String filepath;
    private boolean isOnline;

    public ImageModal(String str) {
        this.filepath = str;
    }

    protected ImageModal(Parcel in) {
        filepath = in.readString();
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

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isOnlineImage() {
        return this.isOnline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filepath);
        dest.writeByte((byte) (isOnline ? 1 : 0));
    }
}
