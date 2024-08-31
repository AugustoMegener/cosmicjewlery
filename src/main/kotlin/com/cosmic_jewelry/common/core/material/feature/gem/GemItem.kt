package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.MaterialItem
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.registries.DeferredRegister
import net.minecraft.resources.ResourceLocation.parse as loc

open class GemItem(name: String,
                   override val featureBuilder: (GemType) -> Item = { Item(Item.Properties().rarity(it.rarity)) },
                   override val dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                   val doLapping: Boolean = false,
                   tags: List<TagKey<Item>> = listOf(),
                   gemSymbol: String = "#",
)
: MaterialItem<GemType>(name, tags, gemSymbol)
{

    init { all += this }

    override fun registerPost(material: GemType, context: DeferredRegister<Item>, feature: () -> Item) {
        if (doLapping) cuttersRegisters[feature] = material.mohs
    }

    override fun getFeature(material: GemType) = get(material)!!

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

    override val featureGeneralTag = TagKey.create(BuiltInRegistries.ITEM.key(), loc("$ID:item"))
}