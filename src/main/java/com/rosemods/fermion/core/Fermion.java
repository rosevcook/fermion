package com.rosemods.fermion.core;

import com.rosemods.fermion.core.data.client.FermionLanguageProvider;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Fermion.MODID)
public class Fermion {
    public static final String MODID = "fermion";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public Fermion() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        final ModLoadingContext context = ModLoadingContext.get();

        REGISTRY_HELPER.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        MinecraftForge.EVENT_BUS.register(this);

        //context.registerConfig(ModConfig.Type.COMMON, FermionConfig.COMMON_SPEC);
        //context.registerConfig(ModConfig.Type.CLIENT, FermionConfig.CLIENT_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void clientSetup(FMLClientSetupEvent event) {

    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        gen.addProvider(client, new FermionLanguageProvider(event));
    }
}
