package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.common.core.feature.MaterialBlock
import com.cosmic_jewelry.common.core.feature.MaterialFeatureBase
import com.cosmic_jewelry.common.core.feature.MaterialFluidType
import com.cosmic_jewelry.common.core.feature.RegistrableMaterialFeature
import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.registries.DeferredRegister

object MaterialRegister {

    private var id: String? = null

    operator fun invoke(modId: String, register: MaterialRegister.() -> Unit) { id = modId
                                                                               register(this)
                                                                               id = null      }

    private fun assertId()
        { if (id == null) throw NullPointerException("GemTypeRegister use allowed only on it's invoke") }

    open class MaterialBuilder<M : Material<M>>(private val material: M, private val id: String) {
        fun <F, C> feature(ctx: C, vararg features: MaterialFeatureBase<M, C, F>) {
            features.forEach { it.register(material, ctx) }
        }

        fun <F> feature(vararg features: MaterialFeatureBase<M, String, F>) {
            features.forEach { it.register(material, id) }
        }

        fun <T> feature(register: DeferredRegister<T>, vararg registryGemFeature:RegistrableMaterialFeature<M, T>) {
            registryGemFeature.forEach { it.register(material, register)  }
        }

        companion object {
            fun <T: MaterialBlock<M>, M: Material<M>> MaterialBuilder<M>.blockFeature(
                registerBlock: DeferredRegister<Block>, registerItem : DeferredRegister<Item>, vararg gemFeature : T)

            { feature(registerBlock, *gemFeature)
              feature(registerItem, *gemFeature.map { it.item }.toTypedArray()) }

            fun <T: MaterialFluidType<M>, M: Material<M>> MaterialBuilder<M>.fluidTypeFeature(
                registerFluidType : DeferredRegister<FluidType>,
                    registerFluid : DeferredRegister<Fluid>,
                    registerBlock : DeferredRegister<Block>,
                     registerItem : DeferredRegister<Item>,
                vararg feature : T
            ) {
                feature(registerFluidType, *feature)
                feature(registerFluid, *feature.flatMap { listOf(it.sourceFluid, it.flowingFluid) }.toTypedArray())
                feature(registerItem, *feature.map { it.bucket }.toTypedArray())
                blockFeature(registerBlock, registerItem, *feature.map { it.block }.toTypedArray())
            }
        }
    }

    fun <M : Material<M>> addMaterial(name: String, material: M, features: MaterialBuilder<M>.() -> Unit) {
        material.registry[parse(("${id!!}:$name"))] = material
        addFeatures(material, features)
    }

    fun <M : Material<M>> addMaterial(vararg materials: Pair<String, M>, features: MaterialBuilder<M>.() -> Unit) {
        materials.forEach {
            it.second.registry[parse(("${id!!}:${it.first}"))] = it.second
            addFeatures(it.second, features)
        }
    }

    fun <M : Material<M>> addMaterial(vararg materials: Triple<String, M, MaterialBuilder<M>.() -> Unit>) {
        materials.forEach {
            it.second.registry[parse(("${id!!}:${it.first}"))] = it.second
            addFeatures(it.second, it.third)
        }
    }

    fun <M : Material<M>> addFeatures(material: M, features: MaterialBuilder<M>.() -> Unit) {
        assertId()
        features(MaterialBuilder(material, id!!))
    }

    fun <M : Material<M>> addFeatures(vararg entries: M, features: MaterialBuilder<M>.() -> Unit) {
        entries.forEach { addFeatures(it, features) }
    }

    fun <M : Material<M>> addFeatures(vararg entries: Pair<M, MaterialBuilder<M>.() -> Unit>) {
        entries.forEach { addFeatures(it.first, it.second) }
    }

}