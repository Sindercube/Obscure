package com.sindercube.obscure.nbtCrafting.mixin;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.sindercube.obscure.nbtCrafting.ObscureNbtCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public abstract class IngredientMixin {

    @Shadow public abstract boolean isEmpty();
    @Shadow public abstract ItemStack[] getMatchingStacks();

    @Inject(method = "entryFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient$StackEntry;<init>(Lnet/minecraft/item/ItemStack;)V"), cancellable = true)
    private static void injected(JsonObject json, CallbackInfoReturnable<Ingredient.Entry> cir, @Local Item item) {
        ItemStack stack = ObscureNbtCrafting.stackFromJson(json);
        cir.setReturnValue(StackEntryAccessor.createStackEntry(stack));
    }

    /**
     * @author Sindercube
     * @reason IDK
     */
    @Overwrite
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null) return false;
        if (this.isEmpty()) return stack.isEmpty();

        ItemStack[] matches = this.getMatchingStacks();
        for (ItemStack match : matches) {
            if (ItemStack.canCombine(match, stack)) return true;
        }
        return false;
    }

}
