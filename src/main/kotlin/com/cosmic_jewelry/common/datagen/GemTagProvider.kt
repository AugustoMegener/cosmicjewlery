package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.material.feature.gem.GemOre
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
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
        GemOre.register.flatMap { it.content.entries }.map { it.key to it.value }.forEach { (g, b) ->
            with(BuiltInRegistries.BLOCK) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add()
                tag(g.family.miningLevel.tag).add(getResourceKey(b).get())
            }
        }
    }
}