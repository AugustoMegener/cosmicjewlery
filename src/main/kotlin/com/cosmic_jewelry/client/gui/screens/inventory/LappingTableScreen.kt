package com.cosmic_jewelry.client.gui.screens.inventory

import com.cosmic_jewelry.CosmicJewelry.ID
import com.cosmic_jewelry.common.world.inventory.menu.LappingTableMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class LappingTableScreen(pMenu: LappingTableMenu, pPlayerInventory: Inventory, pTitle: Component)
: AbstractContainerScreen<LappingTableMenu>(pMenu, pPlayerInventory, pTitle)
{
    override fun renderBg(pGuiGraphics: GuiGraphics, pPartialTick: Float, pMouseX: Int, pMouseY: Int) {
        pGuiGraphics.blit(background, leftPos, topPos, 0, 0, imageWidth, imageHeight)
    }

    override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick)
        renderTooltip(pGuiGraphics, pMouseX, pMouseY)
    }

    companion object {
        val background = ResourceLocation(ID, "textures/gui/container/lapping_table.png")
    }
}