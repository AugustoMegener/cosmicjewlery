package com.cosmic_jewelry.common.core.material

import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity
import kotlin.reflect.KClass

abstract class Material<T: Material<T>>(val id: ResourceLocation) {

    init { materials[id] = this::class }

    open val tags = listOf<UniversalTag>()

    abstract val location: ResourceLocation

    val  name : String get() = location.path
    val owner : String get() = location.namespace

    abstract val rarity: Rarity

    abstract val mohs: Float

    abstract val registry: HashMap<ResourceLocation, T>

    companion object {
        private val materials = HashMap<ResourceLocation, KClass<out Material<*>>>()

        fun byId(id: ResourceLocation) = materials[id]

    }
}