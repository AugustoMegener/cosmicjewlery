package com.cosmic_jewelry.common.capability

import net.minecraft.core.component.DataComponentType
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.SimpleFluidContent
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack
import java.util.function.Supplier

class SpecificFluidHandlerItemStack(componentType: Supplier<DataComponentType<SimpleFluidContent>>,
                                    container: ItemStack,
                                    capacity: Int,
                                    private val fluidType: FluidType)
    : FluidHandlerItemStack(componentType, container, capacity)
{
    override fun isFluidValid(tank: Int, stack: FluidStack) = stack.`is`(fluidType)
}