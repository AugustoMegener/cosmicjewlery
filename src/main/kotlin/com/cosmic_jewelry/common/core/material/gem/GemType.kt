package com.cosmic_jewelry.common.core.material.gem

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.corundumFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.olivineFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.quartzFamily
import com.cosmic_jewelry.common.core.material.gem.GemFamily.Companion.silicateFamily
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity

class GemType internal constructor(val family: GemFamily, override val mohs: Float, val rarity: Rarity) : Material<GemType> {

    override val name:  String get() = location.path
    override val owner: String get() = location.namespace

    override val registry = gemTypes

    val location get() = gemTypes.entries.find { it.value == this }!!.key

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