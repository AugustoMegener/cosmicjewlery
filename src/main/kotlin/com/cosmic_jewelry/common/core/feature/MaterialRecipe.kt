package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation

abstract class MaterialRecipe<M: Material<M>>(name: String, materialSymbol: String = "#")
    : MaterialFeatureBase<M, String, (RecipeOutput) -> Unit>(name, materialSymbol) {

    abstract val builder: (M, ResourceLocation, RecipeOutput) -> Unit

    override fun builder(context: String, material: M): () -> (RecipeOutput) -> Unit =
        { { builder(material, ResourceLocation.parse(("$context:${createName(material)}")), it) } }
}