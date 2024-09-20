package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.core.feature.MaterialItem
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class MaterialItemModelProvider(id: String, output: PackOutput, fileHelper: ExistingFileHelper)
    : ItemModelProvider(output, id, fileHelper)
{
    override fun registerModels() {
        //listOf(cutGemItem).flatMap { it.allFeatures }.forEach { basicItem(it.get()) }


        //GemItem.register.forEach { b -> b.content.forEach { g -> b.generateData(this, g.key) } }

        // TODO: Check if it's really working
        MaterialItem.register.forEach { b -> b.content.forEach { g -> b.generateData(this, g.key) } }
    }
}