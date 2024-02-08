// Created by OiwexO on 29.12.2023.

#pragma once
#include <jni.h>

enum GameVersion {
    INCORRECT = -1,
    DEBUG = 0,
    SONIC_1 = 1,
    SONIC_2 = 2
};

class GlobalSettings {

public:
    static GameVersion GAME_VERSION;
    static bool IS_DEBUG;
    static bool isShieldEnabled;
    static bool isInvincibilityEnabled;
    static bool isSuperFormEnabled;

};

