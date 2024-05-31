package com.cosmic_jewelry.common.core.gem

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity

class GemType(val mosh: Float, val rarity: Rarity = Rarity.COMMON) {

    val location get() = gemTypes.entries.find { it.value == this }!!.key

    companion object {
        val gemTypes = HashMap<ResourceLocation, GemType>()

        val peridotGem = GemType(6.5f)
    }
}