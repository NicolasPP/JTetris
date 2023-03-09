package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.HashMap;

import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.STATS_FONT_SIZE;

public class StatsUI extends UIComponent {

    private final HashMap<CellType, Integer> stats = new HashMap<>();
    private final HashMap<CellType, BitmapFont> fonts = new HashMap<>();
    private final HashMap<CellType, Vector2> positions;
    private final HashMap<CellType, Vector2> fontPositions;

    private float totalHeight;

    private final ResourceManager.Fonts fontName;

    public StatsUI() {
        super(new Vector2(0, 0), STATS_UI_COLS, STATS_UI_ROWS);
        fontName = ResourceManager.Fonts.PIXEL;
        positions = new HashMap<>();
        fontPositions = new HashMap<>();
        init();
    }

    private void init(){
        populateStats();
        populateFonts();

        float rowCount = 0f;
        for (CellType cellType : stats.keySet()){
            if (cellType == CellType.I) rowCount++; else rowCount +=2;
        }

        totalHeight = rowCount * (CELL_SIZE * TEXTURE_SCALE);

        updatePositions();

    }

    public void updatePositions(){
        positions.clear();
        fontPositions.clear();
        Vector2 currentPos = new Vector2(getBottomLeft());
        float vertSpacing = (getSize().y - totalHeight) / (stats.size() + 1);

        for(CellType cellType : stats.keySet()){
            TetrominoSprite tetromino = TetrominoSprite.get(cellType);
            float tetrominoW = tetromino.getTextureSize().x;
            float tetrominoH = tetromino.getTextureSize().y;

            float horzSpacing = (getSize().x - tetrominoW) / 2;

            currentPos.x = getBottomLeft().x + horzSpacing;
            currentPos.y += vertSpacing;

            if (cellType == CellType.I) {
                currentPos.x = getBottomLeft().x;
                tetrominoH = tetromino.getTextureSize().x;
                tetrominoW = tetromino.getTextureSize().y;
            }

            Vector2 fontPos = new Vector2(currentPos);
            fontPos.x += (tetrominoW/2);
            fontPos.y += (tetrominoH/2);

            final GlyphLayout layout = new GlyphLayout(fonts.get(cellType), stats.get(cellType).toString().strip());
            fontPos.x -= layout.width / 2;
            fontPos.y += layout.height / 2;

            positions.put(cellType, new Vector2(currentPos));
            fontPositions.put(cellType, fontPos);

            currentPos.y += tetrominoH;
        }
    }

    private void populateStats(){
        stats.put(CellType.I, 0);
        stats.put(CellType.S, 0);
        stats.put(CellType.Z, 0);
        stats.put(CellType.O, 0);
        stats.put(CellType.T, 0);
        stats.put(CellType.L, 0);
        stats.put(CellType.J, 0);
    }

    private void populateFonts(){
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = STATS_FONT_SIZE;

        parameter.color = TetrominoSprite.get(CellType.I).getColor();
        fonts.put(CellType.I, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.S).getColor();
        fonts.put(CellType.S, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.Z).getColor();
        fonts.put(CellType.Z, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.O).getColor();
        fonts.put(CellType.O, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.T).getColor();
        fonts.put(CellType.T, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.L).getColor();
        fonts.put(CellType.L, ResourceManager.getFont(fontName, parameter));

        parameter.color = TetrominoSprite.get(CellType.J).getColor();
        fonts.put(CellType.J, ResourceManager.getFont(fontName, parameter));
    }
    public void addStat(CellType cellType){
        stats.put(cellType, stats.get(cellType) + 1);
    }
    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
        for(CellType cellType : stats.keySet()){
            boolean rotate = cellType == CellType.I;
            TetrominoSprite tetromino = TetrominoSprite.get(cellType);

            int prevCount = stats.get(cellType) - 1;
            if (stats.get(cellType).toString().strip().length() != Integer.toString(prevCount).length()) updatePositions();

            Vector2 currentPos = positions.get(cellType);
            Vector2 fontPos = fontPositions.get(cellType);
            tetromino.renderTexture(batch, currentPos, rotate);
            tetromino.renderSubTexture(batch, tetromino.getGhostName() ,currentPos, tetromino.getTextureSize(), rotate);
            fonts.get(cellType).draw(batch, stats.get(cellType).toString().strip(), fontPos.x , fontPos.y);
        }
    }
}
