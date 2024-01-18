package com.sindercube.obscure.nbtCrafting.mixin;

import com.google.gson.JsonObject;
import com.sindercube.obscure.nbtCrafting.ObscureNbtCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    /**
     * @author Sindercube
     * @reason IDK
     */
    @Overwrite
    public static ItemStack outputFromJson(JsonObject json) {
        return ObscureNbtCrafting.stackFromJson(json);
    }

}
