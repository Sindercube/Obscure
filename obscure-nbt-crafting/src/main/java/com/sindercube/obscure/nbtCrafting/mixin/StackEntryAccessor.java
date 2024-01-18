package com.sindercube.obscure.nbtCrafting.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.StackEntry.class)
public interface StackEntryAccessor {

    @Invoker("<init>")
    static Ingredient.StackEntry createStackEntry(ItemStack stack) {
        throw new UnsupportedOperationException();
    }

}
