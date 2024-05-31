package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.RotatedPillarBlock
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class GemBlockStateProvider(id: String, output: PackOutput, fileHelper: ExistingFileHelper)
    : BlockStateProvider(output, id, fileHelper)
{
    override fun registerStatesAndModels() {
        cutGemBlock.allFeatures.forEach { (_, b) -> b.get().also { simpleBlockWithItem(it, cubeAll(it)) }  }
        tilesBlock.allFeatures.forEach { (_, b) -> b.get().also { simpleBlockWithItem(it, cubeAll(it)) }  }
        pillarBlock.allFeatures.forEach { (l, b) -> (b.get() as RotatedPillarBlock).also {
            logBlock(it)
            l.location.also { p -> simpleBlockItem(it,
                models().cubeColumn(p.namespace,
                                    p.withPrefix("block/"),
                                    p.withPrefix("block/").withSuffix("_top")))
            }
        } }
    }
}