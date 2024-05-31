package com.cosmic_jewelry.common.util

import net.minecraft.resources.ResourceLocation

object ModUtil {
    infix fun String.of(namespace: String) = ResourceLocation(namespace, this)
}