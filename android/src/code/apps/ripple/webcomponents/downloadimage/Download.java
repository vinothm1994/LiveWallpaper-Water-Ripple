package code.apps.ripple.webcomponents.downloadimage;

import android.os.Parcel;
import android.os.Parcelable;

public class Download implements Parcelable {
    public static final Creator<Download> CREATOR = new Creator<Download>() {
        /* renamed from: rippleDraw */
        public Download createFromParcel(Parcel parcel) {
            return new Download(parcel);
        }

        /* renamed from: rippleDraw */
        public Download[] newArray(int i) {
            return new Download[i];
        }
    };
    private int a;
    private int b;
    private int c;
    public Download(){}

    public Download(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public int getProgress() {
        return this.a;
    }

    public void setProgress(int i) {
        this.a = i;
    }

    public int getCurrentFileSize() {
        return this.b;
    }

    public void setCurrentFileSize(int i) {
        this.b = i;
    }

    public int getTotalFileSize() {
        return this.c;
    }

    public void setTotalFileSize(int i) {
        this.c = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
    }
}
