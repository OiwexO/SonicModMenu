// Created by OiwexO on 29.12.2023.

#include "../MemoryManager.h"
// choose correct offsets according to the definition in CMakeLists.txt
#ifdef SONIC_1
#include "../Sonic1Offsets.h"
#elif defined(SONIC_2)
#include "../Sonic2Offsets.h"
#elif defined(SONIC_3)
#include "../Sonic3Offsets.h"
#elif defined(SONIC_CD)
#include "../SonicCDOffsets.h"
#endif

ADDRESS MemoryManager::GameManager::score = 0;
ADDRESS MemoryManager::GameManager::lives = 0;
ADDRESS MemoryManager::GameManager::rings = 0;
ADDRESS MemoryManager::GameManager::shield = 0;
ADDRESS MemoryManager::GameManager::invincibility = 0;
ADDRESS MemoryManager::GameManager::superForm = 0;

void MemoryManager::GameManager::initialize(ADDRESS _gameModuleBase) {
	score = _gameModuleBase + Offsets::Score;
	lives = _gameModuleBase + Offsets::Lives;
	rings = _gameModuleBase + Offsets::Rings;
	shield = _gameModuleBase + Offsets::Shield;
	invincibility = _gameModuleBase + Offsets::Invincibility;
	superForm = _gameModuleBase + Offsets::SuperForm;
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
