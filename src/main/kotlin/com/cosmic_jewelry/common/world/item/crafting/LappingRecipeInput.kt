package com.cosmic_jewelry.common.world.item.crafting


import com.cosmic_jewelry.common.util.get
import net.minecraft.world.item.crafting.RecipeInput
import net.neoforged.neoforge.items.IItemHandler

class LappingRecipeInput(val inventory: IItemHandler, val cutterSlot: Int = 0, val inputSlot: Int = 2) : RecipeInput {
    override fun getItem(index: Int) =
        if (index >= size()) throw IllegalArgumentException("No item for index ${size()}") else inventory[index]

    override fun size() = 2
}