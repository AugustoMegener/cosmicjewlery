package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialFluid
import com.cosmic_jewelry.common.core.material.essence.Essence
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.world.level.material.Fluid

class EssenceFluid(name : String,
                   override val featureBuilder: (Essence) -> Fluid,
                   tags : List<UniversalTag> = listOf(),
                   essenceSymbol : String = "#", )
    : MaterialFluid<Essence, Fluid>(name, tags, essenceSymbol)