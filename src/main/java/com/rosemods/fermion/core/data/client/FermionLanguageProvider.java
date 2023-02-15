package com.rosemods.fermion.core.data.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rosemods.fermion.core.Fermion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

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
