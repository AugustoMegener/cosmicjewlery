package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*

abstract class MaterialRecipe<T : Material<T>>(        location: ResourceLocation,
                                                       gemSymbol:   String,
                                           private val builder:     (T, ResourceLocation, RecipeOutput) -> Unit) :
    MaterialFeatureBase<T, String, (RecipeOutput) -> Unit>(location, gemSymbol)
{
    override fun builder(context: String, material: T): () -> (RecipeOutput) -> Unit =
        { { builder(material, ResourceLocation(context, createName(material)), it) } }


    companion object {

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