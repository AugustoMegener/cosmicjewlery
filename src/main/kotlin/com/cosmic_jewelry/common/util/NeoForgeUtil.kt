package com.cosmic_jewelry.common.util

import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler

object NeoForgeUtil {
    operator fun IItemHandler.get(i: Int): ItemStack = getStackInSlot(i)
}