package com.cosmic_jewelry.common.registry

// DELEGATES
import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.GemType.Companion.peridotGem
import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object TabRegistry {
    val tabs: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ID)

    val gemsTab by tabs.register("gem_tab") { -> CreativeModeTab.builder()
        .title(Component.translatable("category.$ID.gem"))
        .icon { cutGemItem[peridotGem]!!.defaultInstance }
        .displayItems { _, o ->
            listOf(rawGemItem, cutGemItem, cutGemBlock.item, tilesBlock.item, pillarBlock.item)
                .flatMap { it.allFeatures.values }
                .map { it.get() }
                .forEach(o::accept)
        }
        .build()
    }
}