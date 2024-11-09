package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.core.Registry
import net.neoforged.neoforge.registries.DeferredRegister

abstract class RegistrableMaterialFeature<M: Material<M>, F>(val registry       : Registry<F>,
                                                                 name           : String,
                                                             val tags           : List<UniversalTag> = listOf(),
                                                                 materialSymbol : String = "#")
: MaterialFeatureBase<M, DeferredRegister<F>, F>(name, materialSymbol)
{
    abstract val featureBuilder: (M) ->  F

    val featureSpecificTag = UniversalTag()
    abstract val featureGeneralTag: UniversalTag
    internal open val featureTags = arrayListOf<UniversalTag>()

    init { all += this }


    open fun <T: M> getMaterialTags(material: T): List<UniversalTag> = listOf()

    fun <T: M> getTags(material: T): List<UniversalTag> =
        featureTags + getMaterialTags(material) + tags + featureSpecificTag + featureGeneralTag + material.tags

    @Suppress("UNCHECKED_CAST")
    fun getTags(material: Any) = getTags(material as M)

    override fun builder(context: DeferredRegister<F>, material: M): () -> F {
        val registry = context.register(createName(material)) { -> featureBuilder(material) }
        return registry::get
    }

    companion object: ClassRegister<RegistrableMaterialFeature<*, *>>()
}