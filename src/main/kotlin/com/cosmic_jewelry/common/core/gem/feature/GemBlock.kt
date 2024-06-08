package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.ClassRegister
import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

open class GemBlock(suffix: String,
                    dataGen: BlockStateProvider.(GemType, Block) -> Unit = { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
                    builder: (GemType) -> Block)
    : DataGenGemFeature<Block, BlockStateProvider>(suffix, dataGen, builder)
{
    val item = GemItem(suffix, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }

    init { all += this }

    companion object : ClassRegister<GemBlock>()
}