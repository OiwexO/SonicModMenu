// Created by OiwexO on 30.09.2023.

#pragma once

using ADDRESS = unsigned int;

class MemoryManager {
private:
	static bool isInitialised;

	// address of the libRetroEngineJNI.so library in game's memory
	static ADDRESS gameModuleBase;

	static inline ADDRESS findModuleBase(const char* moduleName);

	template<typename T>
	static T& read(ADDRESS address) {
		return *reinterpret_cast<T*>(address);
	}

	template<typename T>
	static void write(ADDRESS address, T value) {
//		*reinterpret_cast<T*>(address) = value;
	}

public:
	static bool initialize();
	static bool isInitialized() { return isInitialised; };
	static ADDRESS getGameModuleBase() { return gameModuleBase; }

	class GameManager {
	private:
		static ADDRESS score;
		static ADDRESS lives;
		static ADDRESS rings;
		static ADDRESS shield;
		static ADDRESS invincibility;
		static ADDRESS superForm;

	public:
		static void initialize(ADDRESS _gameModuleBase);
		static void setScore(int scoreValue);
		static void setLives(int livesValue);
		static void setRings(int ringsValue);
		static void setShield(bool value);
		static void setInvincibility(bool value);
		static void setSuperForm(bool value);

	};

	class SaveEditor {
	private:
		static constexpr int SAVE_RAM_SIZE = 0x2000;
		static unsigned int saveRam[SAVE_RAM_SIZE];
		static char* saveFilePath;
		static int saveSlotIndex;

	public:
		static constexpr int SAVE_DATA_SIZE = 6;
		static void setSaveFilePath(char* path);
		static int* readSaveFile(int slotIndex);
		static bool writeSaveFile(int slotIndex, const int* saveSlotData);

	};

};
