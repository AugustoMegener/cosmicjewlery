package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialFluid
import com.cosmic_jewelry.common.core.material.essence.Essence
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

class EssenceFluid(name : String,
                   override val featureBuilder: (Essence) -> Fluid,
                   tags : List<TagKey<Fluid>> = listOf(),
                   essenceSymbol : String = "#", )
    : MaterialFluid<Essence, Fluid>(name, tags, essenceSymbol)