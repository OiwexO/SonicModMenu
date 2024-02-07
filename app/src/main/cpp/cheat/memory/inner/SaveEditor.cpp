// Created by OiwexO on 29.12.2023.

#include "../MemoryManager.h"
#include <fstream>

unsigned int MemoryManager::SaveEditor::saveRam[SAVE_RAM_SIZE] = {0};
char* MemoryManager::SaveEditor::saveFilePath = nullptr;

void MemoryManager::SaveEditor::setSaveFilePath(char *path) {
    saveFilePath = new char[strlen(path)];
    strcpy(saveFilePath, path);
}

int* MemoryManager::SaveEditor::readSaveFile(int slotIndex) {
    int* saveSlotData = new int[SAVE_DATA_SIZE]{};
    if (saveFilePath == nullptr) {
        return saveSlotData;
    }
	FILE* saveFile = fopen(saveFilePath, "rb");
    if (saveFile == nullptr) {
        return saveSlotData;
    }
    size_t dataRead = fread(saveRam, 4u, SAVE_RAM_SIZE, saveFile);
    fclose(saveFile);
	if (dataRead <= 0) {
		return saveSlotData;
	}
	int saveSlotOffset = slotIndex * 4 * 8;
	for (int i = 0; i < SAVE_DATA_SIZE; i++) {
		saveSlotData[i] = *(int*)((char*)saveRam + (saveSlotOffset | (i * 4)));
	}
    /*{
		*(int*)((char*)saveRam + (saveSlotOffset)), // character
		*(int*)((char*)saveRam + (saveSlotOffset | 4)), // lives
		*(int*)((char*)saveRam + (saveSlotOffset | 8)), // score
		*(int*)((char*)saveRam + (saveSlotOffset | 12)), // bonusScore
		*(int*)((char*)saveRam + (saveSlotOffset | 16)), // stage
		*(int*)((char*)saveRam + (saveSlotOffset | 20)); // emeralds
	}*/
	return saveSlotData;
}

bool MemoryManager::SaveEditor::writeSaveFile(int slotIndex, const int* saveSlotData) {
    if (saveFilePath == nullptr) {
        return false;
    }
	FILE* saveFile = fopen(saveFilePath, "wb");
    if (saveFile == nullptr) return false;
	int saveSlotOffset = slotIndex * 4 * 8;
	for (int i = 0; i < SAVE_DATA_SIZE; i++) {
		*(int*)((char*)saveRam + (saveSlotOffset | (i * 4))) = saveSlotData[i];
	}
	/*
	*(int*)((char*)saveRam + (saveSlotOffset)) = saveSlotData[0];
    *(int*)((char*)saveRam + (saveSlotOffset | 4)) = saveSlotData[1];
    *(int*)((char*)saveRam + (saveSlotOffset | 8)) = saveSlotData[2];
    *(int*)((char*)saveRam + (saveSlotOffset | 12)) = saveSlotData[3];
    *(int*)((char*)saveRam + (saveSlotOffset | 16)) = saveSlotData[4];
    *(int*)((char*)saveRam + (saveSlotOffset | 20)) = emeralds;
	*/
	size_t dataWritten = fwrite(saveRam, 4u, SAVE_RAM_SIZE, saveFile);
    fclose(saveFile);
    return (dataWritten > 0);
}
