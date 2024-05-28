package com.cosmic_jewelry

import com.cosmic_jewelry.registry.BlockRegistry
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CosmicJewelry.ID)
object CosmicJewelry {
    const val ID = "cosmic_jewelry"

    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        BlockRegistry.blocks.register(MOD_BUS)
    }
}