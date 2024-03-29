// Created by OiwexO on 29.12.2023.

#include <pthread.h>
#include <unistd.h>
#include "NativeBridge.h"
#include "NativeBridgeConstants.h"
#include "../cheat/data/GlobalSettings.h"
#include "../utils/logger.h"
#include "../cheat/memory/MemoryManager.h"

const char *NativeBridge::TAG = "jni_NativeBridge";
bool NativeBridge::isShouldRunThread = true;

void *NativeBridge::cheatThread(void *) {
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "DEBUG build");
    } else {
        LOGD(TAG, "RELEASE build");
        if (!MemoryManager::initialize()) {
            LOGD(TAG, "Could not initialize MemoryManager");
            return nullptr;
        }
        while (NativeBridge::isShouldRunThread) {
            if (GlobalSettings::isShieldEnabled) {
                MemoryManager::GameManager::enableShield();
            }
            if (GlobalSettings::isInvincibilityEnabled) {
                MemoryManager::GameManager::enableInvincibility();
            }
            if (GlobalSettings::isSuperFormEnabled) {
                MemoryManager::GameManager::enableSuperForm();
            }
            sleep(2);
        }
    }
    LOGD(TAG, "Exiting cheatThread()");
    return nullptr;
}

JNIEXPORT void JNICALL
NativeBridge::setGameVersion(JNIEnv *, jobject, jint gameVersion) {
    GameVersion version = static_cast<GameVersion>(gameVersion);
    switch (version) {
        case GameVersion::DEBUG:
            GlobalSettings::IS_DEBUG = true;
            GlobalSettings::GAME_VERSION = GameVersion::DEBUG;
            break;
        case GameVersion::SONIC_1:
        case GameVersion::SONIC_2:
            GlobalSettings::IS_DEBUG = false;
            GlobalSettings::GAME_VERSION = version;
            break;
        default:
            GlobalSettings::IS_DEBUG = true;
            GlobalSettings::GAME_VERSION = GameVersion::INCORRECT;
            break;
    }
    startCheatThread();
}

JNIEXPORT void JNICALL
NativeBridge::setScore(JNIEnv *, jobject, jint score) {
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "setScore: %d", score);
    } else {
        MemoryManager::GameManager::setScore(score);
    }
}

JNIEXPORT void JNICALL
NativeBridge::setLives(JNIEnv *, jobject, jint lives) {
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "setLives: %d", lives);
    } else {
        MemoryManager::GameManager::setLives(lives);
    }
}

JNIEXPORT void JNICALL
NativeBridge::setRings(JNIEnv *, jobject, jint rings) {
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "setRings: %d", rings);
    } else {
        MemoryManager::GameManager::setRings(rings);
    }
}

JNIEXPORT void JNICALL
NativeBridge::setShield(JNIEnv *, jobject, jboolean value) {
    GlobalSettings::isShieldEnabled = value;
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "enableShield: %d", value);
    } else {
        if (!GlobalSettings::isShieldEnabled) {
            MemoryManager::GameManager::disableShield();
        }
    }
}

JNIEXPORT void JNICALL
NativeBridge::setInvincibility(JNIEnv *, jobject, jboolean value) {
    GlobalSettings::isInvincibilityEnabled = value;
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "enableInvincibility: %d", value);
    } else {
        if (!GlobalSettings::isInvincibilityEnabled) {
            MemoryManager::GameManager::disableInvincibility();
        }
    }
}

JNIEXPORT void JNICALL
NativeBridge::setSuperForm(JNIEnv *, jobject, jboolean value) {
    GlobalSettings::isSuperFormEnabled = value;
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "enableSuperForm: %d", value);
    } else {
        if (!GlobalSettings::isSuperFormEnabled) {
            MemoryManager::GameManager::disableSuperForm();
        }
    }
}

JNIEXPORT void JNICALL
NativeBridge::setSaveFilePath(JNIEnv *env, jobject, jstring saveFilePathJvm) {
    jsize saveFilePathLength = env->GetStringUTFLength(saveFilePathJvm);
    char *saveFilePath = new char[saveFilePathLength + 1];
    env->GetStringUTFRegion(saveFilePathJvm, 0, saveFilePathLength, saveFilePath);
    saveFilePath[saveFilePathLength] = '\0';
    const char *filename = "/SGame.bin";
    size_t arraySize = saveFilePathLength + strlen(filename) + 1;
    char *completePath = new char[arraySize];
    strcpy(completePath, saveFilePath);
    strcat(completePath, filename);
    MemoryManager::SaveEditor::setSaveFilePath(completePath);
    delete[] saveFilePath;
    delete[] completePath;
    if (GlobalSettings::IS_DEBUG) {
        LOGD(TAG, "save file path: %s", completePath);
    }
}

