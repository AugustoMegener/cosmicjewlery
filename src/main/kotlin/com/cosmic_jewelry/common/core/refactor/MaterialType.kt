package com.cosmic_jewelry.common.core.refactor

class MaterialType<T : Material> {

    private val materialMap = arrayListOf<Material>()

    init { register() }

    companion object : Registry<MaterialType<*>>()
}