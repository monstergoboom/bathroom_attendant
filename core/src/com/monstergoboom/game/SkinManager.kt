package com.monstergoboom.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Disposable
import org.koin.core.annotation.Single

enum class Theme {
    DEFAULT,
    CLEAN,
    GRIMY,
    LUXURY
}

@Single
class SkinManager : Disposable {

    private data class ThemeColors(
        val background: Color,
        val backgroundDark: Color,
        val primary: Color,
        val primaryHover: Color,
        val primaryPressed: Color,
        val text: Color,
        val textMuted: Color,
        val accent: Color,
        val danger: Color,
        val success: Color
    )

    private val themeColors = mapOf(
        Theme.DEFAULT to ThemeColors(
            background     = Color(0.2f,  0.2f,  0.2f,  0.95f),
            backgroundDark = Color(0.15f, 0.15f, 0.15f, 1f),
            primary        = Color(0.3f,  0.5f,  0.7f,  1f),
            primaryHover   = Color(0.4f,  0.6f,  0.8f,  1f),
            primaryPressed = Color(0.2f,  0.4f,  0.6f,  1f),
            text           = Color.WHITE,
            textMuted      = Color.LIGHT_GRAY,
            accent         = Color(0.9f,  0.7f,  0.2f,  1f),
            danger         = Color(0.8f,  0.2f,  0.2f,  1f),
            success        = Color(0.2f,  0.7f,  0.3f,  1f)
        ),
        Theme.CLEAN to ThemeColors(
            background     = Color(0.95f, 0.95f, 0.97f, 0.95f),
            backgroundDark = Color(0.85f, 0.85f, 0.87f, 1f),
            primary        = Color(0.2f,  0.6f,  0.9f,  1f),
            primaryHover   = Color(0.3f,  0.7f,  1f,   1f),
            primaryPressed = Color(0.1f,  0.5f,  0.8f,  1f),
            text           = Color(0.1f,  0.1f,  0.1f,  1f),
            textMuted      = Color.GRAY,
            accent         = Color(0.1f,  0.7f,  0.7f,  1f),
            danger         = Color(0.9f,  0.3f,  0.3f,  1f),
            success        = Color(0.2f,  0.8f,  0.4f,  1f)
        ),
        Theme.GRIMY to ThemeColors(
            background     = Color(0.15f, 0.12f, 0.1f,  0.95f),
            backgroundDark = Color(0.1f,  0.08f, 0.05f, 1f),
            primary        = Color(0.5f,  0.4f,  0.2f,  1f),
            primaryHover   = Color(0.6f,  0.5f,  0.3f,  1f),
            primaryPressed = Color(0.4f,  0.3f,  0.15f, 1f),
            text           = Color(0.9f,  0.85f, 0.7f,  1f),
            textMuted      = Color(0.6f,  0.55f, 0.45f, 1f),
            accent         = Color(0.7f,  0.5f,  0.1f,  1f),
            danger         = Color(0.7f,  0.2f,  0.15f, 1f),
            success        = Color(0.3f,  0.5f,  0.2f,  1f)
        ),
        Theme.LUXURY to ThemeColors(
            background     = Color(0.1f,  0.08f, 0.15f, 0.95f),
            backgroundDark = Color(0.05f, 0.03f, 0.08f, 1f),
            primary        = Color(0.6f,  0.5f,  0.8f,  1f),
            primaryHover   = Color(0.7f,  0.6f,  0.9f,  1f),
            primaryPressed = Color(0.5f,  0.4f,  0.7f,  1f),
            text           = Color(0.95f, 0.9f,  1f,   1f),
            textMuted      = Color(0.7f,  0.65f, 0.75f, 1f),
            accent         = Color(0.85f, 0.7f,  0.3f,  1f),
            danger         = Color(0.8f,  0.25f, 0.35f, 1f),
            success        = Color(0.3f,  0.75f, 0.5f,  1f)
        )
    )

    private val skins = mutableMapOf<Theme, Skin>()
    private val fonts = mutableMapOf<String, BitmapFont>()
    private var currentTheme = Theme.DEFAULT
    private var sharedAtlas: TextureAtlas? = null
    private var baseTexture: Texture? = null

