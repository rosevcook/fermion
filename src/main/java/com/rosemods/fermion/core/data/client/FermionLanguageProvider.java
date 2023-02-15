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

public class FermionLanguageProvider implements DataProvider {
    private final Map<String, String> data = new TreeMap<>();
    private final DataGenerator gen;
    private final String locale;


    public FermionLanguageProvider(GatherDataEvent event) {
        this.gen = event.getGenerator();
        this.locale = "en_us";
    }

    protected void addTranslations() {
        Fermion.REGISTRY_HELPER.getItemSubHelper().getDeferredRegister().getEntries().forEach(this::tryItem);
        Fermion.REGISTRY_HELPER.getBlockSubHelper().getDeferredRegister().getEntries().forEach(this::tryBlock);

        this.add("tooltip.fermion.dyeable", "Dyeable");
        this.add("tooltip.fermion.horse_armour", "Horse Armor"); // $a$ replaces with the armour value
        this.add("tooltip.fermion.modifiers.horse", "When on Horse:");
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        addTranslations();
        if (!data.isEmpty())
            save(cache, data, this.gen.getOutputFolder().resolve("assets/" + Fermion.MODID + "/lang/" + locale + ".json"));
    }

    @Override
    public String getName() {
        return "Languages: " + locale;
    }

    private void save(CachedOutput cache, Object object, Path target) throws IOException {
        // TODO: DataProvider.saveStable handles the caching and hashing already, but creating the JSON Object this way seems unreliable. -C
        JsonObject json = new JsonObject();
        for (Map.Entry<String, String> pair : data.entrySet()) {
            json.addProperty(pair.getKey(), pair.getValue());
        }

        DataProvider.saveStable(cache, json, target);
    }

    public void addBlock(Supplier<? extends Block> key, String name) {
        add(key.get(), name);
    }

    public void add(Block key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addItem(Supplier<? extends Item> key, String name) {
        add(key.get(), name);
    }

    public void add(Item key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addPotion(Supplier<? extends Potion> potion, String name) {
        add("item.minecraft.potion.effect." + ForgeRegistries.POTIONS.getKey(potion.get()).getPath(), "Potion of " + name);
        add("item.minecraft.splash_potion.effect." + ForgeRegistries.POTIONS.getKey(potion.get()).getPath(), "Splash Potion of " + name);
        add("item.minecraft.lingering_potion.effect." + ForgeRegistries.POTIONS.getKey(potion.get()).getPath(), "Lingering Potion of " + name);
        add("item.minecraft.tipped_arrow.effect." + ForgeRegistries.POTIONS.getKey(potion.get()).getPath(), "Arrow of " + name);
    }

    public void addDisc(Supplier<? extends RecordItem> disc, String desc) {
        addItem(disc, "Music Disc");
        add(disc.get().getDescriptionId() + ".desc", desc);
    }

    public void addDisc(Supplier<? extends RecordItem> disc, String artist, String song) {
        addDisc(disc, artist + " - " + song);
    }

    public void addAdvTitle(String advancementTitle, String name) {
        add("advancements." + advancementTitle + ".title", name);
    }

    public void addAdvDesc(String advancementTitle, String name) {
        add("advancements." + advancementTitle + ".description", name);
    }

    public void addSubtitle(String category, String subtitleName, String name) {
        add("subtitles." + category + "." + subtitleName, name);
    }

    public void addDeath(String deathName, String name) {
        add("death.attack." + deathName, name);
    }

    public void addItemStack(Supplier<ItemStack> key, String name) {
        add(key.get(), name);
    }

    public void add(ItemStack key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEnchantment(Supplier<? extends Enchantment> key, String name) {
        add(key.get(), name);
    }

    public void add(Enchantment key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        add(key.get(), name);
    }

    public void add(MobEffect key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        add(key.get(), name);
    }

    public void add(EntityType<?> key, String name) {
        add(key.getDescriptionId(), name);
    }

    public void add(String key, String value) {
        if (data.put(key, value) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }

    public void tryBlock(Supplier<? extends Block> block) {
        String key = block.get().getDescriptionId();
        String value = formatString(ForgeRegistries.BLOCKS.getKey(block.get()).getPath());
        data.putIfAbsent(key, value);
    }

    public void tryItem(Supplier<? extends Item> item) {
        String key = item.get().getDescriptionId();
        String value = formatString(ForgeRegistries.ITEMS.getKey(item.get()).getPath());
        data.putIfAbsent(key, value);
    }

    public void tryEntity(Supplier<? extends EntityType<?>> entity) {
        String key = entity.get().getDescriptionId();
        String value = formatString(ForgeRegistries.ENTITY_TYPES.getKey(entity.get()).getPath());
        data.putIfAbsent(key, value);
    }

    private String formatString(String key) {
        String[] strArr = key.split("_");
        StringBuffer res = new StringBuffer();
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }
}
