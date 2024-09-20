package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.core.feature.MaterialBlock
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class MaterialBlockStateProvider(id: String, output: PackOutput, fileHelper: ExistingFileHelper)
    : BlockStateProvider(output, id, fileHelper)
{
    override fun registerStatesAndModels() {
        //GemBlock.register.forEach { b -> b.content.forEach { g -> b.generateData(this, g.key) } }

        MaterialBlock.register.forEach { b -> b.content.forEach { g -> b.generateData(this, g.key) } }

        /*cutGemBlock.allFeatures.forEach { it.get().also { b -> simpleBlockWithItem(b, cubeAll(b)) } }
        tilesBlock .allFeatures.forEach { it.get().also { b -> simpleBlockWithItem(b, cubeAll(b)) } }
        pillarBlock.allFeatures.forEach { (it.get() as RotatedPillarBlock).also { b ->
            logBlock(b)
            simpleBlockItem(b, models().withExistingParent(it.id.toString(), "minecraft:block/cube_column"))
        } }*/
    }
}