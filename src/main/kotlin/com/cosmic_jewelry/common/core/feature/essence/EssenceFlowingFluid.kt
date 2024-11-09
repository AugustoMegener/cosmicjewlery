package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialFlowingFluid
import com.cosmic_jewelry.common.core.material.essence.Essence
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.world.level.material.FlowingFluid

class EssenceFlowingFluid(name : String,
                          override val featureBuilder : (Essence) -> FlowingFluid,
                          tags : List<UniversalTag> = listOf(),
                          essenceSymbol : String = "#"                   )
    : MaterialFlowingFluid<Essence>(name, tags, essenceSymbol)