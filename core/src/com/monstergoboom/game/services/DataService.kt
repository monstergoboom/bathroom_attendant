package com.monstergoboom.game.services

import com.monstergoboom.game.models.DataBlock
import org.koin.core.annotation.Single
import java.sql.DriverManager

@Single
class DataService {
    fun write(data: DataBlock) {
        TODO()
    }
    fun read(data: DataBlock): DataBlock {
        TODO()
    }

    fun open() {
        /*
        CREATE TABLE profile (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT(256) NOT NULL,
            CONSTRAINT profile_pk PRIMARY KEY (id)
        );
        */

        val conn = DriverManager.getConnection("jdbc:sqlite:./data/store")

        val ps = conn.prepareStatement("SELECT * FROM PROFILE")
        val rs = ps.executeQuery()

        while (rs.next()) {
            var id = rs.getInt("id")
            var name = rs.getString("name")

            println(id)
            println(name)
        }

        ps.close()
    }
}
