package com.cosmic_jewelry.common.registry

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.CosmicJewelry.registryManager
import com.cosmic_jewelry.common.core.gem.GemType
import dev.architectury.registry.registries.Registrar
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation


object GemTypeRegistry {

    val registryKey: ResourceKey<Registry<GemType>> =
        ResourceKey.createRegistryKey(ResourceLocation("gem_type"))

    var gemTypes: Registrar<GemType> = registryManager[registryKey]

    val peridotGem = gemTypes.register(ResourceLocation(ID, "peridot")) { GemType(6.5f) }
}