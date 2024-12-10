package model;

import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    private final String COL_ID = "ID";
    private final String COL_NOMBRE = "NOMBRE";

    private Connection conn;

    public DepartamentoDAO() {
        conn = DB.getConnection();
    }

    public Departamento getDepartamentoById(int id, boolean order) {
        try {
            String sql = order ? "SELECT * FROM departamento WHERE id=?"
                               : "SELECT * FROM departamento WHERE id=? ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Departamento(rs.getInt(COL_ID), rs.getString(COL_NOMBRE));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Departamento> getDepartamentosByNombre(String nombre, boolean order) {
        try {
            String sql = order ? "SELECT * FROM departamento WHERE nombre=?"
                               : "SELECT * FROM departamento WHERE nombre=? ORDER BY nombre DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            List<Departamento> departamentos = new ArrayList<>();
            while (rs.next()) {
                departamentos.add(new Departamento(
                        rs.getInt(COL_ID),
                        rs.getString(COL_NOMBRE))
                );
            }
            return departamentos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Departamento> getAllDepartamentos() {
        try {
            String sql = "SELECT * FROM departamento";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Departamento> departamentos = new ArrayList<>();
            while (rs.next()) {
                departamentos.add(new Departamento(
                        rs.getInt(COL_ID),
                        rs.getString(COL_NOMBRE))
                );
            }

            if (departamentos.isEmpty()) {
                return null;
            }
            return departamentos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addDepartamento(String nombre) {
        try {
            String sql = "INSERT INTO departamento (nombre) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateDepartamento(Departamento departamento) {
        try {
            String sql = "UPDATE departamento SET nombre=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, departamento.getNombre());
            stmt.setInt(2, departamento.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteDepartamentoById(int id) {
        try {
            String sql = "DELETE FROM departamento WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
