package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.core.gem.feature.RegistryGemBlock
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class GemBlockStateProvider(id: String, output: PackOutput, fileHelper: ExistingFileHelper)
    : BlockStateProvider(output, id, fileHelper)
{
    override fun registerStatesAndModels() {
        RegistryGemBlock.register.forEach { b -> b.allGemTypes.forEach { g -> b.generateData(this, g) } }


        /*cutGemBlock.allFeatures.forEach { it.get().also { b -> simpleBlockWithItem(b, cubeAll(b)) } }
        tilesBlock .allFeatures.forEach { it.get().also { b -> simpleBlockWithItem(b, cubeAll(b)) } }
        pillarBlock.allFeatures.forEach { (it.get() as RotatedPillarBlock).also { b ->
            logBlock(b)
            simpleBlockItem(b, models().withExistingParent(it.id.toString(), "minecraft:block/cube_column"))
        } }*/
    }
}