
package modelo;


public class config {
    
    private  int id;
    private String nombre_empresa;
    private int rut;
    private  String telefono;
    private  String direccion;
    
    private  String razon;

    public config() {
    }

    public config(int id, String nombre_empresa, int rut, String direccion, String telefono, String razon) {
        this.id = id;
        this.nombre_empresa = nombre_empresa;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.razon = razon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public int getRut() {
        return rut;
    }

    public void setRuc(int ruc) {
        this.rut = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }
    

}