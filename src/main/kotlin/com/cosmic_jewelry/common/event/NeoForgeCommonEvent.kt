package com.cosmic_jewelry.common.event

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.core.gem.feature.GemItem
import com.mojang.datafixers.util.Either
import net.minecraft.network.chat.Component
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RenderTooltipEvent

@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.GAME)
object NeoForgeCommonEvent {
    @SubscribeEvent
    fun onRenderTooltip(event: RenderTooltipEvent.GatherComponents) {
        GemItem.register .filter  { it.doLapping }
            .flatMap { it.featuresMap.entries }
            .find    { event.itemStack.item == it.value.get() }?.key
            ?.also    { event.tooltipElements += Either.left(
                Component.literal(("Mohs: ")).withColor(0xffbb00).append("${it.mosh}")) }
    }
}