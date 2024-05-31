package com.cosmic_jewelry

import com.cosmic_jewelry.client.event.ModClientEvent
import com.cosmic_jewelry.common.core.gem.GemType.Companion.peridotGem
import com.cosmic_jewelry.common.registry.BlockRegistry.blocks
import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import com.cosmic_jewelry.common.registry.GemTypeRegister
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.items
import com.cosmic_jewelry.common.registry.TabRegistry.tabs
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CosmicJewelry.ID)
object CosmicJewelry {
    const val ID = "cosmic_jewelry"

    val logger: Logger = LogManager.getLogger(ID)

    init {
        MOD_BUS.addListener(ModClientEvent::onGatherData)
        registerGem()
        listOf(blocks, items, tabs).forEach { it.register(MOD_BUS) }
    }

    private fun registerGem() {
        with (GemTypeRegister) {
            register(ID,
                "peridot" to peridotGem
            ) {
                feature(items, cutGemItem, cutGemBlock.item, tilesBlock.item, pillarBlock.item,)
                feature(blocks, cutGemBlock, tilesBlock, pillarBlock)
            }
        }
    }
}