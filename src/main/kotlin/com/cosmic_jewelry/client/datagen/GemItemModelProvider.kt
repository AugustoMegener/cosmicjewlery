package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.core.material.feature.gem.GemItem
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class GemItemModelProvider(id: String, output: PackOutput, fileHelper: ExistingFileHelper)
    : ItemModelProvider(output, id, fileHelper)
{
    override fun registerModels() {
        //listOf(cutGemItem).flatMap { it.allFeatures }.forEach { basicItem(it.get()) }

        GemItem.register.forEach { b -> b.content.forEach { g -> b.generateData(this, g.key) } }
    }
}