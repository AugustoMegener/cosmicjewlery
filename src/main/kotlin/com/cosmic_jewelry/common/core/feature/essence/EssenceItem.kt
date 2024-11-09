package com.cosmic_jewelry.common.core.feature.essence

import com.cosmic_jewelry.common.core.feature.MaterialItem
import com.cosmic_jewelry.common.core.material.essence.Essence
import com.cosmic_jewelry.common.core.util.ClassRegister
import com.cosmic_jewelry.common.core.util.UniversalTag
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider

class EssenceItem(name: String,
                  override val featureBuilder: (Essence) -> Item,
                  override val dataGen: ItemModelProvider.(Essence, Item) -> Unit = { _, i -> basicItem(i) },
                  tags: List<UniversalTag> = listOf(),
                  essenceSymbol: String = "#", ) : MaterialItem<Essence>(name, tags, essenceSymbol)
{
    init { all += this }

    companion object : ClassRegister<EssenceItem>()
}