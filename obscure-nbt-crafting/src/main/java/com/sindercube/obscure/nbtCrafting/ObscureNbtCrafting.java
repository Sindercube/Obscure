package com.sindercube.obscure.nbtCrafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class ObscureNbtCrafting implements ModInitializer {

    public static ItemStack stackWithoutCount(RegistryEntry<Item> item, Optional<NbtCompound> nbt) {
        ItemStack stack = new ItemStack(item, 1);
        nbt.ifPresent(stack::setNbt);
        return stack;
    }

    public static final Codec<ItemStack> INPUT_ITEM_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Registries.ITEM.createEntryCodec().fieldOf("item").forGetter(ItemStack::getRegistryEntry),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter((stack) -> Optional.ofNullable(stack.getNbt()))
    ).apply(instance, ObscureNbtCrafting::stackWithoutCount));

    public static final Codec<ItemStack> RESULT_ITEM_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Registries.ITEM.createEntryCodec().fieldOf("item").forGetter(ItemStack::getRegistryEntry),
            Codecs.createStrictOptionalFieldCodec(Codecs.POSITIVE_INT, "count", 1).forGetter(ItemStack::getCount),
            NbtCompound.CODEC.optionalFieldOf("data").forGetter((stack) -> Optional.ofNullable(stack.getNbt()))
    ).apply(instance, ItemStack::new));

    @Override
    public void onInitialize() {}

}