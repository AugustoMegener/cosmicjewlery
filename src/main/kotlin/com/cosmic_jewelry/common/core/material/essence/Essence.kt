package com.cosmic_jewelry.common.core.material.essence

import com.cosmic_jewelry.common.core.material.Material
import com.cosmic_jewelry.common.core.material.gem.GemType
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.blueDiamondGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.pinkDiamondGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.whiteDiamondGem
import com.cosmic_jewelry.common.core.material.gem.GemType.Companion.yellowDiamondGem
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Rarity

sealed class Essence : Material<Essence> {

    override val location get() = essences.entries.find { it.value == this }!!.key

    override val mohs : Float = 0f

    override val registry = essences

    class Base(         val   provider : GemType,
               override val      color : Int,
               override val     rarity : Rarity = Rarity.COMMON)
        : Essence(), IEssence
    { override val type = this }

    data object Composed : Essence() {
        override val rarity = Rarity.COMMON

        class ComposedEssence(val composition: List<Pair<Float, Base>>) : IEssence {
            override val type = Composed

            override val color = composition.map { it.first to it.second.color }
                .fold(floatArrayOf(0f, 0f, 0f)) { acc, (proportion, color) ->
                    acc[0] += (color shr 16 and 0xFF) * proportion
                    acc[1] += (color shr  8 and 0xFF) * proportion
                    acc[2] += (color        and 0xFF) * proportion
                    acc
                }.let { c ->
                    val p = composition.map { it.first }.sum()
                    val r = c.map { (it / p).coerceIn(0f, 255f).toInt()  }

                    (r[0] shl 16) or (r[1] shl 8) or r[2]
                }
        }
    }

    companion object {
        val essences = HashMap<ResourceLocation, Essence>()

        val  whiteEssence = Base( whiteDiamondGem, 0xe3e0db, Rarity.EPIC)
        val yellowEssence = Base(yellowDiamondGem, 0xf8e180, Rarity.RARE)
        val   blueEssence = Base(  blueDiamondGem, 0x429fe5, Rarity.RARE)
        val   pinkEssence = Base(  pinkDiamondGem, 0xf08db8, Rarity.RARE)
    }
}