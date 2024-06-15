package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import com.cosmic_jewelry.common.core.material.feature.MaterialOre
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

class GemOre private constructor(builder: GemOreBuilder) :
    MaterialOre<GemType>(builder.location, builder.gemSymbol, builder.miningTime),
    DataGenFeature<BlockStateProvider, GemType, Block>
{
    constructor(location: ResourceLocation, gemSymbol: String = "#", builder: GemOreBuilder.() -> Unit) :
            this(GemOreBuilder(location, gemSymbol).also { builder(it) })

    class GemOreBuilder internal constructor(val location: ResourceLocation, val gemSymbol: String = "#") {
        private var miningTimeValue: ((GemType) -> Float)? = null
        internal val miningTime get() = miningTimeValue ?: { (it.mosh + 3f) / 2 }

        private var modelValue: (BlockStateProvider.(GemType, Block) -> Unit)? = null
        internal val model get() = modelValue ?: { _, b -> simpleBlockWithItem(b, cubeAll(b)) }

        fun miningTime(value: (GemType) -> Float) { miningTimeValue = value }
        fun model(value: BlockStateProvider.(GemType, Block) -> Unit) { modelValue = value }
    }

    override val dataGen: BlockStateProvider.(GemType, Block) -> Unit = builder.model

    override val item = GemItem(location, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }


    init { all += this; Blocks.IRON_ORE }

    companion object : ClassRegister<GemOre>()
}