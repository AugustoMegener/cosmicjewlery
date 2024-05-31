package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.common.core.gem.GemFeature
import com.cosmic_jewelry.common.core.gem.GemType
import com.cosmic_jewelry.common.core.gem.GemType.Companion.gemTypes
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredRegister

object GemTypeRegister {
    class GemFeatureRegister internal constructor(private val gemType: GemType, private val name: String) {
        fun <T> feature(register: DeferredRegister<T>, vararg gemFeature: GemFeature<T>) {
            gemFeature.forEach { it.register(gemType, name, register)  }
        }
    }

    fun register(resourceLocation: ResourceLocation, gemType: GemType, features: GemFeatureRegister.() -> Unit) {
        gemTypes[resourceLocation] = gemType
        features(GemFeatureRegister(gemType, resourceLocation.namespace))
    }

    fun register(id: String, vararg entries: Pair<String, GemType>, features: GemFeatureRegister.() -> Unit) {
        gemTypes += entries.map { Pair(ResourceLocation(id, it.first), it.second) }
        entries.forEach { features(GemFeatureRegister(it.second, it.first)) }
    }

    fun register(id: String, vararg entries: Triple<String, GemType, GemFeatureRegister.() -> Unit>) {
        gemTypes += entries.map { Pair(ResourceLocation(id, it.first), it.second) }
        entries.forEach { it.third(GemFeatureRegister(it.second, it.first)) }
    }
}