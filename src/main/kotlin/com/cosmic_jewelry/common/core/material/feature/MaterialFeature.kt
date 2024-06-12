package com.cosmic_jewelry.common.core.material.feature

import com.google.common.collect.ImmutableMap

open class MaterialFeature<M, F>(private val suffix: String, protected val builder: (M) -> F) {

    private val featureMap = HashMap<M, F>()

    val content: Map<M, F> get() = ImmutableMap.copyOf(featureMap)
    val gemTypes get() = ArrayList(featureMap.keys)
    val features get() = ArrayList(featureMap.values)

    fun register(material: M, feature: F) {
        registerPre(material, feature)
        featureMap[material] = feature
        registerPost(material, feature)
    }

    open fun  registerPre(material: M, feature: F) {}
    open fun registerPost(material: M, feature: F) {}
}