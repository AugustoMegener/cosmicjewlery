package com.cosmic_jewelry.common.util

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionHand.MAIN_HAND
import net.minecraft.world.InteractionHand.OFF_HAND
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler.ITEM
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.capability.IFluidHandler
import net.neoforged.neoforge.items.IItemHandler

val InteractionHand.opposite get() = when (this) { MAIN_HAND -> OFF_HAND; OFF_HAND -> MAIN_HAND }

val ItemStack.fluid get() = getCapability(ITEM)

operator fun IItemHandler.get(i: Int): ItemStack = getStackInSlot(i)

operator fun IFluidHandler.get(i: Int): FluidStack = getFluidInTank(i)

val IFluidHandler.totalCapacity get() = (0..tanks).sumOf { getTankCapacity(it) }