package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.common.core.gem.GemType
import com.cosmic_jewelry.common.core.gem.GemType.Companion.gemTypes
import com.cosmic_jewelry.common.core.gem.feature.GemBlock
import com.cosmic_jewelry.common.core.gem.feature.GemFeature
import com.cosmic_jewelry.common.core.gem.GemRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredRegister

object GemTypeRegister {

    private var id: String? = null

    operator fun invoke(modId: String, register: GemTypeRegister.() -> Unit) { id = modId
                                                                               register(this)
                                                                               id = null      }

    private fun assertId()
        { if (id == null) throw NullPointerException("GemTypeRegister use allowed only on it's invoke") }

    class GemTypeBuilder internal constructor(private val gemType: GemType,
                                              private val id:      String,
                                              private val name:    String  )
    {
        fun <T> feature(register: DeferredRegister<T>, vararg gemFeature: GemFeature<T>) {
            gemFeature.forEach { it.register(gemType, name, register)  }
        }

        fun blockFeature(registerBlock: DeferredRegister<Block>,
                         registerItem:  DeferredRegister<Item>,
                         vararg gemFeature: GemBlock)
        { feature(registerBlock, *gemFeature)
          feature(registerItem, *gemFeature.map { it.item }.toTypedArray()) }

        fun recipe(vararg recipe: GemRecipe) {
            recipe.forEach { it.register(gemType, id) }
        }
    }

    fun register(name: String, gemType: GemType, features: GemTypeBuilder.() -> Unit) {
        assertId()
        gemTypes[ResourceLocation(id!!, name)] = gemType
        features(GemTypeBuilder(gemType, id!!, name))
    }

    fun register(vararg entries: Pair<String, GemType>, features: GemTypeBuilder.() -> Unit) {
        entries.forEach { register(it.first, it.second, features) }
    }

    fun register(vararg entries: Triple<String, GemType, GemTypeBuilder.() -> Unit>) {
        entries.forEach { register(it.first, it.second, it.third) }
    }
}