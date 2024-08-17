package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

open class GemBlock(             name           : String,
                                 gemSymbol      : String = "#",
                    override val dataGen        : BlockStateProvider.(GemType, Block) -> Unit,
                    override val featureBuilder : (GemType) -> Block)
: MaterialBlock<GemType>(name, gemSymbol)
{
    constructor(suffix: String, gemSymbol: String, builder: (GemType) -> Block) :
            this(suffix, gemSymbol, { _, b -> simpleBlockWithItem(b, cubeAll(b)) }, builder)

    constructor(suffix: String, builder: (GemType) -> Block) :
            this(suffix, { _, b -> simpleBlockWithItem(b, cubeAll(b)) }, builder)

    constructor(suffix: String, dataGen: BlockStateProvider.(GemType, Block) -> Unit, builder: (GemType) -> Block) :
            this(suffix, "#", dataGen, builder)

    override val item = GemItem(name, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }

    init { all += this }

    companion object : ClassRegister<GemBlock>()
}