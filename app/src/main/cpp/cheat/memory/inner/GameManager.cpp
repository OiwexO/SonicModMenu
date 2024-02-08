// Created by OiwexO on 29.12.2023.

#include "../MemoryManager.h"
#include "../Sonic1Offsets.h"
#include "../Sonic2Offsets.h"

ADDRESS MemoryManager::GameManager::score = 0;
ADDRESS MemoryManager::GameManager::lives = 0;
ADDRESS MemoryManager::GameManager::rings = 0;
ADDRESS MemoryManager::GameManager::shield = 0;
ADDRESS MemoryManager::GameManager::invincibility = 0;
ADDRESS MemoryManager::GameManager::superForm = 0;

void MemoryManager::GameManager::initialize(ADDRESS _gameModuleBase, GameVersion gameVersion) {
    if (gameVersion == GameVersion::SONIC_1) {
        score = _gameModuleBase + Sonic1Offsets::Score;
        lives = _gameModuleBase + Sonic1Offsets::Lives;
        rings = _gameModuleBase + Sonic1Offsets::Rings;
        shield = _gameModuleBase + Sonic1Offsets::Shield;
        invincibility = _gameModuleBase + Sonic1Offsets::Invincibility;
        superForm = _gameModuleBase + Sonic1Offsets::SuperForm;
    } else if (gameVersion == GameVersion::SONIC_2) {
        score = _gameModuleBase + Sonic2Offsets::Score;
        lives = _gameModuleBase + Sonic2Offsets::Lives;
        rings = _gameModuleBase + Sonic2Offsets::Rings;
        shield = _gameModuleBase + Sonic2Offsets::Shield;
        invincibility = _gameModuleBase + Sonic2Offsets::Invincibility;
        superForm = _gameModuleBase + Sonic2Offsets::SuperForm;
    }
}

void MemoryManager::GameManager::setScore(int scoreValue) {
	write(score, scoreValue);
}

void MemoryManager::GameManager::setLives(int livesValue) {
	write(lives, livesValue);
}

void MemoryManager::GameManager::setRings(int ringsValue) {
	write(rings, ringsValue);
}

void MemoryManager::GameManager::enableShield() {
	write(shield, 1);
}

void MemoryManager::GameManager::disableShield() {
	write(shield, 0);
}

void MemoryManager::GameManager::enableInvincibility() {
	write(invincibility, INVINCIBILITY_DURATION);
}

void MemoryManager::GameManager::disableInvincibility() {
	write(invincibility, 0);
}

void MemoryManager::GameManager::enableSuperForm() {
	write(rings, 99);
	write(superForm, 1);
}

void MemoryManager::GameManager::disableSuperForm() {
	write(superForm, 0);
}
