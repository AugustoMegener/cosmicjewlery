package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.core.registries.BuiltInRegistries.FLUID
import net.minecraft.world.level.material.Fluid
import net.minecraft.resources.ResourceLocation.parse as loc

abstract class MaterialFluid<M : Material<M>, F : Fluid>(name           : String,
                                                         tags           : List<UniversalTag> = listOf(),
                                                         materialSymbol : String = "#")
    : RegistrableMaterialFeature<M, Fluid>(FLUID, name, tags, materialSymbol)
{
    override fun <T : M> getMaterialTags(material: T) =
        listOf(UniversalTag(loc("$ID:${material.id.path}_flowing_fluid")))
}