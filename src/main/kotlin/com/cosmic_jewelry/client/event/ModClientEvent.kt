package com.cosmic_jewelry.client.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.client.datagen.GemBlockStateProvider
import net.neoforged.neoforge.data.event.GatherDataEvent

//@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ModClientEvent {

    //@SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        val a = true
        with(event.generator) {
            addProvider(
                event.includeClient(),
                GemBlockStateProvider(ID, packOutput, event.existingFileHelper)
            )
        }
    }
}