package com.sindercube.obscure.nbtCrafting.mixin;

import com.mojang.serialization.Codec;
import com.sindercube.obscure.nbtCrafting.ObscureNbtCrafting;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Shadow @Mutable @Final public static Codec<ItemStack> RECIPE_RESULT_CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void replaceResultCodec(CallbackInfo ci) {
        RECIPE_RESULT_CODEC = ObscureNbtCrafting.RESULT_ITEM_CODEC;
    }

}
