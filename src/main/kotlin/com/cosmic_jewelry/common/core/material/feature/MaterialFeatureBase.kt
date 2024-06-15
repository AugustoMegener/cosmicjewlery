package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material

abstract class MaterialFeatureBase<M : Material<M>, C, F>(protected val name: String,
                                                       private val materialSymbol: String = "#")
{
    private val registerMap = HashMap<M, () -> F>()

    val content by lazy { registerMap.mapValues { it.value() } }
    val materials get() = ArrayList(content.keys)
    val features get() = ArrayList(content.values)

    abstract fun builder(context: C, material: M): () -> F

    fun register(material: M, context: C) {
        registerPre(material, context)
        registerMap[material] = builder(context, material).also { registerPost(material, context, it) }
    }

    operator fun get(material: M) = content[material]

    open fun  registerPre(material: M, context: C) {}
    open fun registerPost(material: M, context: C, feature: () -> F) {}

    abstract fun getMaterialName(material: M): String
    protected fun createName(material: M) = name.replace(materialSymbol, getMaterialName(material))
}