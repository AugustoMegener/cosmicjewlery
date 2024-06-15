package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.MaterialItem
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.registries.DeferredRegister

open class GemItem(             location:   ResourceLocation,
                                gemSymbol:  String = "#",
                            val doLapping:  Boolean = false,
                   override val dataGen:    ItemModelProvider.(GemType, Item) -> Unit,
                            val builder:    (GemType) -> Item) :
    MaterialItem<GemType>(location, gemSymbol)
{
    constructor(location: ResourceLocation,
                gemSymbol: String = "#",
                dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                builder: (GemType) -> Item) : this(location, gemSymbol, false, dataGen, builder)

    constructor(location: ResourceLocation,
                dataGen: ItemModelProvider.(GemType, Item) -> Unit = { _, i -> basicItem(i) },
                builder: (GemType) -> Item) : this(location, "#", false, dataGen, builder)

    constructor(location: ResourceLocation , builder: (GemType) -> Item) :
            this(location, "#", false, { _, i -> basicItem(i) }, builder)

    constructor(location: ResourceLocation, doLapping: Boolean, builder: (GemType) -> Item) :
            this(location, "#", doLapping, { _, i -> basicItem(i) }, builder)


    init { all += this }


    override fun registerPost(material: GemType, context: DeferredRegister<Item>, feature: () -> Item) {
        if (doLapping) cuttersRegisters[feature] = material.mosh
    }

    override fun getFeature(material: GemType) = get(material)!!

    override fun getFeatureBuilder(material: GemType) = builder(material)


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