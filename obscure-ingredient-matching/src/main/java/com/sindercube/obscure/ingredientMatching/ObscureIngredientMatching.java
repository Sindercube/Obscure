package com.sindercube.obscure.ingredientMatching;

import com.sindercube.obscure.ingredientMatching.core.ItemConditionRegistry;
import net.fabricmc.api.ModInitializer;

public class ObscureIngredientMatching implements ModInitializer {

    @Override
    public void onInitialize() {
		ItemConditionRegistry.init();
    }

}
