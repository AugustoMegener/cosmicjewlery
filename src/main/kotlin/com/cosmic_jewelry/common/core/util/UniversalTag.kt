package com.cosmic_jewelry.common.core.util

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

class UniversalTag(val location: ResourceLocation) {
    private val tags: HashMap<ResourceKey<out Registry<*>>, TagKey<*>> = hashMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T> by(registry: ResourceKey<out Registry<T>>) =
        tags.getOrPut(registry) { TagKey.create(registry, location) } as TagKey<T>

}