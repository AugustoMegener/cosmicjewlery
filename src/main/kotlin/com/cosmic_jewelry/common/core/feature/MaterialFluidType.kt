package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.level.block.LiquidBlock
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions
import net.neoforged.neoforge.fluids.BaseFlowingFluid
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.registries.NeoForgeRegistries.FLUID_TYPES

abstract class MaterialFluidType<M : Material<M>>(name : String,
                                                  tags : List<UniversalTag> = listOf(),
                                                  materialSymbol : String = "#")
    : RegistrableMaterialFeature<M, FluidType>(FLUID_TYPES, name, tags, materialSymbol)
{
    init { all += this }

    abstract val    clientExt : (M) -> IClientFluidTypeExtensions
    abstract val flowingFluid : MaterialFlowingFluid<M>
    abstract val  sourceFluid : MaterialFlowingFluid<M>
    abstract val       bucket : MaterialItem<M>
    abstract val        block : MaterialBlock<M>

    override fun <T : M> getMaterialTags(material: T) = listOf(UniversalTag(parse("$ID:${material.id.path}_item")))

    open fun getFlowingFluidProperties(material: M): BaseFlowingFluid.Properties =
        BaseFlowingFluid.Properties({ this[material] }, { sourceFluid[material] }, { flowingFluid[material] })
            .slopeFindDistance(2)
            .levelDecreasePerBlock(5)
            .bucket { bucket[material] }
            .block  { block[material] as LiquidBlock }

    @Suppress("UNCHECKED_CAST")
    fun getClientExtUnsafe(material: Any) = clientExt(material as M)

    companion object : ClassRegister<MaterialFluidType<*>>()
}