package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.neoforged.neoforge.registries.DeferredRegister

abstract class RegistryMaterialFeature<M: Material<M>, F>(name: String, materialSymbol: String = "#")
: MaterialFeatureBase<M, DeferredRegister<F>, F>(name, materialSymbol)
{

    abstract  fun getFeatureBuilder(material: M):  F

    override fun builder(context: DeferredRegister<F>, material: M): () -> F {
        val registry = context.register(createName(material)) { -> getFeatureBuilder(material) }
        return registry::get
    }
}