// Created by OiwexO on 29.12.2023.

#include <jni.h>
#include "bridge/NativeBridge.h"

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *) {
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    if (NativeBridge::registerNativeMethods(env) != JNI_OK) {
        return JNI_ERR;
    }
    NativeBridge::startCheatThread();
    return JNI_VERSION_1_6;
}
