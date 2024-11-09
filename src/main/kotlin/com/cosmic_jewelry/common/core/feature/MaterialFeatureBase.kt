package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialFeatureBase<M : Material<M>, C, F>(val id              : ResourceLocation,
                                                          val materialSymbol  : String = "#") {

    val name = id.path

    val universalId = loc("${id.namespace}:${id.path.replace(materialSymbol, "material")}")

    protected val registerMap = HashMap<M, () -> F>()

    val content   by lazy { registerMap.mapValues { it.value() }       }
    val users     by lazy { content.map { it.value to it.key }.toMap() }
    val materials by lazy { ArrayList(content.keys)   }
    val features  by lazy { ArrayList(content.values) }

    abstract fun builder(context: C, material: M): () -> F

    fun register(material: M, context: C) {
        registerPre(material, context)
        registerMap[material] = builder(context, material).also { registerPost(material, context, it) }
    }

    operator fun get(material: M) = content[material]

    open fun  registerPre(material: M, context: C) {}
    open fun registerPost(material: M, context: C, feature: () -> F) {}

    protected fun createName(material: M) = name.replace(materialSymbol, material.name)
    fun createPath(material: M): ResourceLocation = loc("${material.owner}:${createName(material)}")

    @Suppress("UNCHECKED_CAST")
    fun createPathUnsafe(material: Material<*>) = createPath(material as M)

    fun getGenericName(material: Material<*>) = name.replace(materialSymbol, material.id.path)
    fun getGenericPath(material: Material<*>) = loc("${material.id.namespace}:${getGenericName(material)}")
}