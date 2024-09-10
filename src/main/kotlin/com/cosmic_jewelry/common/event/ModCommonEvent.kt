package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.material.feature.MaterialBlock
import com.cosmic_jewelry.common.core.material.feature.MaterialItem
import com.cosmic_jewelry.common.datagen.*
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIERS


@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD)
object ModCommonEvent {

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        with(event.generator) {
            addProvider(
                event.includeServer(),
                GemRecipeProvider(packOutput, event.lookupProvider)
            )
            addProvider(
                event.includeServer(),
                MaterialLootTableProvider(packOutput, event.lookupProvider)
            )

            addProvider(
                event.includeServer(),
                FeatureTagsProvider(ID, packOutput, event.lookupProvider, event.existingFileHelper,
                                    BuiltInRegistries.BLOCK, MaterialBlock)
            )
            addProvider(
                event.includeServer(),
                FeatureTagsProvider(ID, packOutput, event.lookupProvider, event.existingFileHelper,
                                    BuiltInRegistries.ITEM, MaterialItem)
            )

            addProvider(
                event.includeServer(),
                DatapackBuiltinEntriesProvider(
                    packOutput,
                    event.lookupProvider,
                    RegistrySetBuilder().add(PLACED_FEATURE,     OreGenProvider                 ::bootstrap)
                                        .add(CONFIGURED_FEATURE, ConfiguredFeatureOreGenProvider::bootstrap)
                                        .add(BIOME_MODIFIERS,    BiomeModifierProvider          ::bootstrap),
                    mutableSetOf(ID)
                )
            )
        }
    }

    @SubscribeEvent
    fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, lappingTableBlockEntityType) { b, _ -> b.inventory }
    }
}