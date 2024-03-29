package com.sindercube.obscure.globalDatapacks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.sindercube.obscure.globalDatapacks.GlobalResourcePackSource;
import com.sindercube.obscure.globalDatapacks.ObscureGlobalDatapacks;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.util.path.SymlinkFinder;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VanillaDataPackProvider.class)
public class VanillaDataPackProviderMixin {

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyArg(
            method = "createManager(Ljava/nio/file/Path;Lnet/minecraft/util/path/SymlinkFinder;)Lnet/minecraft/resource/ResourcePackManager;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"
            )
    )
    private static ResourcePackProvider[] addGlobalDataPackProvider(ResourcePackProvider[] providers, @Local SymlinkFinder symlinkFinder) {
        return ArrayUtils.add(providers, new FileResourcePackProvider(
                ObscureGlobalDatapacks.DATAPACKS_PATH, ResourceType.SERVER_DATA, GlobalResourcePackSource.INSTANCE, symlinkFinder
        ));
    }

}
