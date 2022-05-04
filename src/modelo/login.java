
package modelo;


public class login {
    private  int id;
    private String identificacion;
    private String nombre;
    private String usuario;
    private String pass;
    private String roll;

    public login() {
    }

    public login(int id,String identificacion, String nombre, String usuario, String pass,String roll) {
        this.id = id;
        this.identificacion=identificacion;
        this.nombre = nombre;
        this.usuario = usuario;
        this.pass = pass;
        this.roll=roll;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

  

   
    
}
