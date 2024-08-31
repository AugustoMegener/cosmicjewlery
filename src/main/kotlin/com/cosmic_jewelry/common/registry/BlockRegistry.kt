package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.gem.GemBlock
import com.cosmic_jewelry.common.core.material.feature.gem.GemOre
import com.cosmic_jewelry.common.world.level.block.LappingTableBlock
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BlockRegistry {
    val blocks: DeferredRegister.Blocks = DeferredRegister.createBlocks(ID)

    val lappingTableBlock: LappingTableBlock by blocks.register("lapping_table")
                           { -> LappingTableBlock(Properties.of().strength(3f)) }

    val gemOreBlock = GemOre("#_ore")
    val deepslateGemOreBlock = GemOre("deepslate_#_ore", {(it + 4.5f) / 2 })

    val cutGemBlock = GemBlock("#_gem_block", { Block(Properties.of().strength(it.mohs)) })
    val tilesBlock  = GemBlock("#_tiles", { Block(Properties.of().strength(it.mohs)) })
    val pillarBlock = GemBlock("#_pillar", { RotatedPillarBlock(Properties.of().strength(it.mohs)) },
        { _, b -> (b as RotatedPillarBlock);
        logBlock(b)
        simpleBlockItem(b, models().withExistingParent(BuiltInRegistries.BLOCK.getKey(b).toString(),
                                                "minecraft:block/cube_column")) })
}
