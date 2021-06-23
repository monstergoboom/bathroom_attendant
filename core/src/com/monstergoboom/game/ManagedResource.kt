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
        logger.info("Loading All Resources...")
        resources.forEach { r ->
            logger.info("Loading -> %s".format(r.javaClass.simpleName))
            r.load()
            logger.info("7")
        }
        logger.info("...Completed All Resource Loading.")
    }
}