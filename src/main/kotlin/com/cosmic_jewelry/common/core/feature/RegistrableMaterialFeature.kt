package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.material.Material.Companion.getTag
import com.cosmic_jewelry.common.core.util.ClassRegister
import net.minecraft.core.Registry
import net.minecraft.tags.TagKey
import net.neoforged.neoforge.registries.DeferredRegister

abstract class RegistrableMaterialFeature<M: Material<M>, F>(val registry       : Registry<F>,
                                                                 name           : String,
                                                             val tags           : List<TagKey<F>> = listOf(),
                                                                 materialSymbol : String = "#")
: MaterialFeatureBase<M, DeferredRegister<F>, F>(name, materialSymbol)
{
    abstract val featureBuilder: (M) ->  F

    val featureSpecificTag: TagKey<F> = TagKey.create(registry.key(), genericPath)
    abstract val featureGeneralTag: TagKey<F>
    internal open val featureTags = arrayListOf<TagKey<F>>()

    init { all += this }


    open fun <T: M> getMaterialTags(material: T): List<TagKey<F>> = listOf()

    fun <T: M> getTags(material: T): List<TagKey<F>> =
        featureTags + getMaterialTags(material) + tags + featureSpecificTag + featureGeneralTag + material.getTag(registry)

    @Suppress("UNCHECKED_CAST")
    fun getTags(material: Any) = getTags(material as M)

    override fun builder(context: DeferredRegister<F>, material: M): () -> F {
        val registry = context.register(createName(material)) { -> featureBuilder(material) }
        return registry::get
    }

    companion object: ClassRegister<RegistrableMaterialFeature<*, *>>()
}