JNIEXPORT jintArray JNICALL
NativeBridge::readSaveFile(JNIEnv *env, jobject, jint slotIndex) {
    int *saveSlotData = MemoryManager::SaveEditor::readSaveFile(slotIndex);
    jintArray saveSlotDataJvm = env->NewIntArray(MemoryManager::SaveEditor::SAVE_DATA_SIZE);
    env->SetIntArrayRegion(
            saveSlotDataJvm,
            0,
            MemoryManager::SaveEditor::SAVE_DATA_SIZE,
            saveSlotData
    );
    delete[] saveSlotData;
    return saveSlotDataJvm;
}

JNIEXPORT jboolean JNICALL
NativeBridge::writeSaveFile(JNIEnv *env, jobject, jint slotIndex, jintArray saveSlotDataJvm) {
    int *saveSlotData = new int[MemoryManager::SaveEditor::SAVE_DATA_SIZE]{};
    env->GetIntArrayRegion(
            saveSlotDataJvm,
            0,
            MemoryManager::SaveEditor::SAVE_DATA_SIZE,
            saveSlotData
    );
    jboolean isWrittenSuccessfully = MemoryManager::SaveEditor::writeSaveFile(
            slotIndex,
            saveSlotData
            );
    delete[] saveSlotData;
    return isWrittenSuccessfully;
}

void NativeBridge::startCheatThread() {
    pthread_t thread;
    pthread_create(&thread, nullptr, cheatThread, nullptr);
}

JNIEXPORT void JNICALL
NativeBridge::exitThread(JNIEnv *, jobject) {
    isShouldRunThread = false;
}

int NativeBridge::registerNativeMethods(JNIEnv *env) {
    if (env == nullptr) {
        LOGE(TAG, "JNIEnv pointer is nullptr");
        return JNI_ERR;
    }
    jclass kotlinNativeBridgeClass = env->FindClass(CLASS_NATIVE_BRIDGE_KOTLIN);
    if (!kotlinNativeBridgeClass) {
        LOGE(TAG, "Could not find NativeBridge class in Kotlin");
        return JNI_ERR;
    }
    JNINativeMethod methods[] = {
            {METHOD_SET_GAME_VERSION,   SIG_SET_GAME_VERSION,   reinterpret_cast<void *>(NativeBridge::setGameVersion)},
            {METHOD_SET_SCORE,          SIG_SET_SCORE,          reinterpret_cast<void *>(NativeBridge::setScore)},
            {METHOD_SET_LIVES,          SIG_SET_LIVES,          reinterpret_cast<void *>(NativeBridge::setLives)},
            {METHOD_SET_RINGS,          SIG_SET_RINGS,          reinterpret_cast<void *>(NativeBridge::setRings)},
            {METHOD_SET_SHIELD,         SIG_SET_SHIELD,         reinterpret_cast<void *>(NativeBridge::setShield)},
            {METHOD_SET_INVINCIBILITY,  SIG_SET_INVINCIBILITY,  reinterpret_cast<void *>(NativeBridge::setInvincibility)},
            {METHOD_SET_SUPER_FORM,     SIG_SET_SUPER_FORM,     reinterpret_cast<void *>(NativeBridge::setSuperForm)},
            {METHOD_SET_SAVE_FILE_PATH, SIG_SET_SAVE_FILE_PATH, reinterpret_cast<void *>(NativeBridge::setSaveFilePath)},
            {METHOD_READ_SAVE_FILE,     SIG_READ_SAVE_FILE,     reinterpret_cast<void *>(NativeBridge::readSaveFile)},
            {METHOD_WRITE_SAVE_FILE,    SIG_WRITE_SAVE_FILE,    reinterpret_cast<void *>(NativeBridge::writeSaveFile)},
            {METHOD_EXIT_THREAD,        SIG_EXIT_THREAD,        reinterpret_cast<void *>(NativeBridge::exitThread)}
    };
    int nMethods = (sizeof(methods) / sizeof(methods[0]));
    int registrationResult = env->RegisterNatives(kotlinNativeBridgeClass, methods, nMethods);
    if (registrationResult != JNI_OK) {
        LOGE(TAG, "Could not register native methods");
        return JNI_ERR;
    }
    return JNI_OK;
}