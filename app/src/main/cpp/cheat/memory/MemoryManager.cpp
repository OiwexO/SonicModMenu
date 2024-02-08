// Created by OiwexO on 29.12.2023.

#include "MemoryManager.h"

#include <unistd.h>
#include <cstring>
#include <cstdio>
#include <cstdlib>

bool MemoryManager::_isInitialized = false;
ADDRESS MemoryManager::gameModuleBase = 0;

ADDRESS MemoryManager::findModuleBase(const char *moduleName) {
    FILE *maps = fopen("/proc/self/maps", "rt");
    if (maps == nullptr) return 0;

    char line[512] = {0};
    ADDRESS address = 0;
    while (fgets(line, sizeof(line), maps)) {
        if (strstr(line, moduleName)) {
            address = (ADDRESS) strtoul(line, nullptr, 16);
            break;
        }
    }
    fclose(maps);
    return address;
}

bool MemoryManager::initialize() {
    while (gameModuleBase <= 0) {
        gameModuleBase = findModuleBase("libRetroEngineJNI");
        sleep(1);
    }
    GameManager::initialize(gameModuleBase, GlobalSettings::GAME_VERSION);
    _isInitialized = true;
    return true;
}
