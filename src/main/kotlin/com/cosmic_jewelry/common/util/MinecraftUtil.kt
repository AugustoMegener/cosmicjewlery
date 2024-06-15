package com.cosmic_jewelry.common.util

import net.minecraft.resources.ResourceLocation

object MinecraftUtil {
    infix fun String.has(path: String) = ResourceLocation(this, path)
}