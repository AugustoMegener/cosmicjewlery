package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.GemFeature.GemBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.registries.DeferredRegister

object BlockRegistry {
    val blocks = DeferredRegister.createBlocks(ID)

    val cutGemBlock = GemBlock("block/gem/")   { Block(Properties.of().strength(it.mosh))              }
    val tilesBlock  = GemBlock("tiles/") { Block(Properties.of().strength(it.mosh))              }
    val pillarBlock = GemBlock("pillar/") { RotatedPillarBlock(Properties.of().strength(it.mosh)) }
}