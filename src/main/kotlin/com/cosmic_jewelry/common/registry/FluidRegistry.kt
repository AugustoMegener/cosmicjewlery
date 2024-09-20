package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import net.minecraft.core.registries.BuiltInRegistries.FLUID
import net.neoforged.neoforge.registries.DeferredRegister

object FluidRegistry {
    val fluids = DeferredRegister.create(FLUID, ID)
}