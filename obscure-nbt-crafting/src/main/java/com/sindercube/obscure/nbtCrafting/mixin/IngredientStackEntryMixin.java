package com.sindercube.obscure.nbtCrafting.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sindercube.obscure.nbtCrafting.ObscureNbtCrafting;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ingredient.StackEntry.class)
public abstract class IngredientStackEntryMixin {

    @Shadow @Mutable @Final static Codec<Ingredient.StackEntry> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceResultCodec(CallbackInfo ci) {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                ObscureNbtCrafting.INPUT_ITEM_CODEC.fieldOf("item").forGetter((entry) -> entry.stack)
        ).apply(instance, Ingredient.StackEntry::new));
    }

}
