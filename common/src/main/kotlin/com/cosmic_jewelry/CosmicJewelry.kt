package com.cosmic_jewelry;

import com.cosmic_jewelry.common.registry.BlockRegistry.blocks
import com.cosmic_jewelry.common.registry.ItemRegistry.items
import com.cosmic_jewelry.common.registry.TabRegistry.tabs
import dev.architectury.registry.registries.RegistrarManager

object CosmicJewelry {
    const val ID = "cosmic_jewelry"

    val registryManager: RegistrarManager by lazy { RegistrarManager.get(ID) }


    fun init() {
        listOf(blocks, items, tabs).forEach { it.register() }
    }
}
