package com.sindercube.obscure.ingredientMatching.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.obscure.ingredientMatching.asm.ConditionalEntry;
import com.sindercube.obscure.ingredientMatching.core.ItemCondition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

public class IngredientMixins {

	@Mixin(Ingredient.class)
	public static abstract class IngredientMixin {

		@Shadow @Final private Ingredient.Entry[] entries;

		@Shadow public abstract boolean isEmpty();

		/**
		 * @author Sindercube
		 * @reason Test with additional conditions
		 */
		@Overwrite
		public boolean test(@Nullable ItemStack testingStack) {
			if (testingStack == null) return false;
			if (this.isEmpty()) return testingStack.isEmpty();

			for (Ingredient.Entry entry : this.entries) {
				for (ItemStack possibleStack : entry.getStacks()) {
					if (!possibleStack.isOf(testingStack.getItem())) continue;
					if (((ConditionalEntry)entry).test(testingStack)) return true;
				}
			}

			return false;
		}

	}

	@Mixin(Ingredient.StackEntry.class)
	public static class StackEntryMixin implements ConditionalEntry {

		@Shadow @Final @Mutable static Codec<Ingredient.StackEntry> CODEC;

		@Unique private List<ItemCondition> conditions = new ArrayList<>();

		@Unique
		public void setConditions(List<ItemCondition> conditions) {
			this.conditions = conditions;
		}

		@Override
		public List<ItemCondition> getConditions() {
			return conditions;
		}

		@Unique
		private static Ingredient.StackEntry create(ItemStack stack, List<ItemCondition> conditions) {
			Ingredient.StackEntry entry = new Ingredient.StackEntry(stack);
			((StackEntryMixin)(Object)entry).setConditions(conditions);
			return entry;
		}

		@Inject(method = "<clinit>", at = @At("TAIL"))
		private static void updateCodec(CallbackInfo ci) {
			CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ItemStack.REGISTRY_ENTRY_CODEC.fieldOf("item").forGetter(Ingredient.StackEntry::stack),
				ItemCondition.CODEC.listOf().optionalFieldOf("conditions", new ArrayList<>()).forGetter(ConditionalEntry::getConditions)
			).apply(instance, StackEntryMixin::create));
		}

	}

	@Mixin(Ingredient.TagEntry.class)
	public static class TagEntryMixin implements ConditionalEntry {

		@Shadow @Final @Mutable static Codec<Ingredient.TagEntry> CODEC;

		@Unique private List<ItemCondition> conditions = new ArrayList<>();

		@Unique
		public void setConditions(List<ItemCondition> conditions) {
			this.conditions = conditions;
		}

		@Override
		public List<ItemCondition> getConditions() {
			return conditions;
		}

		@Unique
		private static Ingredient.TagEntry create(TagKey<Item> tag, List<ItemCondition> conditions) {
			Ingredient.TagEntry entry = new Ingredient.TagEntry(tag);
			((TagEntryMixin)(Object)entry).setConditions(conditions);
			return entry;
		}

		@Inject(method = "<clinit>", at = @At("TAIL"))
		private static void updateCodec(CallbackInfo ci) {
			CODEC = RecordCodecBuilder.create(instance -> instance.group(
				TagKey.unprefixedCodec(RegistryKeys.ITEM).fieldOf("tag").forGetter(Ingredient.TagEntry::tag),
				ItemCondition.CODEC.listOf().optionalFieldOf("conditions", new ArrayList<>()).forGetter(ConditionalEntry::getConditions)
			).apply(instance, TagEntryMixin::create));
		}

	}

}
