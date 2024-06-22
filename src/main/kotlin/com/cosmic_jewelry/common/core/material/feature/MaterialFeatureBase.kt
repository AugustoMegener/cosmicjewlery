package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialFeatureBase<M : Material<M>, C, F>(protected val name: String,
                                                       private val materialSymbol: String = "#")
{
    private val registerMap = HashMap<M, () -> F>()

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

    abstract fun getMaterialName(material: M): String
    protected fun createName(material: M) = name.replace(materialSymbol, getMaterialName(material))
    protected fun createPath(material: M) = loc("${material.owner}:${createName(material)}")
}