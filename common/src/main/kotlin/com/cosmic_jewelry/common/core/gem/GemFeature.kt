package com.cosmic_jewelry.common.core.gem

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.Registrar

class GemFeature<T>(private val prefix:     String,
                            val builder:    (GemType) -> T)
{
    val features = HashMap<GemType, () -> T>()

    fun getLocation(gemType: GemType, target: Registrar<GemType>) = target.getId(gemType)?.withPrefix(prefix)

    fun registerAll(register: DeferredRegister<T>, target: Registrar<GemType>) {
        target.forEach {
            val feature = register.register(getLocation(it, target)!!.namespace) { builder(it) }
            features[it] = { feature.get() }
        }
    }

    operator fun get(gemType: GemType) = features[gemType]?.let { it() }
}