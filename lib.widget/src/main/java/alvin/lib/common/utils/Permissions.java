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

    @NonNull
    public Set<String> deniedPermissions() {
        final Set<String> deniedPermissions = new HashSet<>();
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return Collections.unmodifiableSet(deniedPermissions);
    }

    @NonNull
    public Status requestPermissions(final int requestCode) {
        final Set<String> deniedPermissions = deniedPermissions();
        if (deniedPermissions.isEmpty()) {
            return Status.ALLOW;
        }

        final Set<String> required = new HashSet<>();
        for (String permission : deniedPermissions) {
            if (!activity.shouldShowRequestPermissionRationale(permission)) {
                return Status.DENNY;
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
                return Status.DENNY;
            }
        }
        return Status.ALLOW;
    }

    public enum Status {
        ALLOW, DENNY, REQUIRED
    }
}
