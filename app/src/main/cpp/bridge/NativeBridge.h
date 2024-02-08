// Created by OiwexO on 29.12.2023.

#pragma once
#include <jni.h>
#include <vector>

class NativeBridge {
private:
    static const char* TAG;
    static bool isShouldRunThread;

    static void* cheatThread(void*);
    static JNIEXPORT void JNICALL setGameVersion(JNIEnv*, jobject, jint gameVersion);
    static JNIEXPORT void JNICALL setScore(JNIEnv*, jobject, jint score);
    static JNIEXPORT void JNICALL setLives(JNIEnv*, jobject, jint lives);
    static JNIEXPORT void JNICALL setRings(JNIEnv*, jobject, jint rings);
    static JNIEXPORT void JNICALL setShield(JNIEnv*, jobject, jboolean value);
    static JNIEXPORT void JNICALL setInvincibility(JNIEnv*, jobject, jboolean value);
    static JNIEXPORT void JNICALL setSuperForm(JNIEnv*, jobject, jboolean value);
    static JNIEXPORT void JNICALL setSaveFilePath(JNIEnv* env, jobject, jstring saveFilePathJvm);
    static JNIEXPORT jintArray JNICALL readSaveFile(JNIEnv* env, jobject, jint slotIndex);
    static JNIEXPORT jboolean JNICALL writeSaveFile(JNIEnv* env, jobject, jint slotIndex, jintArray saveSlotDataJvm);
    static void startCheatThread();
    static JNIEXPORT void JNICALL exitThread(JNIEnv*, jobject);

public:
    static int registerNativeMethods(JNIEnv* env);

};
