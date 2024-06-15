package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.util.ClassRegister
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

open class GemBlock(suffix: String,
                    gemSymbol: String,
                    override val dataGen: BlockStateProvider.(GemType, Block) -> Unit,
                    builder: (GemType) -> Block)
: RegistryGemFeature<Block>(suffix, gemSymbol, builder), DataGenFeature<BlockStateProvider, GemType, Block>
{
    constructor(suffix: String, gemSymbol: String, builder: (GemType) -> Block) :
            this(suffix, gemSymbol, { _, b -> simpleBlockWithItem(b, cubeAll(b)) }, builder)

    constructor(suffix: String, builder: (GemType) -> Block) :
            this(suffix, "#", { _, b -> simpleBlockWithItem(b, cubeAll(b)) }, builder)

    constructor(suffix: String, dataGen: BlockStateProvider.(GemType, Block) -> Unit, builder: (GemType) -> Block) :
            this(suffix, "#", dataGen, builder)

    val item = GemItem(suffix, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }

    init { all += this }

    override fun getFeature(material: GemType) = get(material)!!

    companion object : ClassRegister<GemBlock>()
}