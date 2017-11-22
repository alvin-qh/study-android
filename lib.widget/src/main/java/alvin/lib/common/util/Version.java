package alvin.lib.common.util;

import android.os.Build;

public class Version {
    public static final Version VERSIONS_O = new Version(Build.VERSION_CODES.O);
    public static final Version VERSIONS_N = new Version(Build.VERSION_CODES.N);
    public static final Version VERSIONS_M = new Version(Build.VERSION_CODES.M);

    private final int target;

    public Version(int targetSDKVersion) {
        this.target = targetSDKVersion;
    }

    public boolean isEqual() {
        return this.target == Build.VERSION.SDK_INT;
    }

    public boolean isEqualOrGreatThan() {
        return Build.VERSION.SDK_INT >= this.target;
    }

    public boolean isGreatThan() {
        return Build.VERSION.SDK_INT > this.target;
    }

    public boolean isLessThan() {
        return Build.VERSION.SDK_INT < this.target;
    }

    public boolean isEqualOrLessThan() {
        return Build.VERSION.SDK_INT <= this.target;
    }
}
