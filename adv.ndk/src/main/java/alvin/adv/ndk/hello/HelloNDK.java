package alvin.adv.ndk.hello;

public class HelloNDK {
    static {
        System.loadLibrary("ndk_demo");
    }

    public native String helloNDK();
}
