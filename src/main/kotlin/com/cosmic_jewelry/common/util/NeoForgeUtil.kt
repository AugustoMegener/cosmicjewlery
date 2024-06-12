package com.cosmic_jewelry.common.util

import net.minecraft.world.SimpleContainer
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler

object NeoForgeUtil {
    operator fun IItemHandler.get(i: Int): ItemStack = getStackInSlot(i)

    val IItemHandler.container
        get() =
            SimpleContainer(*(0..<slots)
                .map {
                    this[it]
                }
                .toTypedArray())
}