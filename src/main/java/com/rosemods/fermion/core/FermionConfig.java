package com.rosemods.fermion.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = Fermion.MODID)
public class FermionConfig {
    public static final Common COMMON;
    public static final Client CLIENT;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static class Common {
        @ConfigKey("smooth_basalt")
        public final ConfigValue<Boolean> smoothBasalt;
        @ConfigKey("end_stone")
        public final ConfigValue<Boolean> endStone;
        @ConfigKey("smooth_stone")
        public final ConfigValue<Boolean> smoothStone;
        @ConfigKey("brick_walls")
        public final ConfigValue<Boolean> brickWalls;
        @ConfigKey("waxed_concrete_powder")
        public final ConfigValue<Boolean> waxedConcretePowder;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.push("Building Blocks");
            builder.push("Block Set Extensions");

            this.smoothBasalt = builder.comment("Should Smooth Basalt have a full Block Set.").define("Smooth Basalt Block Set", true);
            this.endStone = builder.comment("Should End Stone have a full Block Set.").define("End Stone Block Set", true);
            this.smoothStone = builder.comment("Should Smooth Stone have a Stair variant.").define("Smooth Stone Block Set", true);
            this.brickWalls = builder.comment("Should Brick Blocks with no Wall Block be given one.").define("Brick Walls", true);

            builder.pop();

            this.waxedConcretePowder  =builder.comment("Should you be able to wax Concrete Powder to stop its gravity and ability to convert in water.").define("Waxed Concrete Powder", true);

            builder.pop();
        }

    }

    public static class Client {
        public Client(ForgeConfigSpec.Builder builder) {

        }

    }

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);

        COMMON = commonSpecPair.getLeft();
        CLIENT = clientSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

}
