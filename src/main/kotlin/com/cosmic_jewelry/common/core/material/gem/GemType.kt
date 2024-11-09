package com.cosmic_jewelry.common.core.material.gem

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.corundumFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.diamondFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.olivineFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.quartzFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.silicateFamily
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity
import net.minecraft.resources.ResourceLocation.parse as loc

class GemType internal constructor(val family: GemFamily, override val mohs: Float, override val rarity: Rarity)
    : Material<GemType>(loc("$ID:gem"))
{

    override val location get() = gemTypes.entries.find { it.value == this }!!.key

    override val registry = gemTypes

    val translatableName: MutableComponent get() = Component.translatable(location.toLanguageKey())

    companion object {
        val gemTypes = HashMap<ResourceLocation, GemType>()

        val  whiteDiamondGem = diamondFamily.newGem()
        val yellowDiamondGem = diamondFamily.newGem()
        val   blueDiamondGem = diamondFamily.newGem()
        val   pinkDiamondGem = diamondFamily.newGem()

        val     amethystGem = quartzFamily.newGem()
        val   blueQuartzGem = quartzFamily.newGem()
        val    carnelianGem = quartzFamily.newGem()
        val orangeJasperGem = quartzFamily.newGem()
        val   roseQuartzGem = quartzFamily.newGem()

        val      peridotGem = olivineFamily.newGem()

        val  lapisLazuliGem = silicateFamily.newGem(rarity = Rarity.UNCOMMON)

        val         rubyGem = corundumFamily.newGem(rarity = Rarity.COMMON)
        val     sapphireGem = corundumFamily.newGem()
    }
}