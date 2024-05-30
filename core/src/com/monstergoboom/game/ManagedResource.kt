package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource
import io.github.oshai.kotlinlogging.KotlinLogging
import org.koin.core.annotation.Single

@Single
class ManagedResource {
    private val log = KotlinLogging.logger {}

    private var resources = mutableListOf<Resource>()

    fun register(resource: Resource) {
        resources.add(resource)
    }

    suspend fun load() {
        log.info { "Loading All Resources..." }
        resources.forEach { r ->
            log.info { "Loading -> %s".format(r.javaClass.simpleName) }
            r.load()
            log.info { "7" }
        }
        log.info { "...Completed All Resource Loading." }
    }
}