package com.cosmic_jewelry.common.core.material

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse as loc
import net.minecraft.tags.TagKey
import java.lang.reflect.GenericDeclaration
import java.lang.reflect.TypeVariable

interface Material<T: Material<T>> {
    val name: String
    val owner: String

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