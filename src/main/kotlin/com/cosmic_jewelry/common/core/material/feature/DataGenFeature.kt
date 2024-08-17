package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.data.DataProvider

interface DataGenFeature<P: DataProvider, M: Material<M>, F> {
    val dataGen: P.(M, F) -> Unit

    fun getFeature(material: M): F
    fun generateData(provider: P, material: M) { dataGen(provider, material, getFeature(material)!!) }

    @Suppress("UNCHECKED_CAST")
    fun <T: M> generateData(provider: P, material: Any) { generateData(provider, material as M) }
 }