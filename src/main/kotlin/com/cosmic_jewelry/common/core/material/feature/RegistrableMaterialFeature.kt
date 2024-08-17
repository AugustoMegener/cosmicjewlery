package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.neoforged.neoforge.registries.DeferredRegister

abstract class RegistrableMaterialFeature<M: Material<M>, F>(name: String, materialSymbol: String = "#")
: MaterialFeatureBase<M, DeferredRegister<F>, F>(name, materialSymbol)
{
    abstract val featureBuilder: (M) ->  F

    override fun builder(context: DeferredRegister<F>, material: M): () -> F {
        val registry = context.register(createName(material)) { -> featureBuilder(material) }
        return registry::get
    }
}