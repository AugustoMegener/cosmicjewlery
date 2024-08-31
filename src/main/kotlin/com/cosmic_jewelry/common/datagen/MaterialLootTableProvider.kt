package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.material.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.feature.MaterialOre
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.BlockLootSubProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.concurrent.CompletableFuture

class MaterialLootTableProvider(pOutput: PackOutput,
                                pRegistries: CompletableFuture<HolderLookup.Provider>)
    : LootTableProvider(pOutput,
                        setOf(),
                        mutableListOf(SubProviderEntry({ gemOreGenProvider(it) }, LootContextParamSets.BLOCK)),
                        pRegistries )
{
    companion object {
        private val gemOreGenProvider = { lookup: HolderLookup.Provider ->
            object : BlockLootSubProvider(setOf(), FeatureFlags.REGISTRY.allFlags(), lookup) {
                override fun generate() {
                    MaterialBlock.register.forEach { f ->
                        f.content.filter{ (g, _) -> f.getDropItem(g) != null }.forEach { (g, b) ->
                            b.also { add(it, createOreDrop(it, f.getDropItem(g)!!)) }
                        }
                    }
                }

                override fun getKnownBlocks() = MaterialOre.register.flatMap { it.features }.map { it }.toMutableList()
            }
        }
    }


}