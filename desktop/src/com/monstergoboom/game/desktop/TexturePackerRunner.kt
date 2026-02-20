package com.monstergoboom.game.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

private val KENNEY_ROOT = File("C:/Users/alekm/Downloads/kenney_fantasy-ui-borders")

/**
 * Maps destination filename (becomes the atlas region name) to a source path
 * relative to KENNEY_ROOT.
 *
 * Panel sizes:
 *   Default panels  — 48×48px  (used by DEFAULT, CLEAN, GRIMY themes)
 *   Double panels   — 96×96px  (used by LUXURY theme — thicker, bolder border)
 *   Dividers        — 96×22px  (available for future use in dialog content)
 */
private val ASSET_MAP = mapOf(
    "panel-default.png"       to "PNG/Default/Panel/panel-000.png",
    "panel-simple.png"        to "PNG/Default/Panel/panel-001.png",
    "panel-hollow.png"        to "PNG/Default/Transparent center/panel-transparent-center-000.png",
    "panel-double.png"        to "PNG/Double/Panel/panel-000.png",
    "panel-double-simple.png" to "PNG/Double/Panel/panel-001.png",
    "divider.png"             to "PNG/Default/Divider/divider-000.png",
    "divider-fade.png"        to "PNG/Default/Divider Fade/divider-fade-000.png"
)

private fun copyKenneyAssets(srcRoot: File, destDir: File) {
    for ((destName, srcRelPath) in ASSET_MAP) {
        val src  = File(srcRoot, srcRelPath)
        val dest = File(destDir, destName)
        if (!src.exists()) {
            println("  WARNING: Source not found: ${src.absolutePath}")
            continue
        }
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
        println("  Copied: $destName  (${src.length()} bytes)")
    }
}

fun main() {
    val root   = File(".")
    val srcDir = File(root, "android/assets/ui-src")
    val outDir = File(root, "android/assets/ui")

    if (!KENNEY_ROOT.exists()) {
        println("ERROR: Kenney asset pack not found at ${KENNEY_ROOT.absolutePath}")
        println("Download 'Fantasy UI Borders' from https://kenney.nl and place it there.")
        return
    }

    // Clear and recreate srcDir so stale files don't bleed into the atlas
    srcDir.deleteRecursively()
    srcDir.mkdirs()
    outDir.mkdirs()

    println("Copying Kenney assets...")
    copyKenneyAssets(KENNEY_ROOT, srcDir)

    println("\nPacking atlas...")
    val settings = TexturePacker.Settings().apply {
        combineSubdirectories = false
        paddingX    = 2
        paddingY    = 2
        edgePadding = true
        filterMin   = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
        filterMag   = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
    }

    TexturePacker.process(settings, srcDir.absolutePath, outDir.absolutePath, "ui")
    println("\nDone. Atlas written to ${outDir.absolutePath}/ui.atlas")
    println("Run the game to see the Kenney-based skins.")
}
