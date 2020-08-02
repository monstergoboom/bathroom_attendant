package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

class ItemResource: Resource {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    var items = mutableListOf<Item>()

    override var count: AtomicInteger = AtomicInteger(0)
    override var processed: AtomicInteger = AtomicInteger(0)

    override suspend fun load(): Boolean {

        logger.debug("%s", "what")

        count.set(3)
        processed.set(0)

        items.add(Item("1",
                "Gum",
                "Hard to chew and low quality generic gum.",
                1.0f))

        processed.incrementAndGet()

        items.add(Item("2",
                "Mints",
                "cheap restaurant style mints.",
                1.0f))

        processed.incrementAndGet()

        items.add(Item("3",
                "Deodorant Wipes",
                "Single use deodorant wipe.",
                1.0f))

        processed.incrementAndGet()

        return true
    }

    override suspend fun getStatusComplete(): Float {
        return processed.toFloat() / count.toFloat()
    }
}