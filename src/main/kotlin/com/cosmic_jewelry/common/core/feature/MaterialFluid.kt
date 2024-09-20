package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.core.registries.BuiltInRegistries.FLUID
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

abstract class MaterialFluid<M : Material<M>, F : Fluid>(name           : String,
                                                         tags           : List<TagKey<Fluid>> = listOf(),
                                                         materialSymbol : String = "#")
    : RegistrableMaterialFeature<M, Fluid>(FLUID, name, tags, materialSymbol)
{
    override val featureGeneralTag: TagKey<Fluid> = TagKey.create(FLUID.key(), parse("$ID:fluid"))
}