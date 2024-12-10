package model;

public class Empleado {

    private int id;
    private String nombre;
    private int edad;
    private int departamentoId;

    public Empleado(int id, String nombre, int edad, int departamentoId) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.departamentoId = departamentoId;
    }

    public Empleado(String nombre, int edad, int departamentoId) {
        this.nombre = nombre;
        this.edad = edad;
        this.departamentoId = departamentoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", departamentoId=" + departamentoId +
                '}';
    }
}
