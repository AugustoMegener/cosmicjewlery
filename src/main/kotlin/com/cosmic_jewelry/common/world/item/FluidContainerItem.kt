package com.cosmic_jewelry.common.world.item

import com.cosmic_jewelry.common.registry.DataComponentTypeRegistry.fluidData
import com.cosmic_jewelry.common.util.fluid
import com.cosmic_jewelry.common.util.get
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.fluids.SimpleFluidContent

open class FluidContainerItem(properties: Properties, val capacity: Int, private val barColor: Int? = null) : Item(properties) {


    override fun isBarVisible(stack: ItemStack) = true
    override fun  getBarColor(stack: ItemStack) = barColor ?: 0xffffff

    override fun getBarWidth(stack: ItemStack) =
        stack.fluid?.run { get(0).amount * 13 / getTankCapacity(0) } ?: 0


    override fun hasCraftingRemainingItem(stack: ItemStack) =
        stack.get(fluidData) != null

    override fun getCraftingRemainingItem(itemStack: ItemStack): ItemStack =
        itemStack.copy().also { it.set<SimpleFluidContent>(fluidData, null) }
}