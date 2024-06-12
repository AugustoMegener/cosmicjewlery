package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.gem.feature.RegistryGemOre
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.TagsProvider
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class GemTagProvider(id: String,
                     packOutput: PackOutput,
                     registries: CompletableFuture<HolderLookup.Provider>,
                     existingFileHelper: ExistingFileHelper                 )
    : TagsProvider<Block>(packOutput, Registries.BLOCK, registries, id, existingFileHelper)
{
    override fun addTags(pProvider: HolderLookup.Provider) {
        RegistryGemOre.register.flatMap { it.featuresMap.entries }.map { it.key to it.value.key }.forEach { (g, b) ->
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(b)
            tag(g.family.miningLevel.tag).add(b)
        }
    }
}