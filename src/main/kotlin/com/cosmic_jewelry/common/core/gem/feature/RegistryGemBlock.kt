package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.ClassRegister
import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

open class RegistryGemBlock(suffix: String,
                            dataGen: BlockStateProvider.(GemType, Block) -> Unit = { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
                            builder: (GemType) -> Block)
    : DataGenRegistryGemFeature<Block, BlockStateProvider>(suffix, dataGen, builder)
{
    val item = RegistryGemItem(suffix, { _, _ -> }) { BlockItem(this[it]!!, RegistryGemItem.defaultProperty(it)) }

    init { all += this }

    companion object : ClassRegister<RegistryGemBlock>()
}