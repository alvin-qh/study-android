package alvin.lib.common.utils;

import android.os.Build;

public class Versions {
    public static final Versions VERSIONS_O = new Versions(Build.VERSION_CODES.O);
    public static final Versions VERSIONS_N = new Versions(Build.VERSION_CODES.N);
    public static final Versions VERSIONS_M = new Versions(Build.VERSION_CODES.M);
    public static final Versions VERSIONS_LOLLIPOP = new Versions(Build.VERSION_CODES.LOLLIPOP);

    private final int target;

    public Versions(int targetSDKVersion) {
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
