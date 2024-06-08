package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.world.inventory.menu.LappingTableMenu
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object MenuTypeRegistry {
    val menuTypes = DeferredRegister.create(Registries.MENU, ID)

    val lappingTableMenu by menuTypes.register("lapping_table") { ->
        IMenuTypeExtension.create { windowId, inv, data -> LappingTableMenu(windowId, inv, data) }
    }
}