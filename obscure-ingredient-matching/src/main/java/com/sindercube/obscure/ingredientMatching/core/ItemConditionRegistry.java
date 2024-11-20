package com.sindercube.obscure.ingredientMatching.core;

import com.mojang.serialization.Lifecycle;
import com.sindercube.obscure.Obscure;
import com.sindercube.obscure.ingredientMatching.core.conditions.ComponentConditions;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class ItemConditionRegistry {

	public static final Identifier RAW_KEY = Obscure.of("item_condition");
	public static final RegistryKey<Registry<ItemCondition.Type>> KEY = RegistryKey.ofRegistry(RAW_KEY);
	public static final Registry<ItemCondition.Type> REGISTRY = new SimpleRegistry<>(KEY, Lifecycle.stable());

	public static void init() {
		Registry.register(REGISTRY, Obscure.of("equal"), ComponentConditions.Equals.TYPE);
		Registry.register(REGISTRY, Obscure.of("less_than"), ComponentConditions.LessThan.TYPE);
		Registry.register(REGISTRY, Obscure.of("more_than"), ComponentConditions.MoreThan.TYPE);
	}

}
