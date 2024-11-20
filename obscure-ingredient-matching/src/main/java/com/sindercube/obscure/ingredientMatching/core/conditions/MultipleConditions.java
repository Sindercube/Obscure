package com.sindercube.obscure.ingredientMatching.core.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.obscure.ingredientMatching.core.ItemCondition;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;

import java.util.List;
import java.util.function.Function;

public abstract class MultipleConditions implements ItemCondition {

	private final List<NbtElement> conditions;

	public List<NbtElement> getConditions() {
		return conditions;
	}

	public MultipleConditions(List<NbtElement> conditions) {
		this.conditions = conditions;
	}

	protected static ItemCondition.Type createType(
		Function<List<NbtElement>, MultipleConditions> function
	) {
		MapCodec<? extends MultipleConditions> codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ELEMENT_CODEC.listOf().fieldOf("conditions").forGetter(MultipleConditions::getConditions)
		).apply(instance, function));
		return new ItemCondition.Type(codec);
	}


	@Override
	public boolean parse(ItemStack stack) {
//		IngredientConditionRegistry.REGISTRY.getCodec().listOf().parse(NbtOps.INSTANCE, conditions).getOrThrow();
//		ComponentType<?> componentType = component.value();
//		Codec<?> codec = componentType.getCodecOrThrow();
//		var original = stack.getOrDefault(componentType, null);
//		var comparison = codec.parse(NbtOps.INSTANCE, value).getOrThrow();
//		return compare(original, comparison);
		return true;
	}

	public abstract <T> boolean compare(T original, T comparison);

//	public static class AnyOf extends MultipleConditions {
//
//		public AnyOf(List<NbtElement> conditions) {
//			super(conditions);
//		}
//
//
//
//	}
//
//	public static class AllOf extends MultipleConditions {}
//
//	public static class NoneOf extends MultipleConditions {}

}
