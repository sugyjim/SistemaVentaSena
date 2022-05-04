package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    conexion cn;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;
int id_venta=0;
    public int IdVenta() {
        con = conexion.conectando();

        try {
            
            ps = con.prepareStatement("SELECT  MAX(id)   FROM venta");
            rs = ps.executeQuery();
            if (rs.next()) {
                id_venta = rs.getInt(1);

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return id_venta;

    }

    public int Registrarvent(Venta v) {

        try {
            con = conexion.conectando();
            ps = con.prepareStatement("INSERT INTO venta(cliente,vendedor,total) VALUE ( ?,?,?) ");
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.toString());

        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            return r;

        }
    }

    public int RegistrarDetalle(DetalleVenta Dv) {
             con = conexion.conectando();
        try {
            
            ps = con.prepareStatement("INSERT INTO detalle(cod_producto,cantidad,precio,id_venta) VALUE (?,?,?,?)");

            ps.setString(1, Dv.getCod_producto());
             ps.setDouble(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId_venta());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }finally{
            
            
                try {
                    con.close();
                    
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }

        }
        return r;
    }
    public boolean ActualizarStock(double cant, String cod){
    
        try {
             con = conexion.conectando();
            ps = con.prepareStatement("UPDATE producto SET stock=? WHERE codigo=?");
            ps.setDouble(1, cant);
            ps.setString(2, cod);
            ps.execute();
            return (true);
        } catch (Exception e) {
            System.out.println(e.toString());
            return (false);
        }
        
    
    }
    
     public List listarventa() {
        List<Venta> Listarventa = new ArrayList();
             con = conexion.conectando();
        try {
            
            ps = con.prepareStatement("SELECT *FROM  venta");
            rs = ps.executeQuery();

            while (rs.next()) {
               Venta vent = new Venta();
                vent.setId(rs.getInt("id"));
                vent.setCliente(rs.getString("cliente"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getDouble("total"));
                
                Listarventa.add(vent);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Listarventa;
    }

}
