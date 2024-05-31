package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.GemFeature.GemItem
import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistry {
    val items: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val cutGemItem = GemItem("gem/") { g: GemType -> Item(Item.Properties().rarity(g.rarity)) }
    val rawGemItem = GemItem("ore/") { g: GemType -> Item(Item.Properties().rarity(g.rarity)) }
}