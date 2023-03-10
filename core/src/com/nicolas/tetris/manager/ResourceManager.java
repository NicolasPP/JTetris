package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static com.nicolas.tetris.config.TetrisConfig.TETRIS_FONT_TTF;
import static com.nicolas.tetris.config.TetrisConfig.PIXEL_FONT_TTF;
import static com.nicolas.tetris.config.TetrisConfig.SPRITE_TXT_FILE;

public class ResourceManager {
    private static TextureAtlas texturesAtlas;
    public enum Fonts{
        PIXEL, TETRIS;

        public String getFile(){
            switch (this){
                case PIXEL: return PIXEL_FONT_TTF;
                case TETRIS: return TETRIS_FONT_TTF;

            }
            return PIXEL_FONT_TTF;
        }
    }
    public static void loadData() {
        texturesAtlas = new TextureAtlas(SPRITE_TXT_FILE);
    }

    public static BitmapFont getFont(Fonts font, FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font.getFile()));
        BitmapFont generatedFont = generator.generateFont(parameter);
        generator.dispose();
        return generatedFont;
    }
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
}
