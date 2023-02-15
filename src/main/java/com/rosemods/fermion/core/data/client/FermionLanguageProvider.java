package com.rosemods.fermion.core.data.client;

import com.rosemods.fermion.core.Fermion;

import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class FermionLanguageProvider extends LanguageProvider {

    public FermionLanguageProvider(GatherDataEvent event) {
        super(event.getGenerator(), Fermion.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("tooltip.fermion.dyeable", "Dyeable");
        this.add("tooltip.fermion.horse_armour", "+%d Horse Armor");
        this.add("tooltip.fermion.modifiers.horse", "When on Horse:");
    }

}
