package com.sindercube.obscure.globalDatapacks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;

public class ObscureGlobalDatapacks implements ModInitializer {

    private static final String DATAPACKS_DIRECTORY = "datapacks";
    public static final Path DATAPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve(DATAPACKS_DIRECTORY);

    @Override
    public void onInitialize() {
        File datapacksPath = DATAPACKS_PATH.toFile();
        if (!datapacksPath.exists()) datapacksPath.mkdir();
    }

}