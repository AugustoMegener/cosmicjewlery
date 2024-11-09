package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.level.material.FlowingFluid

abstract class MaterialFlowingFluid<M : Material<M>>(
    name: String,
    tags: List<UniversalTag> = listOf(),
    materialSymbol: String = "#"
) : MaterialFluid<M, FlowingFluid>(name, tags, materialSymbol) {
    override fun <T : M> getMaterialTags(material: T) = listOf(UniversalTag(parse("$ID:${material.id.path}_ore")))
}