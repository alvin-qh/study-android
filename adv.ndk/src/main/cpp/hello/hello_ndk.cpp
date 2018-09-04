#include <jni.h>

extern "C" {

JNIEXPORT jstring JNICALL
Java_alvin_adv_ndk_hello_HelloNDK_helloNDK(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("Hello NDK");
}

}