    val skin: Skin
        get() = skins.getOrPut(currentTheme) { loadSkin(currentTheme) }

    val theme: Theme
        get() = currentTheme

    fun setTheme(theme: Theme) {
        currentTheme = theme
    }

    fun getFont(name: String): BitmapFont? = fonts[name]

    fun getFont(name: String, default: BitmapFont): BitmapFont = fonts[name] ?: default

    fun registerFont(name: String, font: BitmapFont) {
        fonts[name] = font
    }

    fun loadFont(name: String, path: String, size: Int, color: Color = Color.WHITE): BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal(path))
        val params = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            this.size = size
            this.color = color
            this.borderWidth = 0f
            this.shadowOffsetX = 0
            this.shadowOffsetY = 0
        }
        val font = generator.generateFont(params)
        generator.dispose()
        fonts[name] = font
        return font
    }

    fun registerSkin(theme: Theme, skin: Skin) {
        skins[theme] = skin
    }

    private fun tintedNinePatch(regionName: String, color: Color, split: Int): NinePatchDrawable {
        val np = NinePatch(sharedAtlas!!.findRegion(regionName), split, split, split, split)
        np.color = color.cpy()
        return NinePatchDrawable(np)
    }

    private fun flatDrawable(skin: Skin, color: Color): Drawable =
        skin.newDrawable("white", color)

    private fun makeFreeTypeFont(path: String, size: Int): BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal(path))
        val params = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            this.size = size
        }
        val font = generator.generateFont(params)
        generator.dispose()
        return font
    }

    private fun loadSkin(theme: Theme): Skin {
        // Load shared atlas once — all themes share the same Kenney panel images
        if (sharedAtlas == null)
            sharedAtlas = TextureAtlas(Gdx.files.internal("ui/ui.atlas"))

        val skin = Skin(sharedAtlas)
        val c = themeColors[theme]!!

        // 1×1 white pixel for flat drawables (cursor, text selection)
        if (baseTexture == null) {
            val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
            pixmap.setColor(Color.WHITE)
            pixmap.fill()
            baseTexture = Texture(pixmap)
            pixmap.dispose()
        }
        skin.add("white", baseTexture)

        // LUXURY uses 96×96 Double panels; all others use 48×48 Default panels.
        // Split values are sized to the element role, not just the panel size:
        //   dialogSplit=20 — large panels, generous border keeps content away from decoration
        //   buttonSplit=12 — smaller elements (buttons, fields); 50px button - 2×12 = 26px for text
        val isLuxury    = theme == Theme.LUXURY
        val dialogPanel = if (isLuxury) "panel-double"        else "panel-default"
        val buttonPanel = if (isLuxury) "panel-double-simple" else "panel-simple"
        val dialogSplit = if (isLuxury) 20 else 10
        val buttonSplit = if (isLuxury) 12 else 10

        // Named drawables — used directly in style objects below
        val windowBg         = tintedNinePatch(dialogPanel,    c.background,                            dialogSplit)
        val buttonUp         = tintedNinePatch(buttonPanel,    c.primary,                               buttonSplit)
        val buttonOver       = tintedNinePatch(buttonPanel,    c.primaryHover,                          buttonSplit)
        val buttonDown       = tintedNinePatch(buttonPanel,    c.primaryPressed,                        buttonSplit)
        val dangerUp         = tintedNinePatch(buttonPanel,    c.danger,                                buttonSplit)
        val dangerOver       = tintedNinePatch(buttonPanel,    c.danger.cpy().lerp(Color.WHITE, 0.2f),  buttonSplit)
        val dangerDown       = tintedNinePatch(buttonPanel,    c.danger.cpy().lerp(Color.BLACK, 0.2f),  buttonSplit)
        val successUp        = tintedNinePatch(buttonPanel,    c.success,                               buttonSplit)
        val successOver      = tintedNinePatch(buttonPanel,    c.success.cpy().lerp(Color.WHITE, 0.2f), buttonSplit)
        val successDown      = tintedNinePatch(buttonPanel,    c.success.cpy().lerp(Color.BLACK, 0.2f), buttonSplit)
        val fieldBg          = tintedNinePatch(buttonPanel,    c.backgroundDark,                        buttonSplit)
        val fieldFocused     = tintedNinePatch(buttonPanel,    c.background,                            buttonSplit)
        val listBg           = tintedNinePatch("panel-hollow", c.backgroundDark,                        buttonSplit)
        val listSelection    = tintedNinePatch(buttonPanel,    c.primary,                               buttonSplit)
        val scrollBg         = tintedNinePatch("panel-hollow", c.backgroundDark,                        buttonSplit)
        val scrollKnob       = tintedNinePatch(buttonPanel,    c.primary,                               buttonSplit)
        val cursor           = flatDrawable(skin, c.accent)
        val selection        = flatDrawable(skin, c.primary.cpy().apply { a = 0.5f })

        // Fonts — FreeType at native pixel sizes (sharp at all resolutions; no upscale blur)
        val defaultFont = makeFreeTypeFont("fonts/Oswald-VariableFont_wght.ttf", 22)
        val smallFont   = makeFreeTypeFont("fonts/Oswald-VariableFont_wght.ttf", 16)
        val titleFont   = makeFreeTypeFont("fonts/Acme-Regular.ttf", 32)
        skin.add("default-font", defaultFont)
        skin.add("small-font",   smallFont)
        skin.add("title-font",   titleFont)
        fonts.forEach { (name, font) -> skin.add(name, font) }

        // Window / Dialog style
        skin.add("default", Window.WindowStyle().apply {
            this.titleFont      = titleFont
            this.titleFontColor = c.text.cpy()
            this.background     = windowBg
        })

        // Label styles
        skin.add("default", Label.LabelStyle(defaultFont, c.text.cpy()))
        skin.add("title",   Label.LabelStyle(titleFont,   c.text.cpy()))
        skin.add("small",   Label.LabelStyle(smallFont,   c.textMuted.cpy()))
        skin.add("accent",  Label.LabelStyle(defaultFont, c.accent.cpy()))
        skin.add("danger",  Label.LabelStyle(defaultFont, c.danger.cpy()))
        skin.add("success", Label.LabelStyle(defaultFont, c.success.cpy()))

        // TextButton styles
        skin.add("default", TextButton.TextButtonStyle().apply {
            font      = defaultFont
            fontColor = c.text.cpy()
            up        = buttonUp
            over      = buttonOver
            down      = buttonDown
        })
        skin.add("danger", TextButton.TextButtonStyle().apply {
            font      = defaultFont
            fontColor = c.text.cpy()
            up        = dangerUp
            over      = dangerOver
            down      = dangerDown
        })
        skin.add("success", TextButton.TextButtonStyle().apply {
            font      = defaultFont
            fontColor = c.text.cpy()
            up        = successUp
            over      = successOver
            down      = successDown
        })

        // TextField style
        skin.add("default", TextField.TextFieldStyle().apply {
            font              = defaultFont
            fontColor         = c.text.cpy()
            background        = fieldBg
            focusedBackground = fieldFocused
            this.cursor       = cursor
            this.selection    = selection
        })

        // List style
        skin.add("default", List.ListStyle().apply {
            font                 = defaultFont
            fontColorSelected    = c.text.cpy()
            fontColorUnselected  = c.textMuted.cpy()
            this.selection       = listSelection
            background           = listBg
        })

        // ScrollPane style
        skin.add("default", ScrollPane.ScrollPaneStyle().apply {
            background  = scrollBg
            vScrollKnob = scrollKnob
            hScrollKnob = scrollKnob
        })

        return skin
    }

    override fun dispose() {
        skins.values.forEach { it.dispose() }
        skins.clear()
        sharedAtlas?.dispose()
        sharedAtlas = null
        baseTexture?.dispose()
        baseTexture = null
        fonts.values.forEach { it.dispose() }
        fonts.clear()
    }
}
