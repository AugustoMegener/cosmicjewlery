package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.registry.BlockRegistry.cutGemBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.pillarBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.tilesBlock
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import com.cosmic_jewelry.common.util.ClassRegister
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipe
import com.cosmic_jewelry.common.world.item.crafting.LappingRecipeBuilder
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.*

open class GemRecipe(
    name: String,
    gemSymbol: String,
    private val builder: (GemType, ResourceLocation, RecipeOutput) -> Unit,
) : GemFeature<String, (RecipeOutput) -> Unit>(name, gemSymbol)
{

    constructor(name: String, builder: (GemType, ResourceLocation, RecipeOutput) -> Unit) :
            this(name, "#", builder)

    init { all += this }

    override fun builder(context: String, material: GemType): () -> (RecipeOutput) -> Unit =
        { { builder(material, ResourceLocation(context, createName(material)), it) } }


    companion object : ClassRegister<GemRecipe>() {
        val cutGemBlockRecipe = GemRecipe("#_cut_gems") { g, l, o ->
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

        val gemPillarBlockRecipe = GemRecipe("#_gem_pillar") { g, l, o ->
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(cutGemBlock.item[g]), RecipeCategory.BUILDING_BLOCKS, pillarBlock.item[g]!!, 16
            ).unlockedBy("unlocks", invCriteria(cutGemBlock.item[g]!!)).save(o, l)
        }

        val gemTilesBlockRecipe = GemRecipe("#_gem_tiles") { g, l, o ->
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(cutGemBlock.item[g]), RecipeCategory.BUILDING_BLOCKS, tilesBlock.item[g]!!, 16
            ).unlockedBy("unlocks", invCriteria(cutGemBlock.item[g]!!)).save(o, l)
        }

        val gemLappingRecipe = GemRecipe("#_lapping") { g, l, o, ->
            LappingRecipeBuilder(LappingRecipe(Ingredient.of(rawGemItem[g]!!), cutGemItem[g]!!.defaultInstance))
                .save(o, l)
        }


        val Item.predicate get() = ItemPredicate.Builder.item().of(this).build()
        val ItemStack.predicate get() = item.predicate
        val TagKey<Item>.predicate get() = ItemPredicate.Builder.item().of(this).build()

        fun invCriteria(vararg itemPredicate: ItemPredicate) = CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
            InventoryChangeTrigger.TriggerInstance(
                Optional.empty(),
                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                itemPredicate.asList()))

        fun invCriteria(vararg items: Item) = invCriteria(*(items.map { it.predicate }).toTypedArray())
    }
}