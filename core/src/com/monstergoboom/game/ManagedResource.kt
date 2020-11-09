package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource
import org.slf4j.LoggerFactory.getLogger

class ManagedResource {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = getLogger(javaClass.enclosingClass)
    }

    var resources = mutableListOf<Resource>()

    fun register(resource: Resource) {
        resources.add(resource)
    }

    suspend fun load() {
        logger.debug("Resources", "Loading Resources...");
        resources.forEach {
            r -> r.load()
            logger.debug("Resources", "Complete: {0}".format(r.getStatusComplete()));
        }
        logger.debug("Resources", "...Completed Resource Load");
    }
}