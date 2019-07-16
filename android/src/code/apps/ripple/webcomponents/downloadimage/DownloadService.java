package code.apps.ripple.webcomponents.downloadimage;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.core.app.NotificationCompat.Builder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.mygdx.game.R;

import code.apps.ripple.webcomponents.FileDownloader;
import code.apps.ripple.webcomponents.OnImageDownloadFinished;

public class DownloadService extends IntentService implements OnImageDownloadFinished {
    private Builder a;
    private NotificationManager b;

    public DownloadService() {
        super("Download Service");
    }

    /* Access modifiers changed, original: protected */
    public void onHandleIntent(Intent intent) {
        this.b = (NotificationManager) getSystemService("notification");
        this.a = new Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle("Download").setContentText("Downloading Files").setAutoCancel(true);
        this.b.notify(0, this.a.build());
        FileDownloader fileDownloader = new FileDownloader(getApplicationContext(), this);
    }

    private void a(Download download) {
        b(download);
        this.a.setProgress(100, download.getProgress(), false);
        this.b.notify(0, this.a.build());
    }

    private void b(Download download) {
        Intent intent = new Intent("MESSAGE_PROGRESS");
        intent.putExtra("KEY_DONWLOAD", download);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void a() {
        Download download = new Download();
        download.setProgress(100);
        b(download);
        this.b.cancel(0);
        this.a.setProgress(0, 0, false);
        this.a.setContentText("Files Downloaded...");
        this.b.notify(0, this.a.build());
    }

    public void onTaskRemoved(Intent intent) {
        this.b.cancel(0);
    }

    public void onImageDownloadFinished() {
        a();
    }

    public void publishProgress(Download download) {
        a(download);
    }
}
