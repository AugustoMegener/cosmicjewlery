package com.cosmic_jewelry.common.datagen

import com.cosmic_jewelry.common.core.feature.MaterialRecipe
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import java.util.concurrent.CompletableFuture

class GemRecipeProvider(pOutput: PackOutput,
                        pRegistries: CompletableFuture<HolderLookup.Provider>) : RecipeProvider(pOutput, pRegistries)
{
    override fun buildRecipes(pRecipeOutput: RecipeOutput) {
        MaterialRecipe.register.flatMap { it.features } .forEach { it(pRecipeOutput) }
    }
}