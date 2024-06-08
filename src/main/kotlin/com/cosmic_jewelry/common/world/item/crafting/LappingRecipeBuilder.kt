package com.cosmic_jewelry.common.world.item.crafting

import net.minecraft.advancements.Criterion
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

class LappingRecipeBuilder(val recipe: LappingRecipe) : RecipeBuilder {
    override fun unlockedBy(pName: String, pCriterion: Criterion<*>) = this

    override fun group(pGroupName: String?) = this

    override fun getResult(): Item = recipe.result.item

    override fun save(pRecipeOutput: RecipeOutput, pId: ResourceLocation) {
        pRecipeOutput.accept(pId, recipe, null)
    }
}