package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.feature.essence.EssenceItem
import com.cosmic_jewelry.common.core.feature.gem.GemItem
import com.cosmic_jewelry.common.core.feature.gem.GemItem.Companion.defaultProperty
import com.cosmic_jewelry.common.registry.BlockRegistry.lappingTableBlock
import net.minecraft.core.registries.Registries.ITEM
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import net.minecraft.resources.ResourceLocation.parse as loc

object ItemRegistry {
    val items: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val lappingTableBlockItem: BlockItem by
        items.register("lapping_table") { -> BlockItem(lappingTableBlock, Properties()) }

    val cutGemTag: TagKey<Item> = TagKey.create(ITEM, loc("c:material_gem"))
    val rawGemTag: TagKey<Item> = TagKey.create(ITEM, loc("c:uncut_material_gem"))

    val cutGemItem = GemItem("#_gem", { Item(defaultProperty(it)) }, doLapping = true)
    val rawGemItem = GemItem("uncut_#_gem", { Item(defaultProperty(it)) }, doLapping = true)

    val essenceJarItem = EssenceItem("#_jar", { Item(Properties().stacksTo(1).rarity(it.rarity)) })
}