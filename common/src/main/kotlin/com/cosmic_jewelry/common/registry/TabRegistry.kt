package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.registry.GemTypeRegistry.peridotGem
import com.cosmic_jewelry.common.registry.ItemRegistry.cutGemItem
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab

object TabRegistry {
    val tabs: DeferredRegister<CreativeModeTab> = DeferredRegister.create(ID, Registries.CREATIVE_MODE_TAB)

    val gemsTab = tabs.register("gem_tab") {
        CreativeTabRegistry.create(Component.translatable("category.$ID.gem")) {
            cutGemItem[peridotGem.get()]!!.defaultInstance
        }
    }
}