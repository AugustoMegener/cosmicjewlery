package com.cosmic_jewelry.common.core.feature

import com.cosmic_jewelry.common.core.material.Material
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid

abstract class MaterialFlowingFluid<M: Material<M>>(name           : String,
                                                    tags           : List<TagKey<Fluid>> = listOf(),
                                                    materialSymbol : String = "#")
    : MaterialFluid<M, FlowingFluid>(name, tags, materialSymbol)