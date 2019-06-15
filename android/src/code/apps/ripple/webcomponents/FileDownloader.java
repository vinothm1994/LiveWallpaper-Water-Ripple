package code.apps.ripple.webcomponents;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import code.apps.ripple.logic.Wallpaper;
import code.apps.ripple.webcomponents.downloadimage.Download;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileDownloader {
    public static final String SPKEY_IMAGE_NAMES = "online_images";
    public static final String SPKEY_SELECTED_IMAGE = "selected_image";
    public static final String SPKEY_TOTAL_IMAGES = "total_images";
    Download a;
    OnImageDownloadFinished b;
    SharedPreferences c;
    Context e;


    public FileDownloader(Context context, OnImageDownloadFinished onImageDownloadFinished) {
        this.e = context;
        this.b = onImageDownloadFinished;
        this.c = context.getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        this.a = new Download();
        execute();
    }

    public static String randomAlphaNumeric(int i) {
        String toLowerCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append(toLowerCase.charAt((int) (Math.random() * ((double) toLowerCase.length()))));
            i = i2;
        }
    }

    public void execute() {
        if (this.c.getBoolean("write_permission", false)) {
            a();
        } else {

        }
    }

    private void a() {
        ApiClientRipple.getClient().create(ApiInterfaceRipple.class).getImages(this.e.getPackageName()).enqueue(new Callback<ResponseRippleImage>() {
            public void onResponse(Call<ResponseRippleImage> call, Response<ResponseRippleImage> response) {
                if (response == null || response.body() == null) {
                    Toast.makeText(FileDownloader.this.e, "Download Failed...Try Again", 0).show();
                    return;
                }
                if (((ResponseRippleImage) response.body()).getResponse().intValue() == 1) {
                    List img = ((ResponseRippleImage) response.body()).getImg();
                    FileDownloader.this.c.edit().putInt(FileDownloader.SPKEY_TOTAL_IMAGES, img.size()).commit();
                    String[] strArr = new String[img.size()];
                    img.toArray(strArr);
                    new a(FileDownloader.this.e).execute(strArr);
                }
            }

            public void onFailure(Call<ResponseRippleImage> call, Throwable th) {
                th.printStackTrace();
            }
        });
    }



    class a extends AsyncTask<String, Integer, String> {
        String a = "";
        Context b;

        public a(Context context) {
            this.b = context;
        }

        /* Access modifiers changed, original: protected|varargs */
        /* renamed from: rippleDraw */
        public String doInBackground(String... strArr) {
            int length = strArr.length;
            int length2 = strArr.length;
            int i = 0;
            int i2 = i;
            while (i < length2) {
                String str = strArr[i];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ApiClientRipple.BASE_URL);
                stringBuilder.append(str);
                b(stringBuilder.toString());
                i2++;
                publishProgress(new Integer[]{Integer.valueOf((int) ((float) ((i2 * 100) / length)))});
                i++;
            }
            return null;
        }

        /* Access modifiers changed, original: protected */
        /* renamed from: rippleDraw */
        public void onPostExecute(String str) {
            FileDownloader.this.c.edit().putString(FileDownloader.SPKEY_IMAGE_NAMES, this.a.substring(0, this.a.length() - 1)).commit();
            FileDownloader.this.b.onImageDownloadFinished();
            super.onPostExecute(str);
        }

        /* Access modifiers changed, original: protected|varargs */
        /* renamed from: rippleDraw */
        public void onProgressUpdate(Integer... numArr) {
            FileDownloader.this.a.setProgress(numArr[0].intValue());
            FileDownloader.this.b.publishProgress(FileDownloader.this.a);
            super.onProgressUpdate(numArr);
        }

        private void b(String str) {
            Bitmap decodeStream;
            Exception e;
            try {
                InputStream openStream = new URL(str).openStream();
                decodeStream = BitmapFactory.decodeStream(openStream);
                try {
                    openStream.close();
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                decodeStream = null;
                e.printStackTrace();
                a(decodeStream);
            }
            a(decodeStream);
        }

        public void a(Bitmap bitmap) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(FileDownloader.randomAlphaNumeric(10));
                stringBuilder.append(".jpg");
                String stringBuilder2 = stringBuilder.toString();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(stringBuilder2);
                stringBuilder3.append(",");
                stringBuilder3.append(this.a);
                this.a = stringBuilder3.toString();
                FileOutputStream openFileOutput = this.b.openFileOutput(stringBuilder2, 0);
                bitmap.compress(CompressFormat.JPEG, 100, openFileOutput);
                openFileOutput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
