package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;

import static com.nicolas.tetris.config.TetrisConfig.*;

public class ScoreUI extends UIComponent {

    private final BitmapFont labelFont;
    private final BitmapFont valueFont;

    private final Vector2 scoreLabelPos, linesLabelPos, levelLabelPos,
            scoreValuePos, linesValuePos, levelValuePos;

    private int scoreValue;
    private Integer linesValue;
    private Integer levelValue;

    public ScoreUI() {
        super(new Vector2((STATS_UI_COLS + BOARD_COLS) * (CELL_SIZE * TEXTURE_SCALE), 0),
                SCORE_UI_COLS, SCORE_UI_ROWS);
        scoreValue = 0;
        linesValue = 0;
        levelValue = 0;
        int padding = 10;

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
        scoreLabelPos = new Vector2(getX(labelLayout), getBottomLeft().y + getSize().y - padding);
        scoreValuePos = new Vector2(getX(valueLayout), scoreLabelPos.y - labelLayout.height - padding);

        valueLabel = getSixDigitNumber(levelValue);
        labelLayout = new GlyphLayout(labelFont, LEVEL_LABEL);
        valueLayout = new GlyphLayout(valueFont, valueLabel);
        levelLabelPos = new Vector2(getX(labelLayout), scoreValuePos.y - valueLayout.height - padding);
        levelValuePos = new Vector2(getX(valueLayout), levelLabelPos.y - labelLayout.height - padding);

        valueLabel = getSixDigitNumber(linesValue);
        labelLayout = new GlyphLayout(labelFont, LINES_LABEL);
        valueLayout = new GlyphLayout(valueFont, valueLabel);
        linesLabelPos = new Vector2(getX(labelLayout), levelValuePos.y - valueLayout.height - padding);
        linesValuePos = new Vector2(getX(valueLayout), linesLabelPos.y - labelLayout.height - padding);
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

    @Override
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
}
