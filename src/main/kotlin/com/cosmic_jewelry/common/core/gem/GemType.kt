package com.cosmic_jewelry.common.core.gem

import com.cosmic_jewelry.common.core.gem.GemFamily.Companion.corundumFamily
import com.cosmic_jewelry.common.core.gem.GemFamily.Companion.olivineFamily
import com.cosmic_jewelry.common.core.gem.GemFamily.Companion.quartzFamily
import com.cosmic_jewelry.common.core.gem.GemFamily.Companion.silicateFamily
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity

class GemType internal constructor(val family: GemFamily, val mosh: Float, val rarity: Rarity) {

    val location get() = gemTypes.entries.find { it.value == this }!!.key
    val owner: String get() = location.namespace
    val name:  String get() = location.path

    val translatableName: MutableComponent get() = Component.translatable(location.toLanguageKey())

    companion object {
        val gemTypes = HashMap<ResourceLocation, GemType>()

        val amethystGem     = quartzFamily.newGem()
        val blueQuartzGem   = quartzFamily.newGem()
        val carnelianGem    = quartzFamily.newGem()
        val orangeJasperGem = quartzFamily.newGem()
        val roseQuartzGem   = quartzFamily.newGem()

        val peridotGem      = olivineFamily.newGem()

        val lapisLazuliGem  = silicateFamily.newGem(rarity = Rarity.RARE)

        val rubyGem         = corundumFamily.newGem(rarity = Rarity.COMMON)
        val sapphireGem     = corundumFamily.newGem()

    }
}