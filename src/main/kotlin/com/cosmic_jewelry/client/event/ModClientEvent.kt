package com.cosmic_jewelry.client.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.client.datagen.MaterialBlockStateProvider
import com.cosmic_jewelry.client.datagen.MaterialItemModelProvider
import com.cosmic_jewelry.client.gui.screens.inventory.LappingTableScreen
import com.cosmic_jewelry.client.renderer.blockentity.BuriedGemRenderer
import com.cosmic_jewelry.common.registry.BlockEntityTypeRegistry.buriedGemBlockEntityType
import com.cosmic_jewelry.common.registry.MenuTypeRegistry.lappingTableMenu
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ModClientEvent {

    @SubscribeEvent
    fun onRegisterMenuScreen(event: RegisterMenuScreensEvent) {
        event.register(lappingTableMenu) { m, i, n -> LappingTableScreen(m, i, n) }
    }

    @SubscribeEvent
    fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(buriedGemBlockEntityType) { BuriedGemRenderer }
    }

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        with(event.generator) {

            addProvider(event.includeClient(), MaterialBlockStateProvider(ID, packOutput, event.existingFileHelper))
            addProvider(event.includeClient(),  MaterialItemModelProvider(ID, packOutput, event.existingFileHelper))
        }
    }
}