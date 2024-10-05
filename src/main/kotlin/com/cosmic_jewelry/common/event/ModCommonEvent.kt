package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.capability.SpecificFluidHandlerItemStack
import com.cosmic_jewelry.common.core.feature.MaterialBlock
import com.cosmic_jewelry.common.core.feature.MaterialFluidType
import com.cosmic_jewelry.common.core.feature.MaterialItem
import com.cosmic_jewelry.common.core.feature.essence.EssenceItem
import com.cosmic_jewelry.common.datagen.*
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.lappingTableBlockEntityType
import com.cosmic_jewelry.common.registry.DataComponentTypeRegistry.fluidData
import com.cosmic_jewelry.common.registry.FluidTypeRegistry.essenceFluidType
import com.cosmic_jewelry.common.registry.ItemRegistry.essencePipetteItem
import com.cosmic_jewelry.common.world.item.FluidContainerItem
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
import net.minecraft.core.registries.Registries.PLACED_FEATURE
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack
import net.neoforged.neoforge.registries.NeoForgeRegistries
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
                FeatureTagsProvider(ID, packOutput, event.lookupProvider, event.existingFileHelper,
                    NeoForgeRegistries.FLUID_TYPES, MaterialFluidType)
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
    fun RegisterCapabilitiesEvent.onRegisterCapabilities() {
        registerBlockEntity(Capabilities.ItemHandler.BLOCK, lappingTableBlockEntityType) { b, _ -> b.inventory }

        EssenceItem.register
            .flatMap { it.content.keys.zip(it.content.values.filterIsInstance<FluidContainerItem>()) }
            .toMap()
            . forEach { (m, c) ->
            registerItem(
                FluidHandler.ITEM, { i, _ -> SpecificFluidHandlerItemStack(fluidData, i, c.capacity, essenceFluidType[m]!!) }, c)
        }


        listOf(essencePipetteItem).forEach {
            registerItem(FluidHandler.ITEM, { i, _ ->  FluidHandlerItemStack(fluidData, i, it.capacity) }, it) }

    }
}