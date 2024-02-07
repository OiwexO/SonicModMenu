// Created by OiwexO on 29.12.2023.

#include "GlobalSettings.h"

#ifdef BUILD_TYPE_DEBUG
bool GlobalSettings::IS_DEBUG = true;
#else
bool GlobalSettings::IS_DEBUG = false;
#endif

GameVersions GlobalSettings::GAME_VERSION = GameVersions::INCORRECT;
bool GlobalSettings::isShieldEnabled = false;
bool GlobalSettings::isInvincibilityEnabled = false;
bool GlobalSettings::isSuperFormEnabled = false;






