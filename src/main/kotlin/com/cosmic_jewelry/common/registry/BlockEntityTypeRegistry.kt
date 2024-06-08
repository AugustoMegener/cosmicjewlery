package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.registry.BlockRegistry.lappingTableBlock
import com.cosmic_jewelry.common.world.level.block.entity.LappingTableBlockEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister

import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BlockEntityTypeRegistry {
    val blockEntityTypes = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ID)

    val lappingTableBlockEntityType by blockEntityTypes.register("lapping_table") { ->
        BlockEntityType.Builder.of({ p, s -> LappingTableBlockEntity(p, s) }, lappingTableBlock).build(null)
    }
}