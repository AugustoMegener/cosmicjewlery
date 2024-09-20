package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialFlowingFluid
import com.cosmic_jewelry.common.core.material.essence.Essence
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid

class EssenceFlowingFluid(name : String,
                          override val featureBuilder : (Essence) -> FlowingFluid,
                          tags : List<TagKey<Fluid>> = listOf(),
                          essenceSymbol : String = "#"                   )
    : MaterialFlowingFluid<Essence>(name, tags, essenceSymbol)