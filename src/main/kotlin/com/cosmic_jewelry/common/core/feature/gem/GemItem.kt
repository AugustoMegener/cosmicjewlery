package com.cosmic_jewelry.common.core.feature.gem

import com.cosmic_jewelry.common.core.feature.MaterialItem
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.registries.DeferredRegister

open class GemItem(name: String,
                   override val featureBuilder: (GemType) -> Item = { Item(Item.Properties().rarity(it.rarity)) },
                   override val dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                   val doLapping: Boolean = false,
                   tags: List<UniversalTag> = listOf(),
                   gemSymbol: String = "#",
)
: MaterialItem<GemType>(name, tags, gemSymbol)
{

    init { all += this }

    override fun registerPost(material: GemType, context: DeferredRegister<Item>, feature: () -> Item) {
        if (doLapping) cuttersRegisters[feature] = material.mohs
    }

    companion object : ClassRegister<GemItem>() {
        val defaultProperty = { it: GemType -> Item.Properties().rarity(it.rarity) }

        private val cuttersRegisters = HashMap<() -> Item, Float>()
        val cuttersMohsMap by lazy { cuttersRegisters.mapKeys { it.key() } }

        val Item.isCutter get() = this in cuttersMohsMap.keys
        val ItemStack.isCutter get() = item.isCutter

        /**
        * Returns the mohs value if the item is a cutter, else, return null.
         */
        val Item.cutterMohs: Float? get() = cuttersMohsMap[this]
        val ItemStack.cutterMohs: Float? get() = this.item.cutterMohs
    }
}