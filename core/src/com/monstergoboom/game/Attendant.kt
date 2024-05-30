package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Playable
import java.util.UUID


class Attendant (
    var name: String,
    var description: String
) : Identifiable, Playable {
    private var identifier: UUID = UUID.randomUUID()

    var attributes: Attributes = Attributes()
    var assets: Assets = Assets()

    override fun id(): String {
        return identifier.toString();
    }
}