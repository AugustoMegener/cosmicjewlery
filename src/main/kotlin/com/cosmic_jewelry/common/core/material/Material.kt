package com.cosmic_jewelry.common.core.material

import net.minecraft.resources.ResourceLocation

interface Material<T: Material<T>> {
    val typeName:   String
    val name:       String
    val registry:   HashMap<ResourceLocation, T>
}