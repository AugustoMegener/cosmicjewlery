package com.cosmic_jewelry.client.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.client.datagen.GemBlockStateProvider
import com.cosmic_jewelry.client.datagen.GemItemModelProvider
import com.cosmic_jewelry.client.gui.screens.inventory.LappingTableScreen
import com.cosmic_jewelry.common.registry.MenuTypeRegistry.lappingTableMenu
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ModClientEvent {

    @SubscribeEvent
    fun onRegisterMenuScreen(event: RegisterMenuScreensEvent) {
        event.register(lappingTableMenu) { m, i, n -> LappingTableScreen(m, i, n) }
    }

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        with(event.generator) {

            addProvider(event.includeClient(), GemBlockStateProvider(ID, packOutput, event.existingFileHelper))
            addProvider(event.includeClient(),  GemItemModelProvider(ID, packOutput, event.existingFileHelper))
        }
    }
}