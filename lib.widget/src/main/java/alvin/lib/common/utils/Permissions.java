package alvin.lib.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

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
    @Nullable
    public String[] dinedPermissions() {
        final Set<String> deniedPermissions = new HashSet<>();
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.isEmpty() ? null :
                deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }

    /**
     * Ask user to grant some permissions which were not allowed yet
     *
     * @param requestCode A int value will be pass to
     *                    {@link Activity#onRequestPermissionsResult(int, String[], int[])} method
     * @return {@link Status#ALLOWED}, {@link Status#DENIED} or {@link Status#REQUIRED}
     * @see Permissions#dinedPermissions()
     * @see Activity#shouldShowRequestPermissionRationale(String)
     * @see Activity#requestPermissions(String[], int)
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    @NonNull
    public Status requestPermissions(final int requestCode,
                                     @Nullable final Supplier<Boolean> askFn) {
        final String[] deniedPermissions = dinedPermissions();
        if (deniedPermissions == null) {
            return Status.ALLOWED;
        }

        Status status = Status.REQUIRED;
        for (String permission : deniedPermissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                status = Status.DENIED;
            }
        }
        if (askFn == null || askFn.get()) {
            activity.requestPermissions(deniedPermissions, requestCode);
        }
        return status;
    }

    @NonNull
    public Status requestPermissions(final int requestCode) {
        return requestPermissions(requestCode, null);
    }

    @NonNull
    public static Status checkPermissionsWithResults(final String[] permissions,
                                                     final int[] results) {
        for (int i = 0; i < permissions.length; i++) {
            if (results[i] == PackageManager.PERMISSION_DENIED) {
                return Status.DENIED;
            }
        }
        return Status.ALLOWED;
    }

    public enum Status {
        /**
         * If all permissions are allowed
         */
        ALLOWED,

        /**
         * If some permissions are denied
         */
        DENIED,

        /**
         * The permissions not granted by user
         */
        REQUIRED
    }
}
