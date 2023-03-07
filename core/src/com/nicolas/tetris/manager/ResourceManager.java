package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.nicolas.tetris.config.TetrisConfig.*;

public class ResourceManager {
    private static TextureAtlas texturesAtlas;
    private static BitmapFont font;
    public static void loadData() {
        texturesAtlas = new TextureAtlas(SPRITE_TXT_FILE);
        font = generateFont(PIXEL_FONT_TTF);
    }

    public static BitmapFont getFont() {return font;}
    public static TextureRegion getTexture(String name) {
        Sprite sprite = texturesAtlas.createSprite(name);
        return new TextureRegion(
                sprite.getTexture(),
                sprite.getRegionX(),
                sprite.getRegionY(),
                sprite.getRegionWidth(),
                sprite.getRegionHeight()
        );
    }

    private static BitmapFont generateFont(String fontFile){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont generatedFont = generator.generateFont(parameter);
        generator.dispose();
        return generatedFont;
    }
}
