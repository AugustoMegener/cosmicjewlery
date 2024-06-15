package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.material.feature.MaterialFeatureBase

abstract class GemFeature<C, T>(name: String, gemSymbol: String = "#")
: MaterialFeatureBase<GemType, C, T>(name, gemSymbol) {
    override fun getMaterialName(material: GemType) = material.name
}