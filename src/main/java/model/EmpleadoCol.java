package model;

public enum EmpleadoCol {
    COL_ID (1),
    COL_NOMBRE (2),
    COL_EDAD (3),
    COL_DPTO_ID (4);

    private final int index;

    EmpleadoCol(int index) {
        this.index = index;
    }

    public static EmpleadoCol fromIndex(int index) {
        for (EmpleadoCol col : values()) {
            if (col.index == index) {
                return col;
            }
        }
        return null;
    }
}
