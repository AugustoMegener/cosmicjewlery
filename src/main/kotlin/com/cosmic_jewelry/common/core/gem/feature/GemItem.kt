package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.ClassRegister
import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.registries.DeferredHolder

open class GemItem(suffix: String,
                   val doLapping: Boolean = false,
                   dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                   builder: (GemType) -> Item)
: DataGenGemFeature<Item, ItemModelProvider>(suffix, dataGen, builder)
{
    constructor(suffix: String,
                dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                builder: (GemType) -> Item) : this(suffix, false, dataGen, builder)

    init { all += this }

    override fun registerPost(gemType: GemType, name: String, feature: DeferredHolder<Item, out Item>) {
        cuttersRegisters[feature] = Pair(doLapping, gemType.mosh)
    }

    companion object : ClassRegister<GemItem>() {
        val defaultProperty = { it: GemType -> Item.Properties().rarity(it.rarity) }

        private val cuttersRegisters = HashMap<DeferredHolder<Item, out Item>, Pair<Boolean, Float>>()
        val cuttersMap by lazy { cuttersRegisters.mapKeys { it.key.get() } }

        val Item.isCutter get() = cuttersMap[this]?.first == null
        val ItemStack.isCutter get() = item.isCutter

        /**
        * Returns the mohs value if the item is a cutter, else, return null.
         */
        val Item.cutterMohs: Float? get() = if (isCutter) cuttersMap[this]?.second else null
        val ItemStack.cutterMohs: Float? get() = this.item.cutterMohs
    }
}