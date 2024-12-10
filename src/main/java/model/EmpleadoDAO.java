package model;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public static final String COL_ID = "ID";
    public static final String COL_NOMBRE = "NOMBRE";
    public static final String COL_EDAD = "EDAD";
    public static final String COL_DEPARTAMENTO_ID = "DPTO_ID";

    private Connection conn;

    public EmpleadoDAO() {
        conn = DB.getConnection();
    }

    public Empleado getEmpleadoById(int id) {
        try {
            String sql = "SELECT * FROM empleados WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Empleado(
                        rs.getInt(COL_ID),
                        rs.getString(COL_NOMBRE),
                        rs.getInt(COL_EDAD),
                        rs.getInt(COL_DEPARTAMENTO_ID)
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Empleado> getEmpleadosByColumnOrderBy(EmpleadoCol column, String value, boolean order) {
        try {
            String sql;
            PreparedStatement stmt;

            switch (column) {
                case COL_ID -> {
                    sql = order ? "SELECT * FROM empleados WHERE id=?"
                                : "SELECT * FROM empleados WHERE id=? ORDER BY id DESC";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(value));
                }
                case COL_NOMBRE -> {
                    sql = order ? "SELECT * FROM empleados WHERE nombre=?"
                                : "SELECT * FROM empleados WHERE nombre=? ORDER BY id DESC";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, value);
                }
                case COL_EDAD -> {
                    sql = order ? "SELECT * FROM empleados WHERE edad=?"
                                : "SELECT * FROM empleados WHERE edad=? ORDER BY id DESC";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(value));
                }
                case COL_DPTO_ID -> {
                    sql = order ? "SELECT * FROM empleados WHERE dpto_id=?"
                                : "SELECT * FROM empleados WHERE dpto_id=? ORDER BY id DESC";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(value));
                }
                default -> {
                    System.out.println("[ERR]: Columna no encontrada");
                    return null;
                }
            }

            ResultSet rs = stmt.executeQuery();
            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
                empleados.add(
                        new Empleado(
                                rs.getInt(COL_ID),
                                rs.getString(COL_NOMBRE),
                                rs.getInt(COL_EDAD),
                                rs.getInt(COL_DEPARTAMENTO_ID)
                        )
                );
            }

            if (empleados.isEmpty()) {
                return null;
            }
            return empleados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Empleado> getAllEmpleados() {
        try {
            String sql = "SELECT * FROM empleados";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
                empleados.add(
                        new Empleado(
                                rs.getInt(COL_ID),
                                rs.getString(COL_NOMBRE),
                                rs.getInt(COL_EDAD),
                                rs.getInt(COL_DEPARTAMENTO_ID)
                        )
                );
            }
            return empleados;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addEmpleado(Empleado empleado) {
        try {
            String sql = "INSERT INTO empleados (nombre, edad, dpto_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, empleado.getNombre());
            stmt.setInt(2, empleado.getEdad());
            stmt.setInt(3, empleado.getDepartamentoId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateEmpleado(Empleado empleado) {
        try {
            String sql = "UPDATE empleados SET nombre=?, edad=?, dpto_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, empleado.getNombre());
            stmt.setInt(2, empleado.getEdad());
            stmt.setInt(3, empleado.getDepartamentoId());
            stmt.setInt(4, empleado.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteEmpleadoById(int id) {
        try {
            String sql = "DELETE FROM empleados WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
