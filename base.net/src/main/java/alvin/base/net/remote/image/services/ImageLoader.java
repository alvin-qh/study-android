package alvin.base.net.remote.image.services;

import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import alvin.lib.common.utils.Cache;
import alvin.lib.common.utils.MD5;

public class ImageLoader {
    private static final int BUFFER_SIZE = 1024 * 1024 * 10;

    private final String cachePath;
    private final Cache<Drawable> cache;

    public ImageLoader(String cachePath, Cache<Drawable> cache) {
        this.cachePath = cachePath;
        this.cache = cache;
    }

    public Drawable load(String imgSrc) throws IOException {
        final String cacheFileName = new MD5().digestToString(imgSrc);

        if (cache != null) {
            Optional<Drawable> mayDrawable = cache.get(cacheFileName);
            if (mayDrawable.isPresent()) {
                return mayDrawable.get();
            }
        }

        Drawable drawable;

        final Path cacheFile = Paths.get(cachePath, cacheFileName);
        if (!Files.exists(cacheFile)) {
            final URL imageUrl = new URL(imgSrc);
            try (InputStream in = new BufferedInputStream(imageUrl.openStream())) {
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(cacheFile.toFile()))) {
                    int n;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((n = in.read(buffer)) > 0) {
                        out.write(buffer, 0, n);
                    }
                }
            }
        }
        drawable = Drawable.createFromPath(cacheFile.toAbsolutePath().toString());

        if (cache != null) {
            cache.put(cacheFileName, drawable);
        }
        return drawable;
    }

    public void dispose() {
        if (cache != null) {
            cache.clear();
        }
    }
}
