package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.GemFeature
import com.cosmic_jewelry.common.registry.GemTypeRegistry.gemTypes
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

object BlockRegistry {
    val blocks = DeferredRegister.create(ID, Registries.BLOCK)

    val gemBlock  = GemFeature("block/")  { Block(Properties.of().strength(it.mosh))              }
        .also { it.registerAll(blocks, gemTypes) }
    val gemTiles  = GemFeature("tiles/")  { Block(Properties.of().strength(it.mosh))              }
        .also { it.registerAll(blocks, gemTypes) }
    val gemPillar = GemFeature("pillar/") { RotatedPillarBlock(Properties.of().strength(it.mosh)) as Block }
        .also { it.registerAll(blocks, gemTypes) }
}