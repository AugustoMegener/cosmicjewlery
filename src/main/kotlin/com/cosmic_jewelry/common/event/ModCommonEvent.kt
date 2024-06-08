package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.datagen.GemLootTableProvider
import com.cosmic_jewelry.common.datagen.GemRecipeProvider
import com.cosmic_jewelry.common.datagen.GemTagProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
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
        }
    }
}