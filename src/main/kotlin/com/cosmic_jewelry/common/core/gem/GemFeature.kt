package com.cosmic_jewelry.common.core.gem

import com.google.common.collect.ImmutableMap
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

open class GemFeature<T>(private val prefix: String, protected val builder: (GemType) -> T) {

    private val features = HashMap<GemType, DeferredHolder<T, out T>>()
    val allFeatures get() = ImmutableMap.copyOf(features)

    fun register(gemType: GemType, name: String, register: DeferredRegister<T>) {
        features[gemType] = register.register(prefix + name) { -> builder(gemType) }
    }
    
    operator fun get(gemType: GemType) = features[gemType]?.get()


    class GemBlock(prefix: String, builder: (GemType) -> Block) : GemFeature<Block>(prefix, builder) {
        val item = GemItem(prefix) { BlockItem(this[it]!!, Item.Properties().rarity(it.rarity)) }
    }
    class GemItem(prefix: String, builder: (GemType) -> Item) : GemFeature<Item>(prefix, builder)
}