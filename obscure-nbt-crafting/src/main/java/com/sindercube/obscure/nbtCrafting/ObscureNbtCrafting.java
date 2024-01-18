package com.sindercube.obscure.nbtCrafting;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ObscureNbtCrafting implements ModInitializer {

    public static final Codec<ItemStack> RECIPE_ITEM_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Registries.ITEM.getCodec().fieldOf("item").forGetter(ItemStack::getItem),
            Codec.intRange(1, 64).optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter((stack) -> Optional.ofNullable(stack.getNbt()))
    ).apply(instance, ItemStack::new));

    public static ItemStack stackFromJson(JsonObject json) {
        return RECIPE_ITEM_CODEC
                .decode(JsonOps.INSTANCE, json)
                .getOrThrow(true, LOGGER::error)
                .getFirst();
    }

    public static final String MOD_ID = "snc";
    public static final Logger LOGGER = LoggerFactory.getLogger("SimpleNBTCrafting");

    @Override
    public void onInitialize() {}

}