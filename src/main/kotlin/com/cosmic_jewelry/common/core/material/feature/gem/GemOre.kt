package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import com.cosmic_jewelry.common.core.material.feature.MaterialOre
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.registry.ItemRegistry.rawGemItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

class GemOre(name: String,
             gemSymbol: String = "#",
             override val miningTime: (Float) -> Float,
             override val dataGen: BlockStateProvider.(GemType, Block) -> Unit, )
: MaterialOre<GemType>(name, gemSymbol),
    DataGenFeature<BlockStateProvider, GemType, Block>
{
    override val dropItem = rawGemItem

    override val item = GemItem(name, { _, _ -> }) { BlockItem(this[it]!!, GemItem.defaultProperty(it)) }

    constructor(name: String, miningTime: (Float) -> Float, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, "#", miningTime, dataGen)

    constructor(name: String, miningTime: (Float) -> Float) :
            this(name, "#", miningTime, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })

    constructor(name: String, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, { (it + 3f) / 2 }, dataGen)

    constructor(name: String) :
            this(name, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })
}