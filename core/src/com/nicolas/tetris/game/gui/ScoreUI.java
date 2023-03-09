package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;

import static com.nicolas.tetris.config.TetrisConfig.SCORE_PADDING;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_FONT_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_VALUE_FONT_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_LABEL;
import static com.nicolas.tetris.config.TetrisConfig.LEVEL_LABEL;
import static com.nicolas.tetris.config.TetrisConfig.LINES_LABEL;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class ScoreUI extends UIComponent {

    private BitmapFont labelFont;
    private BitmapFont valueFont;

    private Vector2 scoreLabelPos, linesLabelPos, levelLabelPos,
            scoreValuePos, linesValuePos, levelValuePos;

    private int scoreValue;
    private Integer linesValue;
    private Integer levelValue;

    public ScoreUI() {
        super(new Vector2((STATS_UI_COLS + BOARD_COLS) * (CELL_SIZE * TEXTURE_SCALE), 0),
                SCORE_UI_COLS, SCORE_UI_ROWS);
        init();
    }

    private void init(){
        updateValues(0, 0, 0);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = SCORE_FONT_SIZE;
        labelFont = ResourceManager.getFont(ResourceManager.Fonts.PIXEL, parameter);
        parameter.size = SCORE_VALUE_FONT_SIZE;
        valueFont = ResourceManager.getFont(ResourceManager.Fonts.PIXEL, parameter);

        GlyphLayout labelLayout, valueLayout;
        String valueLabel;

        valueLabel = getSixDigitNumber(scoreValue);
        labelLayout = new GlyphLayout(labelFont, SCORE_LABEL);
        valueLayout = new GlyphLayout(valueFont, valueLabel);
        scoreLabelPos = new Vector2(getX(labelLayout), getBottomLeft().y + getSize().y - SCORE_PADDING);
        scoreValuePos = new Vector2(getX(valueLayout), scoreLabelPos.y - labelLayout.height - SCORE_PADDING);

        valueLabel = getSixDigitNumber(levelValue);
        labelLayout = new GlyphLayout(labelFont, LEVEL_LABEL);
        valueLayout = new GlyphLayout(valueFont, valueLabel);
        levelLabelPos = new Vector2(getX(labelLayout), scoreValuePos.y - valueLayout.height - SCORE_PADDING);
        levelValuePos = new Vector2(getX(valueLayout), levelLabelPos.y - labelLayout.height - SCORE_PADDING);

        valueLabel = getSixDigitNumber(linesValue);
        labelLayout = new GlyphLayout(labelFont, LINES_LABEL);
        valueLayout = new GlyphLayout(valueFont, valueLabel);
        linesLabelPos = new Vector2(getX(labelLayout), levelValuePos.y - valueLayout.height - SCORE_PADDING);
        linesValuePos = new Vector2(getX(valueLayout), linesLabelPos.y - labelLayout.height - SCORE_PADDING);
    }

    private String getSixDigitNumber(Integer num){
        if (num >= 999999) return "999999";
        if (num <= 0) return "000000";
        int zeroesPrefixSize = 6 - num.toString().length();
        StringBuilder prefix = new StringBuilder();
        while (prefix.length() < zeroesPrefixSize) prefix.append("0");
        prefix.append(num);
        return prefix.toString();
    }

    private float getX(GlyphLayout layout){
        return getBottomLeft().x + (getSize().x - layout.width) / 2;
    }

    public void updateValues(int score, int level, int lines){
        scoreValue = score;
        linesValue = lines;
        levelValue = level;
    }

    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);

        String valueLabel;

        valueLabel = getSixDigitNumber(scoreValue);
        labelFont.draw(batch, SCORE_LABEL, scoreLabelPos.x, scoreLabelPos.y);
        valueFont.draw(batch, valueLabel, scoreValuePos.x, scoreValuePos.y);

        valueLabel = getSixDigitNumber(levelValue);
        labelFont.draw(batch, LEVEL_LABEL, levelLabelPos.x, levelLabelPos.y);
        valueFont.draw(batch, valueLabel, levelValuePos.x, levelValuePos.y);

        valueLabel = getSixDigitNumber(linesValue);
        labelFont.draw(batch, LINES_LABEL, linesLabelPos.x, linesLabelPos.y);
        valueFont.draw(batch, valueLabel, linesValuePos.x, linesValuePos.y);
    }

    public void reset(){
        init();
    }
}
