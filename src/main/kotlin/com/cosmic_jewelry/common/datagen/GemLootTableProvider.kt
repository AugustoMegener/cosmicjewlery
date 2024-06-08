package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.gem.feature.GemOre
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.concurrent.CompletableFuture

class GemLootTableProvider(pOutput: PackOutput,
                           pRegistries: CompletableFuture<HolderLookup.Provider>)
    : LootTableProvider(pOutput,
                        setOf(),
                        mutableListOf(SubProviderEntry({ gemOreGenProvider }, LootContextParamSets.BLOCK)),
                        pRegistries                                                                         )
{
    companion object {
        val gemOreGenProvider = object : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags())
        {
            override fun generate() {
                GemOre.register.flatMap { it.featuresMap.entries } .forEach { (g, b) ->
                    b.get().also { add(it, createOreDrop(it, rawGemItem[g]!!)) }
                }
            }

            override fun getKnownBlocks() = GemOre.register.flatMap { it.allFeatures } .map { it.get() }.toMutableList()
        }
    }
}