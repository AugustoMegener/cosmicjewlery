package com.cosmic_jewelry.fabric;

import com.cosmic_jewelry.CosmicJewelry
import net.fabricmc.api.ModInitializer

class CosmicJewelryFabric : ModInitializer {
    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run the Fabric-like setup.
        CosmicJewelry.init();
    }
}
