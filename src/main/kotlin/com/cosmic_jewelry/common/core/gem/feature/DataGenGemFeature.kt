package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.data.DataProvider

abstract class DataGenGemFeature<T, P: DataProvider>(suffix: String,
                                                     private val dataGen: (P.(GemType, T) -> Unit),
                                                     builder: (GemType) -> T)
: GemFeature<T>(suffix, builder) {
    fun generateData(provider: P, gemType: GemType) { dataGen(provider, gemType, get(gemType)!!) }
}