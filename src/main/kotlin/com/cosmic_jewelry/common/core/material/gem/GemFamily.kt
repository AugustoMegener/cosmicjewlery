package com.cosmic_jewelry.common.core.material.gem



import com.cosmic_jewelry.common.core.feature.MaterialOre
import com.cosmic_jewelry.common.core.feature.MaterialOre.Companion.MiningLevel.Companion.ironLevel
import com.cosmic_jewelry.common.core.feature.MaterialOre.Companion.MiningLevel.Companion.stoneLevel
import com.cosmic_jewelry.common.datagen.OreGenProvider.commonOrePlacement
import com.cosmic_jewelry.common.registry.BlockRegistry.deepslateGemOreBlock
import com.cosmic_jewelry.common.registry.BlockRegistry.gemOreBlock
import com.cosmic_jewelry.common.util.ClassRegister
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.target
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement.triangle
import net.minecraft.world.level.levelgen.placement.PlacementModifier
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

class GemFamily(val defaultMosh: Float, val defaultRarity: Rarity, builder: GemFamilyBuilder.() -> Unit) {

    private val gemsList = ArrayList<GemType>()

    val gems get() = gemsList.toTypedArray()
    val gemsByLocation get() = gemsList.associateBy { it.location }

    var miningLevel = stoneLevel; private set
    var oreConfiguration = { g: GemType ->
        OreConfiguration(TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), gemOreBlock[g]!!.defaultBlockState(), 9) }
        private set
    var orePlacements = arrayListOf<PlacementModifier>(); private set

    class GemFamilyBuilder internal constructor(private val target: GemFamily) {
        fun miningLevel(level: MaterialOre.Companion.MiningLevel) { target.miningLevel = level }
        fun oreConfiguration(configuration: (GemType) -> OreConfiguration) { target.oreConfiguration = configuration }
        fun orePlacements(placements: List<PlacementModifier>) { target.orePlacements += placements }
    }

    init { all += this; builder(GemFamilyBuilder(this)) }

    fun newGem(mosh: Float? = null, rarity: Rarity? = null) =
        GemType(this, mosh ?: defaultMosh, rarity ?: defaultRarity).also { this.gemsList += it }

    companion object : ClassRegister<GemFamily>() {
        val stoneReplaceRule = TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
        val slateReplaceRule = TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)

        val  diamondFamily = GemFamily(10f,  Rarity.EPIC) {}

        val   quartzFamily = GemFamily(7.0f, Rarity.COMMON)   {}
        val silicateFamily = GemFamily(5.5f, Rarity.UNCOMMON) {}

        val  olivineFamily = GemFamily(6.5f, Rarity.UNCOMMON) {
            miningLevel(ironLevel)

            orePlacements(
                commonOrePlacement(2, triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32)))
            )

            oreConfiguration {
                OreConfiguration(listOf(target(slateReplaceRule, deepslateGemOreBlock[it]!!.defaultBlockState())), 7)
            }
        }

        val corundumFamily = GemFamily(7.0f, Rarity.RARE) {
            miningLevel(ironLevel)

            orePlacements(
                commonOrePlacement(1, triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(32)))
            )

            oreConfiguration {
                OreConfiguration(listOf(target(stoneReplaceRule, gemOreBlock[it]!!.defaultBlockState())), 4)
            }
        }
    }
}