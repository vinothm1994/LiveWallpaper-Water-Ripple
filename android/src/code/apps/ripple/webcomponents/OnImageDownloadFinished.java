package code.apps.ripple.webcomponents;

import code.apps.ripple.webcomponents.downloadimage.Download;

public interface OnImageDownloadFinished {
    void onImageDownloadFinished();

    void publishProgress(Download download);
}
