package com.cosmic_jewelry.common.registry

// DELEGATES

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.peridotGem
import com.cosmic_jewelry.common.core.feature.gem.GemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.lappingTableBlockItem
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
            GemItem.register.flatMap { it.features } .map { it } .forEach(o::accept)
            o.accept(lappingTableBlockItem)
        }
        .build()
    }
}