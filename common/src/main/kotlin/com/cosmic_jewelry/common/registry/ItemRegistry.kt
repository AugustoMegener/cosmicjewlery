package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.GemFeature
import com.cosmic_jewelry.common.core.gem.GemType
import com.cosmic_jewelry.common.registry.BlockRegistry.gemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.gemPillar
import com.cosmic_jewelry.common.registry.BlockRegistry.gemTiles
import com.cosmic_jewelry.common.registry.GemTypeRegistry.gemTypes
import com.cosmic_jewelry.common.registry.TabRegistry.gemsTab
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item


object ItemRegistry {
    val items = DeferredRegister.create(ID, Registries.ITEM)

    val rawGemItem =  GemFeature("ore/") { Item(getGemItemProperties(it)) }
        .also { it.registerAll(items, gemTypes) }
    val cutGemItem =  GemFeature("gem/") { Item(getGemItemProperties(it)) }
        .also { it.registerAll(items, gemTypes) }

    val gemBlockItem  = GemFeature("block/")  { BlockItem(gemBlock[it]!!, getGemItemProperties(it)) as Item  }
        .also { it.registerAll(items, gemTypes) }
    val gemTilesItem  = GemFeature("tiles/")  { BlockItem(gemTiles[it]!!, getGemItemProperties(it)) as Item  }
        .also { it.registerAll(items, gemTypes) }
    val gemPillarItem = GemFeature("pillar/") { BlockItem(gemPillar[it]!!, getGemItemProperties(it)) as Item }
        .also { it.registerAll(items, gemTypes) }


    private fun getGemItemProperties(gemType: GemType): Item.Properties =
        Item.Properties().rarity(gemType.rarity).`arch$tab`(gemsTab)
}