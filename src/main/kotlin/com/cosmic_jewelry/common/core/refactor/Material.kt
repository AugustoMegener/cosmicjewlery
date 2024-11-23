package com.cosmic_jewelry.common.core.refactor

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity

abstract class Material(val location: ResourceLocation) {

    abstract val rarity: Rarity
}