package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.feature.essence.EssenceFluidType
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.FluidType.Properties
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries

object FluidTypeRegistry {
    val fluidTypes: DeferredRegister<FluidType> = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ID)

    val essenceFluidType = EssenceFluidType("#", { FluidType(Properties.create()) })
}