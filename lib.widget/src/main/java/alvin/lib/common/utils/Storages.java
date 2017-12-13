package alvin.lib.common.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class Storages {
    private final Context context;

    public Storages(Context context) {
        this.context = context;
    }

    @NonNull
    private File makeExternalStoragePublicDirectory(@NonNull final String type,
                                                    @NonNull final String pathname) throws IOException {
        final File dir = Environment.getExternalStoragePublicDirectory(type);
        if (dir == null || !dir.exists()) {
            throw new IOException("Invalid external storage public directory with type: " + type);
        }

        return new File(dir, pathname);
    }

    @NonNull
    public File createImageCaptureFile(@NonNull final String pathname,
                                       @NonNull final String filename) throws IOException {
        final File dir = makeExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM, pathname);
        return new File(dir, filename);
    }
}
