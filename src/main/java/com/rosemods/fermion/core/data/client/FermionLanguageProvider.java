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
        this.add("tooltip.fermion.brewing_ingredient", "Brewing Ingredient");
        this.add("tooltip.fermion.hidden_item", "Hidden Item");
        this.add("tooltip.fermion.horse_armour", "Horse Armor");
        this.add("tooltip.fermion.when_on_horse", "When on Horse:");
        this.add("tooltip.fermion.mining_power", " %s Mining Power");
        this.add("tooltip.fermion.mining_speed", " %s Mining Speed");
    }

}
