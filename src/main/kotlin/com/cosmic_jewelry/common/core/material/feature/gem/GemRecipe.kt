package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.MaterialRecipe
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import com.cosmic_jewelry.common.util.ClassRegister
import com.cosmic_jewelry.common.util.MinecraftUtil.has
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipe
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipeBuilder
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient

class GemRecipe(location: ResourceLocation,
                gemSymbol: String,
                builder:   (GemType, ResourceLocation, RecipeOutput) -> Unit) :
    MaterialRecipe<GemType>(location, gemSymbol, builder)
{

    constructor(location: ResourceLocation, builder: (GemType, ResourceLocation, RecipeOutput) -> Unit) :
            this(location, "#", builder)

    init { all += this }


    companion object : ClassRegister<GemRecipe>() {
        val cutGemBlockRecipe = GemRecipe(ID has "#_cut_gems") { g, l, o ->
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, cutGemBlock.item[g]!!)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', cutGemItem[g]!!)
                .unlockedBy("unlocks", invCriteria(cutGemItem[g]!!))
                .save(o, l.withSuffix("_packing"))

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cutGemItem[g]!!, 9)
                .requires(cutGemBlock.item[g]!!)
                .unlockedBy("unlocks", invCriteria(cutGemBlock.item[g]!!))
                .save(o, l.withSuffix("_unpacking"))
        }

        val gemPillarBlockRecipe = GemRecipe(ID has "#_gem_pillar") { g, l, o ->
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(cutGemBlock.item[g]), RecipeCategory.BUILDING_BLOCKS, pillarBlock.item[g]!!, 16
            ).unlockedBy("unlocks", invCriteria(cutGemBlock.item[g]!!)).save(o, l)
        }

        val gemTilesBlockRecipe = GemRecipe(ID has "#_gem_tiles") { g, l, o ->
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(cutGemBlock.item[g]), RecipeCategory.BUILDING_BLOCKS, tilesBlock.item[g]!!, 16
            ).unlockedBy("unlocks", invCriteria(cutGemBlock.item[g]!!)).save(o, l)
        }

        val gemLappingRecipe = GemRecipe(ID has "#_lapping") { g, l, o, ->
            LappingRecipeBuilder(LappingRecipe(Ingredient.of(rawGemItem[g]!!), cutGemItem[g]!!.defaultInstance))
                .save(o, l)
        }
    }
}