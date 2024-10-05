package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.client.util.MaterialFluidTypeExt
import com.cosmic_jewelry.common.core.feature.essence.EssenceFluidType
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.tags.TagKey
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.FluidType.Properties
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.FLUID_TYPES


object FluidTypeRegistry {
    val fluidTypes: DeferredRegister<FluidType> = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ID)

    val essenceFluid: TagKey<FluidType> = TagKey.create(FLUID_TYPES, parse("c:material"))

    val essenceFluidType = EssenceFluidType("#", { FluidType(Properties.create()) }, { MaterialFluidTypeExt(it, "#", "#") })

}