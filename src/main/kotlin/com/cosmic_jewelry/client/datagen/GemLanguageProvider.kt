package com.cosmic_jewelry.client.datagen

import com.cosmic_jewelry.common.core.gem.feature.GemBlock
import com.cosmic_jewelry.common.core.gem.feature.GemItem
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class GemLanguageProvider(modid: String, output: PackOutput,  locale: String) : LanguageProvider(output, modid, locale) {
    override fun addTranslations() {
        GemItem.register.forEach { f -> f.featuresMap.forEach { (g, i) ->
            add(i.get(), g.translatableName.string + f.translatableName.string)
        } }
        GemBlock.register.forEach { f -> f.featuresMap.forEach { (g, i) ->
            add(i.get(), g.translatableName.string + f.translatableName.string)
        } }
    }
}