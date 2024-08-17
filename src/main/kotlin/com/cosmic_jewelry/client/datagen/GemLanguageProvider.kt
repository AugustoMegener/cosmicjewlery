package com.cosmic_jewelry.client.datagen

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class GemLanguageProvider(modId: String, output: PackOutput,  locale: String) : LanguageProvider(output, modId, locale) {
    override fun addTranslations() {
        // No.
    }
}