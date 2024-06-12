package com.cosmic_jewelry.common.core.gem

import com.cosmic_jewelry.common.core.gem.feature.RegistryGemOre.Companion.MiningLevel
import com.cosmic_jewelry.common.core.gem.feature.RegistryGemOre.Companion.MiningLevel.Companion.ironLevel
import com.cosmic_jewelry.common.core.gem.feature.RegistryGemOre.Companion.MiningLevel.Companion.stoneLevel
import net.minecraft.world.item.Rarity

class GemFamily(val defaultMosh: Float, val defaultRarity: Rarity, builder: GemFamilyBuilder.() -> Unit) {

    private val gemsList = ArrayList<GemType>()

    val gems get() = gemsList.toTypedArray()
    val gemsByLocation get() = gemsList.associateBy { it.location }

    var miningLevel = stoneLevel; private set

    class GemFamilyBuilder internal constructor(private val target: GemFamily) {
        fun miningLevel(level: MiningLevel) { target.miningLevel = level }
    }

    init { all += this; builder(GemFamilyBuilder(this)) }

    fun newGem(mosh: Float? = null, rarity: Rarity? = null) =
        GemType(this, mosh ?: defaultMosh, rarity ?: defaultRarity).also { this.gemsList += it }

    companion object : ClassRegister<GemFamily>() {
        val quartzFamily   = GemFamily((7f),   Rarity.COMMON)   { }
        val silicateFamily = GemFamily((5.5f), Rarity.UNCOMMON) { }
        val olivineFamily  = GemFamily((6.5f), Rarity.UNCOMMON) { miningLevel(ironLevel) }
        val corundumFamily = GemFamily((7f),   Rarity.RARE)     { miningLevel(ironLevel) }
    }
}