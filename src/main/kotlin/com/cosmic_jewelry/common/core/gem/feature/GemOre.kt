package com.cosmic_jewelry.common.core.gem.feature

import com.cosmic_jewelry.common.core.gem.ClassRegister
import com.cosmic_jewelry.common.core.gem.GemType
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

class GemOre(
    suffix: String,
    miningTime: (Float) -> Float = { (it + 3f) / 2 },
    dataGen: BlockStateProvider.(GemType, Block) -> Unit = { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
) : GemBlock(suffix,
             dataGen,
             { DropExperienceBlock(ConstantInt.of((0)), Properties.of().strength(miningTime(it.mosh), (3f))
                                                                       .requiresCorrectToolForDrops()) } )
{
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