package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.material.feature.RegistryMaterialFeature

abstract class RegistryGemFeature<T>(name: String,
                                     gemSymbol: String = "#",
                                     private val featureGemBuilder: (GemType) -> T)
: RegistryMaterialFeature<GemType, T>(name, gemSymbol)
{
    override fun getFeatureBuilder(material: GemType) = featureGemBuilder(material)

    override fun getMaterialName(material: GemType) = material.name
}