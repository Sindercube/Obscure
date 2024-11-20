package com.sindercube.obscure.ingredientMatching.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.MapCodec;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;

public interface ItemCondition {

	Codec<ItemCondition> CODEC = ItemConditionRegistry.REGISTRY.getCodec()
		.dispatch("type", ItemCondition::getType, ItemCondition.Type::codec);

	Codec<NbtElement> ELEMENT_CODEC = Codec.PASSTHROUGH.xmap(
		dynamic -> dynamic.convert(NbtOps.INSTANCE).getValue(),
		element -> new Dynamic<>(NbtOps.INSTANCE, element)
	);


	boolean parse(ItemStack stack);

	Type getType();

	record Type(MapCodec<? extends ItemCondition> codec) {}

}
