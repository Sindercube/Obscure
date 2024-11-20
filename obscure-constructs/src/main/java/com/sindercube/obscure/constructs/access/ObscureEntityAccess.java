package com.sindercube.obscure.constructs.access;

import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ObscureEntityAccess {

	default void onConstructed(BlockPattern.Result result, World world, BlockPos headPos) {}

}
