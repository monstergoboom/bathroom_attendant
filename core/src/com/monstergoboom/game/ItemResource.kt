package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource
import com.monstergoboom.game.models.ItemData
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.yield
import org.koin.core.annotation.Single
import java.util.*

@Single
class ItemResource : Resource {
    private val log = KotlinLogging.logger {}

    private val items: MutableList<ItemData> = mutableListOf()

    private var status: Float = 0f
    private var totalItems: Int = 0
    private var processed: Int = 0

    override suspend fun load(): Boolean {

        log.info { "ItemResource->load() : Started" }

        totalItems = 2

        val hygiene: CategoryType = CategoryType.HYGIENE

        items.add(ItemData.newBuilder()
                .setProduct(ProductType.DEODORANT.ordinal)
                .setId(UUID.randomUUID().toString())
                .setName("Old Spice Classic")
                .setDescription("Reduces underarm odor for 24 hours. The citrus and clove scents " +
                        "of Original are often imitated but never duplicated. Increase your " +
                        "awesomeabilityness. If your Grandfather hadn't worn it, you wouldn't have " +
                        "existed. You know the smell of fear, it's the opposite of that. Increase " +
                        "your ability to sense other dimensions.")
                .build())

        processed++

        yield()

        items.add(ItemData.newBuilder()
                .setCategory(hygiene.ordinal)
                .setProduct(ProductType.MOUTHWASH.ordinal)
                .setId(UUID.randomUUID().toString())
                .setName("Scope Classic Mouthwash")
                .setDescription("Feel the burn inside your mouth. Original forumula.")
                .build())

        processed++

        log.info { "ItemResource->load() : Complete" }
        return true
    }

    override suspend fun getStatusComplete(): Float {
        return (processed.toFloat() / totalItems.toFloat())
    }

    override fun resources(): List<Any> {
        return items
    }

    fun items(): List<ItemData> {
        return items
    }
}
