package in.mrasif.app.digiads.utils;

import java.io.File;

/**
 * Created by asif on 12/4/18.
 */

public interface DownloadHandler {
    void downloadProgressShow();
    void downloadProgressUpdate(int progress);
    void downloadProgressDismiss(File file);
}
