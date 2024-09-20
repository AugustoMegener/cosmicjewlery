package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.essence.Essence
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider

class EssenceBlock(name: String,
                   override val featureBuilder: (Essence) -> Block,
                   override val dataGen: BlockStateProvider.(Essence, Block) -> Unit =
                            { _, b -> simpleBlockWithItem(b, cubeAll(b)) },
                   tags: List<TagKey<Block>> = listOf(),
                   essenceSymbol: String = "#")
    : MaterialBlock<Essence>(name, tags, essenceSymbol)
{
    override val item =
        EssenceItem(name, { BlockItem(this[it]!!, Properties()) }, { _, _ -> }, essenceSymbol = essenceSymbol)
}