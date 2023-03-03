package com.rosemods.fermion.core;

import com.rosemods.fermion.core.data.client.FermionLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Fermion.MODID)
public class Fermion {
    public static final String MODID = "fermion";

    public Fermion() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        final ModLoadingContext context = ModLoadingContext.get();

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, FermionConfig.COMMON_SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, FermionConfig.CLIENT_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Fermion::removeItems);
    }

    private void clientSetup(FMLClientSetupEvent event) {

    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        boolean client = event.includeClient();

        gen.addProvider(client, new FermionLanguageProvider(event));
    }

    private static void removeItems() {
        for(String s : FermionConfig.COMMON.hiddenItems.get()) {
            Item i = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(s));

            if (i != null && i != Items.AIR)
                ObfuscationReflectionHelper.setPrivateValue(Item.class, i, null, "f_41377_");
        }
    }
}