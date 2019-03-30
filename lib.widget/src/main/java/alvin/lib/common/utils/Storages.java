package alvin.lib.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;

public class Storages {
    private final Context context;

    public Storages(Context context) {
        this.context = context;
    }

    /**
     * Create public directory on external storage.
     *
     * @param type Type of public directory, such as:
     *             {@link Environment#DIRECTORY_DCIM}
     *             {@link Environment#DIRECTORY_DOWNLOADS}
     *             {@link Environment#DIRECTORY_MUSIC}
     *             ... and so on
     */
    @NonNull
    private File makeExternalStoragePublicDirectory(@NonNull final String type,
                                                    @NonNull final String pathname) throws IOException {
        final File dir = Environment.getExternalStoragePublicDirectory(type);
        if (dir == null || !dir.exists()) {
            throw new IOException("Invalid external storage public directory with type: " + type);
        }

        return new File(dir, pathname);
    }

    /**
     * Create a file in external public DCIM directory.
     *
     * @see Storages#makeExternalStoragePublicDirectory(String, String)
     * @see Environment#DIRECTORY_DCIM
     */
    @NonNull
    public File createImageCaptureFile(@NonNull final String pathname,
                                       @NonNull final String filename) throws IOException {
        final File dir = makeExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM, pathname);
        return new File(dir, filename);
    }

    @NonNull
    public File createExternalStorageFile(@NonNull String pathname,
                                          @NonNull String filename) throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory(), pathname);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Cannot create folder " + dir.getAbsolutePath());
            }
        }
        return new File(dir, filename);
    }
}
