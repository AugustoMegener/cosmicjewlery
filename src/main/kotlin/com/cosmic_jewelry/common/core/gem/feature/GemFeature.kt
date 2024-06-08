package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.GemType
import com.google.common.collect.ImmutableMap
import net.minecraft.network.chat.Component
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

open class GemFeature<T>(private val suffix: String, protected val builder: (GemType) -> T) {

    private val features = HashMap<GemType, DeferredHolder<T, out T>>()

    val featuresMap: Map<GemType, DeferredHolder<T, out T>> get() = ImmutableMap.copyOf(features)
    val allGemTypes get() = ArrayList(features.keys)
    val allFeatures get() = ArrayList(features.values)

    val translatableName get() = Component.translatable("features.$suffix")

    fun register(gemType: GemType, name: String, register: DeferredRegister<T>) {
        registerPre(gemType, name)
        features[gemType] = register.register(name + suffix) { -> builder(gemType) }
            .also { registerPost(gemType, name, it) }
    }

    open fun  registerPre(gemType: GemType, name: String) {}
    open fun registerPost(gemType: GemType, name: String, feature: DeferredHolder<T, out T>) {}

    operator fun get(gemType: GemType) = features[gemType]?.get()
}