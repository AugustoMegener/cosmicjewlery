package com.cosmic_jewelry.common.core.material

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Rarity
import net.minecraft.resources.ResourceLocation.parse as loc

interface Material<T: Material<T>> {

    val location: ResourceLocation

    val  name : String get() = location.path
    val owner : String get() = location.namespace

    val rarity: Rarity

    val mohs: Float


    val registry: HashMap<ResourceLocation, T>

    companion object {
        val tags = HashMap<Material<*>, HashMap<Registry<*>, TagKey<*>>>()

        // generic typing insanity moments
        @Suppress("UNCHECKED_CAST")
        fun <T, M: Material<M>> Material<M>.getTag(registry: Registry<T>): TagKey<T> =
            tags.computeIfAbsent(this) { HashMap() }
                .computeIfAbsent(registry) { TagKey.create(registry.key(), loc("$owner:${name}_material")) }
                    as TagKey<T>
    }
}