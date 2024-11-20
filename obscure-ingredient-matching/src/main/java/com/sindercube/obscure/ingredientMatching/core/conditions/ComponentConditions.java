package com.sindercube.obscure.ingredientMatching.core.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.obscure.ingredientMatching.core.ItemCondition;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.function.BiFunction;

public abstract class ComponentConditions implements ItemCondition {

	private final RegistryEntry<ComponentType<?>> component;
	private final NbtElement value;

	public RegistryEntry<ComponentType<?>> getComponent() {
		return component;
	}
	public NbtElement getValue() {
		return value;
	}

	public ComponentConditions(RegistryEntry<ComponentType<?>> component, NbtElement value) {
		this.component = component;
		this.value = value;
	}

	protected static ItemCondition.Type createType(
		BiFunction<RegistryEntry<ComponentType<?>>, NbtElement, ComponentConditions> function
	) {
		MapCodec<? extends ComponentConditions> codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Registries.DATA_COMPONENT_TYPE.getEntryCodec().fieldOf("component").forGetter(ComponentConditions::getComponent),
			ELEMENT_CODEC.fieldOf("value").forGetter(ComponentConditions::getValue)
		).apply(instance, function));
		return new ItemCondition.Type(codec);
	}


	@Override
	public boolean parse(ItemStack stack) {
		ComponentType<?> componentType = component.value();
		Codec<?> codec = componentType.getCodecOrThrow();
		var original = stack.getOrDefault(componentType, null);
		var comparison = codec.parse(NbtOps.INSTANCE, value).getOrThrow();
		return compare(original, comparison);
	}

	public abstract <T> boolean compare(T original, T comparison);


	public static class Equals extends ComponentConditions {

		public Equals(RegistryEntry<ComponentType<?>> component, NbtElement value) {
			super(component, value);
		}

		public static final ItemCondition.Type TYPE = createType(Equals::new);

		@Override
		public Type getType() {
			return TYPE;
		}

		@Override
		public <T> boolean compare(T original, T comparison) {
			return original.equals(comparison);
		}

	}

	public static class LessThan extends ComponentConditions {

		public LessThan(RegistryEntry<ComponentType<?>> component, NbtElement value) {
			super(component, value);
		}

		public static final ItemCondition.Type TYPE = createType(LessThan::new);

		@Override
		public Type getType() {
			return TYPE;
		}

		@Override
		public <T> boolean compare(T original, T comparison) {
			if (!(original instanceof Number originalNumber)) throw new ClassCastException();
			if (!(comparison instanceof Number comparisonNumber)) throw new ClassCastException();

			return originalNumber.floatValue() < comparisonNumber.floatValue();
		}

	}

	public static class MoreThan extends ComponentConditions {

		public MoreThan(RegistryEntry<ComponentType<?>> component, NbtElement value) {
			super(component, value);
		}

		public static final ItemCondition.Type TYPE = createType(MoreThan::new);

		@Override
		public Type getType() {
			return TYPE;
		}

		@Override
		public <T> boolean compare(T original, T comparison) {
			if (!(original instanceof Number originalNumber)) throw new ClassCastException();
			if (!(comparison instanceof Number comparisonNumber)) throw new ClassCastException();

			return originalNumber.floatValue() > comparisonNumber.floatValue();
		}

	}

}
