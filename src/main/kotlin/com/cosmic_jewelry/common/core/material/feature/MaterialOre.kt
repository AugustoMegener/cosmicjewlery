package com.cosmic_jewelry.common.core.material.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

abstract class MaterialOre<T : Material<T>>(location:    ResourceLocation,
                                            gemSymbol:   String = "#",
                                            val          miningTime: (T) -> Float) :
    MaterialBlock<T>(location, gemSymbol),
    DataGenFeature<BlockStateProvider, T, Block>
{
    override fun getFeatureBuilder(material: T): Block =
        Block(BlockBehaviour.Properties.of().strength(miningTime(material), (3f)).requiresCorrectToolForDrops())

    companion object {

        @JvmInline value class MiningLevel(val tag: TagKey<Block>) {
            companion object {
                val stoneLevel = MiningLevel(BlockTags.NEEDS_STONE_TOOL)
                val ironLevel = MiningLevel(BlockTags.NEEDS_IRON_TOOL)
                val diamondLevel = MiningLevel(BlockTags.NEEDS_DIAMOND_TOOL)
            }
        }
    }
}