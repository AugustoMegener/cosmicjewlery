package com.cosmic_jewelry.common.core.material

import net.minecraft.resources.ResourceLocation

interface Material<T: Material<T>> {
    val name: String
    val owner: String
    val registry: HashMap<ResourceLocation, T>
}