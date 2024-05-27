package com.cosmic_jewelry.forge;

import com.cosmic_jewelry.CosmicJewelry
import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(CosmicJewelry.ID)
class CosmicJewelryForge {
    init {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(CosmicJewelry.ID, MOD_BUS);

        // Run our common setup.
        CosmicJewelry.init();
    }
}
