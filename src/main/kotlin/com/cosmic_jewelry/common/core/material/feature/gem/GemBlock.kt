package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem

open class GemBlock(location: ResourceLocation,
                    gemSymbol: String,
                    builder: MaterialBlockBuilder<GemType>.() -> MaterialBlockBuilder<GemType>) :
    MaterialBlock<GemType>(location, gemSymbol, builder)
{
        constructor(location: ResourceLocation,
                    builder: MaterialBlockBuilder<GemType>.() -> MaterialBlockBuilder<GemType>) :
                this(location, "#", builder)

    override val item = GemItem(location, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }

    init { all += this }

    override fun getFeature(material: GemType) = get(material)!!

    companion object : ClassRegister<GemBlock>()
}