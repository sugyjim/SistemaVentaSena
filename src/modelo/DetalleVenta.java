
package modelo;


public class DetalleVenta {
    private int id;
    private String cod_producto;
    private double cantidad;
    private double  precio;
    private int id_venta;

    public DetalleVenta() {
        
    }

    public DetalleVenta(int id, String cod_producto,  double cantidad, double precio, int id_venta) {
        this.id = id;
        this.cod_producto = cod_producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_venta = id_venta;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
    
    
    
    
}
