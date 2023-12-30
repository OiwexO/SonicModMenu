// Created by OiwexO on 29.12.2023.

#pragma once
#include <jni.h>
#include <vector>

class NativeBridge {
private:
    static const char* TAG;
    static bool isShouldRunThread;
    static constexpr int VERSION_INCORRECT = -1;
    static constexpr int VERSION_DEBUG = 0;

    static void* cheatThread(void*);
    static void setGameVersion(JNIEnv*, jobject, jint gameVersion);
    static void setScore(JNIEnv*, jobject, jint score);
    static void setLives(JNIEnv*, jobject, jint lives);
    static void setRings(JNIEnv*, jobject, jint rings);
    static void setShield(JNIEnv*, jobject, jboolean value);
    static void setInvincibility(JNIEnv*, jobject, jboolean value);
    static void setSuperForm(JNIEnv*, jobject, jboolean value);
    static jintArray readSaveFile(JNIEnv* env, jobject, jint slotIndex);
    static jboolean writeSaveFile(JNIEnv* env, jobject, jint slotIndex, jintArray saveSlotDataJvm);
    static void setSaveFilePath(JNIEnv* env, jobject, jstring saveFilePathJvm);
    static void exitThread(JNIEnv*, jobject);

public:
    static int registerNativeMethods(JNIEnv* env);
    static void startCheatThread();

};
