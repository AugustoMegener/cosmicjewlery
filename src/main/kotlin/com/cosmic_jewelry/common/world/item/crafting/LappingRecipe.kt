package com.cosmic_jewelry.common.world.item.crafting

import com.cosmic_jewelry.common.core.material.feature.gem.GemItem.Companion.cutterMohs
import com.cosmic_jewelry.common.registry.RecipeRegistry.lappingRecipeSerializer
import com.cosmic_jewelry.common.registry.RecipeRegistry.lappingRecipeType
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

class LappingRecipe(val input: Ingredient, val result: ItemStack) : Recipe<Container> {

    override fun matches(pContainer: Container, pLevel: Level): Boolean {
        val inputStack = pContainer.getItem(1)
        val inputStackMohs = inputStack.cutterMohs
        val cutterMohs = pContainer.getItem(0).cutterMohs

        return input.test(inputStack) && cutterMohs != null && inputStackMohs != null && cutterMohs >= inputStackMohs
    }


    override fun assemble(pCraftingContainer: Container, pRegistries: HolderLookup.Provider?): ItemStack =
        result.copy().also { pCraftingContainer.removeItem(1, 1) }

    override fun canCraftInDimensions(pWidth: Int, pHeight: Int) = true

    override fun getResultItem(pRegistries: HolderLookup.Provider?) = result

    override fun getSerializer(): RecipeSerializer<LappingRecipe> = lappingRecipeSerializer
    override fun getType(): RecipeType<LappingRecipe> = lappingRecipeType

    companion object : RecipeSerializer<LappingRecipe> {

        val mapCodec: MapCodec<LappingRecipe> = RecordCodecBuilder.mapCodec {
            it.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(LappingRecipe::input),
                ItemStack.CODEC.fieldOf("output").forGetter(LappingRecipe::result)
            ).apply(it) { i, o -> LappingRecipe(i, o) }
        }

        val streamCodec: StreamCodec<RegistryFriendlyByteBuf, LappingRecipe> = StreamCodec.of(
            { b, r -> Ingredient.CONTENTS_STREAM_CODEC.encode(b, r.input)
                      ItemStack.STREAM_CODEC.encode(b, r.result)          },
            { b -> LappingRecipe(Ingredient.CONTENTS_STREAM_CODEC.decode(b), ItemStack.STREAM_CODEC.decode(b)) }
        )

        override fun codec() = mapCodec

        override fun streamCodec() = streamCodec
    }
}