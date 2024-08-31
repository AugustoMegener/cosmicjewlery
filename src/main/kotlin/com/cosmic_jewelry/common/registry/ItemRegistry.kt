package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.gem.GemItem
import com.cosmic_jewelry.common.core.material.feature.gem.GemItem.Companion.defaultProperty
import com.cosmic_jewelry.common.registry.BlockRegistry.lappingTableBlock
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ItemRegistry {
    val items: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val lappingTableBlockItem by items.register("lapping_table") { -> BlockItem(lappingTableBlock, Properties()) }

    val cutGemItem = GemItem("#_gem", { Item(defaultProperty(it)) }, doLapping = true)
    val rawGemItem = GemItem("uncut_#_gem", { Item(defaultProperty(it)) }, doLapping = true)
}