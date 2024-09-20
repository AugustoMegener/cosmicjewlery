package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipe
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object RecipeRegistry {
    val recipeTypes = DeferredRegister.create(Registries.RECIPE_TYPE, ID)
    val recipeSerializers = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ID)

    val lappingRecipeType by recipeTypes.register("lapping") { ->
        RecipeType.register<LappingRecipe>("lapping")
    }

    val lappingRecipeSerializer by recipeSerializers.register("lapping_serializer") { ->
        RecipeSerializer.register("lapping", LappingRecipe)
    }
}