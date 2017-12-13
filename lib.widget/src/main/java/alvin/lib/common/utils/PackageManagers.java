package alvin.lib.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

public class PackageManagers {
    private final Context context;
    private PackageManager packageManager;

    public PackageManagers(Context context) {
        this.context = context;
    }

    private PackageManager getPackageManager() {
        if (packageManager == null) {
            packageManager = context.getPackageManager();
        }
        return packageManager;
    }

    /**
     * Check if the specified package of given action name was exist.
     *
     * @see PackageManager#queryIntentActivities(Intent, int)
     * @see PackageManager#MATCH_DEFAULT_ONLY
     */
    public boolean isPackageExist(final String action) {
        final PackageManager manager = getPackageManager();

        final Intent intent = new Intent(action);
        final List<ResolveInfo> infos = manager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !infos.isEmpty();
    }

    /**
     * Check if camera application exist, and if 'Image Capture Activity' available.
     *
     * @see PackageManagers#isPackageExist(String)
     * @see MediaStore#ACTION_IMAGE_CAPTURE
     */
    public boolean isImageCapturePackageExist() {
        return isPackageExist(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public Intent grantUriPermission(final Intent intent, final Uri uri, final int uriPermission) {
        final PackageManager manager = getPackageManager();

        final List<ResolveInfo> infos = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        infos.stream()
                .map(info -> info.activityInfo.packageName)
                .forEach(name -> context.grantUriPermission(name, uri, uriPermission));

        return intent;
    }
}
