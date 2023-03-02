package com.nicolas.tetris.config;

public class TetrisConfig {
    final static public String BG_GRAY_NAME = "BG_gray";
    final static public String BG_BLACK_NAME = "BG_black";
    final static public String BORDER_NAME = "Border";
    final static public String BOARD_NAME = "Board";
    final static public String SHAPE_I_NAME = "shape_I";
    final static public String SHAPE_J_NAME = "shape_J";
    final static public String SHAPE_L_NAME = "shape_L";
    final static public String SHAPE_O_NAME = "shape_O";
    final static public String SHAPE_S_NAME = "shape_S";
    final static public String SHAPE_T_NAME = "shape_T";
    final static public String SHAPE_Z_NAME = "shape_Z";
    final static public String LIGHT_BLUE = "LightBlue";
    final static public String BLUE = "Blue";
    final static public String ORANGE = "Orange";
    final static public String YELLOW = "Yellow";
    final static public String GREEN = "Green";
    final static public String PURPLE = "Purple";
    final static public String RED = "Red";
    final static public String GHOST_SHAPE_I_NAME = "ghost_shape_I";
    final static public String GHOST_SHAPE_J_NAME = "ghost_shape_J";
    final static public String GHOST_SHAPE_L_NAME = "ghost_shape_L";
    final static public String GHOST_SHAPE_O_NAME = "ghost_shape_O";
    final static public String GHOST_SHAPE_S_NAME = "ghost_shape_S";
    final static public String GHOST_SHAPE_T_NAME = "ghost_shape_T";
    final static public String GHOST_SHAPE_Z_NAME = "ghost_shape_Z";
    final static public int[][] SHAPE_I_MAP = {{1, 1, 1, 1}, {0, 0, 0, 0}};
    final static public int[][] SHAPE_J_MAP = {{0, 0, 0, 1}, {1, 1, 1, 1}};
    final static public int[][] SHAPE_L_MAP = {{1, 0, 0, 0}, {1, 1, 1, 1}};
    final static public int[][] SHAPE_O_MAP = {{1, 1, 0, 0}, {1, 1, 0, 0}};
    final static public int[][] SHAPE_S_MAP = {{0, 1, 1, 0}, {1, 1, 0, 0}};
    final static public int[][] SHAPE_T_MAP = {{0, 1, 0, 0}, {1, 1, 1, 0}};
    final static public int[][] SHAPE_Z_MAP = {{1, 1, 0, 0}, {0, 1, 1, 0}};

    final static public float TEXTURE_SCALE = 0.5f;
    final static public int WINDOW_WIDTH = 512;
    final static public int WINDOW_HEIGHT = 704;
    final static public int CELL_SIZE = 64;
    final static public int BOARD_COLS = 12;
    final static public int BOARD_ROWS = 22;
    public final static int SPAWN_ROWS = 4;
    public final static int GRID_ROWS = BOARD_ROWS + 2;
    public final static int GRID_COLS = BOARD_COLS - 2;
}
