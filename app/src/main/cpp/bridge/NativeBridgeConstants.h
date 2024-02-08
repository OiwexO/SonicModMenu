// Created by OiwexO on 29.12.2023.

#pragma once

/* CLASS NAMES ===================================================================================*/

#define CLASS_NATIVE_BRIDGE_KOTLIN "com/iwex/sonicmodmenu/domain/NativeBridge$Companion"

/*================================================================================================*/



/* METHOD NAMES ==================================================================================*/

#define METHOD_SET_GAME_VERSION "setGameVersion"
#define METHOD_SET_SCORE "setScore"
#define METHOD_SET_LIVES "setLives"
#define METHOD_SET_RINGS "setRings"
#define METHOD_SET_SHIELD "setShield"
#define METHOD_SET_INVINCIBILITY "setInvincibility"
#define METHOD_SET_SUPER_FORM "setSuperForm"
#define METHOD_READ_SAVE_FILE "readSaveFile"
#define METHOD_WRITE_SAVE_FILE "writeSaveFile"
#define METHOD_SET_SAVE_FILE_PATH "setSaveFilePath"
#define METHOD_EXIT_THREAD "exitThread"

/*================================================================================================*/



/* METHOD SIGNATURES =============================================================================*/

#define SIG_SET_GAME_VERSION "(I)V"
#define SIG_SET_SCORE "(I)V"
#define SIG_SET_LIVES "(I)V"
#define SIG_SET_RINGS "(I)V"
#define SIG_SET_SHIELD "(Z)V"
#define SIG_SET_INVINCIBILITY "(Z)V"
#define SIG_SET_SUPER_FORM "(Z)V"
#define SIG_READ_SAVE_FILE "(I)[I"
#define SIG_WRITE_SAVE_FILE "(I[I)Z"
#define SIG_SET_SAVE_FILE_PATH "(Ljava/lang/String;)V"
#define SIG_EXIT_THREAD "()V"

/*==================================================================================================*/