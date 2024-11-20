package com.sindercube.obscure;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Obscure implements ModInitializer {

    public static final String MOD_ID = "obscure";

    public static Identifier of(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger("Obscure");

    @Override
    public void onInitialize() {
        LOGGER.info("Obscure Initialized!");
    }

}
