package com.cosmic_jewelry.common.core.material.feature.gem

import com.cosmic_jewelry.common.util.ClassRegister
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.material.feature.DataGenFeature
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

class GemOre(name: String,
             gemSymbol: String = "#",
             miningTime: (Float) -> Float,
             override val dataGen: BlockStateProvider.(GemType, Block) -> Unit,
) : GemBlock(name, gemSymbol, { DropExperienceBlock(ConstantInt.of((0)),
                                                    Properties.of().strength(miningTime(it.mosh), (3f))
                                                        .requiresCorrectToolForDrops()) }),
    DataGenFeature<BlockStateProvider, GemType, Block>
{
    constructor(name: String, miningTime: (Float) -> Float, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, "#", miningTime, dataGen)

    constructor(name: String, miningTime: (Float) -> Float) :
            this(name, "#", miningTime, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })

    constructor(name: String, dataGen: BlockStateProvider.(GemType, Block) -> Unit) :
            this(name, { (it + 3f) / 2 }, dataGen)

    constructor(name: String) :
            this(name, { _, b -> simpleBlockWithItem(b, cubeAll(b)) })

    init { all += this; Blocks.IRON_ORE }



    companion object : ClassRegister<GemOre>() {

        @JvmInline value class MiningLevel(val tag: TagKey<Block>) {
            companion object {
                val stoneLevel = MiningLevel(BlockTags.NEEDS_STONE_TOOL)
                val ironLevel = MiningLevel(BlockTags.NEEDS_IRON_TOOL)
                val diamondLevel = MiningLevel(BlockTags.NEEDS_DIAMOND_TOOL)
            }
        }
    }
}