package com.nicolas.tetris.manager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResourceManager {
    private static TextureAtlas texturesAtlas;

    public static void loadData(){
        texturesAtlas = new TextureAtlas("sprites.txt");
    }
    public static Sprite get(String name){
        return texturesAtlas.createSprite(name);
    }


}
