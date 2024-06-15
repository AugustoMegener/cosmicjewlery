package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation

abstract class MaterialFeatureBase<M : Material<M>, C, F>(protected val location: ResourceLocation,
                                                          private val materialSymbol: String = "#")
{
    val namespace = location.namespace
    val name = location.path

    private val registerMap = HashMap<M, () -> F>()

    val content by lazy { registerMap.mapValues { it.value() } }
    val materials get() = ArrayList(content.keys)
    val features get() = ArrayList(content.values)


    fun register(material: M, context: C) {
        registerPre(material, context)
        registerMap[material] = builder(context, material).also { registerPost(material, context, it) }
    }

    operator fun get(material: M) = content[material]

    open fun  registerPre(material: M, context: C) {}
    open fun registerPost(material: M, context: C, feature: () -> F) {}

    fun createName(material: M) = name.replace(materialSymbol, material.name)
    fun createTypeName(material: M) = name.replace(materialSymbol, material.typeName)

    fun createLocation(material: M) = ResourceLocation(namespace, createName(material))
    fun createTypeLocation(material: M) = ResourceLocation(namespace, createTypeName(material))

    abstract fun builder(context: C, material: M): () -> F
}