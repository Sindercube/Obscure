package com.sindercube.obscure.globalDatapacks;

import net.minecraft.resource.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GlobalResourcePackSource implements ResourcePackSource {

    public static final GlobalResourcePackSource INSTANCE = new GlobalResourcePackSource();

    private GlobalResourcePackSource() {}

    @Override
    public boolean canBeEnabledLater() {
        return true;
    }

    @Override
    public Text decorate(Text packName) {
        return Text.translatable("pack.nameAndSource", packName, Text.translatable("pack.source.global")).formatted(Formatting.GRAY);
    }

}
