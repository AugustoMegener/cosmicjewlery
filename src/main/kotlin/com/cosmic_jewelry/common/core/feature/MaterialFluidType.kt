package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.LiquidBlock
import net.neoforged.neoforge.fluids.BaseFlowingFluid
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.registries.NeoForgeRegistries.FLUID_TYPES

abstract class MaterialFluidType<M : Material<M>>(name : String,
                                                  tags : List<TagKey<FluidType>> = listOf(),
                                                  materialSymbol : String = "#")
    : RegistrableMaterialFeature<M, FluidType>(FLUID_TYPES, name, tags, materialSymbol)
{
    abstract val flowingFluid : MaterialFlowingFluid<M>
    abstract val  sourceFluid : MaterialFlowingFluid<M>
    abstract val       bucket : MaterialItem<M>
    abstract val        block : MaterialBlock<M>

    override val featureGeneralTag: TagKey<FluidType> = TagKey.create(FLUID_TYPES.key(), parse("$ID:fluid"))

    fun getFlowingFluidProperties(material: M): BaseFlowingFluid.Properties =
        BaseFlowingFluid.Properties({ this[material] }, { sourceFluid[material] }, { flowingFluid[material] })
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .bucket { bucket[material] }
            .block  { block[material] as LiquidBlock }
}