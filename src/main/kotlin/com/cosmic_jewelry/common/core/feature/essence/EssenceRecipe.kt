package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialRecipe
import com.cosmic_jewelry.common.core.feature.gem.GemRecipe.Companion.invCriteria
import com.cosmic_jewelry.common.core.material.essence.Essence
import com.cosmic_jewelry.common.registry.DataComponentTypeRegistry.fluidData
import com.cosmic_jewelry.common.registry.FluidTypeRegistry.essenceFluidType
import com.cosmic_jewelry.common.registry.ItemRegistry.essenceJarItem
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items.BUCKET
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.crafting.DataComponentIngredient
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.SimpleFluidContent

class EssenceRecipe(                     name : String,
                                essenceSymbol : String = "#",
                    override val      builder : (Essence, ResourceLocation, RecipeOutput) -> Unit)
    : MaterialRecipe<Essence>(name, essenceSymbol)
{
    companion object {
        val fillEssenceContainersRecipe = EssenceRecipe("fill_#_essence") { e, l, o ->
            val    jar = essenceJarItem[e]!!
            val bucket = essenceFluidType.bucket[e]!!

            val fullJar = jar.defaultInstance.also {
                it.set(fluidData, SimpleFluidContent.copyOf((FluidStack(essenceFluidType.sourceFluid[e]!!, 250))))
                it.grow(3) }

            val emptyJar = jar.defaultInstance

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, fullJar)
                .pattern(" * ")
                .pattern("*#*")
                .pattern(" * ")

                .define('*', DataComponentIngredient.of(true, emptyJar))
                .define('#', bucket)
                .unlockedBy("unlocks", invCriteria(bucket))
                .save(o, l.withSuffix("_jar"))

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, bucket)
                .pattern(" * ")
                .pattern("*#*")
                .pattern(" * ")

                .define('*', Ingredient.of(fullJar))
                .define('#', BUCKET)
                .unlockedBy("unlocks", invCriteria(jar))
                .save(o, l.withSuffix("_bucket"))
        }
    }
}