package alvin.lib.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Permissions {
    private final Activity activity;
    private final Set<String> permissions = new HashSet<>();

    public Permissions(final Activity activity,
                       final Collection<String> permissions) {
        this.activity = activity;
        this.permissions.addAll(permissions);
    }

    public Permissions(@NonNull final Activity activity,
                       @NonNull final String... permissions) {
        this(activity, Arrays.asList(permissions));
    }

    /**
     * Check the permissions and get which are dined
     *
     * @return Collection of dined permissions, empty if no permission was dined
     * @see android.content.Context#checkSelfPermission(String)
     */
    @NonNull
    public Set<String> dinedPermissions() {
        final Set<String> deniedPermissions = new HashSet<>();
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return Collections.unmodifiableSet(deniedPermissions);
    }

    /**
     * Ask user to grant some permissions which were not allowed yet
     *
     * @param requestCode A int value will be pass to
     *                    {@link Activity#onRequestPermissionsResult(int, String[], int[])} method
     * @return {@link Status#ALLOW}, {@link Status#DENY} or {@link Status#REQUIRED}
     *
     * @see Permissions#dinedPermissions()
     * @see Activity#shouldShowRequestPermissionRationale(String)
     * @see Activity#requestPermissions(String[], int)
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    @NonNull
    public Status requestPermissions(final int requestCode) {
        final Set<String> deniedPermissions = dinedPermissions();
        if (deniedPermissions.isEmpty()) {
            return Status.ALLOW;
        }

        final Set<String> required = new HashSet<>();
        for (String permission : deniedPermissions) {
            if (!activity.shouldShowRequestPermissionRationale(permission)) {
                return Status.DENY;
            }
            required.add(permission);
        }

        if (!required.isEmpty()) {
            activity.requestPermissions(required.toArray(new String[required.size()]), requestCode);
        }
        return Status.REQUIRED;
    }

    @NonNull
    public static Status checkPermissionsWithResults(final String[] permissions,
                                                     final int[] results) {
        for (int i = 0; i < permissions.length; i++) {
            if (results[i] == PackageManager.PERMISSION_DENIED) {
                return Status.DENY;
            }
        }
        return Status.ALLOW;
    }

    public enum Status {
        /**
         * If some permissions are allowed
         */
        ALLOW,

        /**
         * If some permissions are denied
         */
        DENY,

        /**
         * The permissions not granted by user
         */
        REQUIRED
    }
}
