package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.datagen.*
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent


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
                GemLootTableProvider(packOutput, event.lookupProvider)
            )
            addProvider(
                event.includeServer(),
                GemTagProvider(ID, packOutput, event.lookupProvider, event.existingFileHelper)
            )

            addProvider(
                event.includeServer(),
                DatapackBuiltinEntriesProvider(
                    packOutput,
                    event.lookupProvider,
                    RegistrySetBuilder().add(PLACED_FEATURE,     OreGenProvider                 ::bootstrap)
                                        .add(CONFIGURED_FEATURE, ConfiguredFeatureOreGenProvider::bootstrap),
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