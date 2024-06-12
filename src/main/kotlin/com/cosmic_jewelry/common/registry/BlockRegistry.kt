package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.feature.RegistryGemBlock
import com.cosmic_jewelry.common.core.gem.feature.RegistryGemOre
import com.cosmic_jewelry.common.world.level.block.LappingTableBlock
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BlockRegistry {
    val blocks = DeferredRegister.createBlocks(ID)

    val lappingTableBlock by blocks.register("lapping_table")
        { -> LappingTableBlock(Properties.of().strength(3f)) }

    val gemOreBlock = RegistryGemOre("_ore")
    val deepslateGemOreBlock = RegistryGemOre("_deepstale_ore", { (it + 4.5f) / 2 })

    val cutGemBlock = RegistryGemBlock("_gem_block") { Block(Properties.of().strength(it.mosh)) }
    val tilesBlock  = RegistryGemBlock("_tiles") { Block(Properties.of().strength(it.mosh)) }
    val pillarBlock = RegistryGemBlock("_pillar", { _, b -> (b as RotatedPillarBlock)
        logBlock(b)
        simpleBlockItem(b, models().withExistingParent(
            BuiltInRegistries.BLOCK.getKey(b).toString(), "minecraft:block/cube_column"))
    }) { RotatedPillarBlock(Properties.of().strength(it.mosh)) }
}
