
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    
    conexion cn;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean Registrarproveedor( Proveedor pr){
    
    con=conexion.conectando();
        try {
            
            ps=con.prepareStatement(" INSERT INTO proveedor(rut,nombre,telefono,direccion,razon) VALUE (?,?,?,?,?)");
            ps.setInt(1, pr.getRut());
            ps.setString(2,pr.getNombre());
            ps.setString(3,pr.getTelefono());
            ps.setString(4,pr.getDireccion());
            ps.setString(5,pr.getRazon());
            ps.execute();
            return true;
            
            
        } catch (Exception e) {
            
            System.out.println(e.toString());
            return false;
        }finally{
        
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        
        }
    
    
    }
    
    
    public List listarProveedor(){
    List<Proveedor>Listarpr= new ArrayList();
        try {
            con=conexion.conectando();
         ps=con.prepareStatement("SELECT *FROM  proveedor");
         rs=ps.executeQuery();
         
          while (rs.next()) {
              Proveedor pr =new Proveedor();
             pr.setId(rs.getInt("id"));
             pr.setRut(rs.getInt("rut"));
             pr.setNombre(rs.getString("nombre"));
             pr.setTelefono(rs.getString("telefono"));
             pr.setDireccion(rs.getString("direccion"));
             pr.setRazon(rs.getString("razon"));
             Listarpr.add(pr);
         }
        } catch (Exception e) {
            System.out.println(e.toString());
            
            
        }
    return Listarpr;
    
    }
    
    
    
    public boolean EliminarProveedor(int id){
     
     con=conexion.conectando();
     try {
        
         
        ps=con.prepareStatement("DELETE  FROM  proveedor WHERE id=?");
        ps.setInt(1, id);
        ps.execute();
        return  true;
    } catch (Exception e) {
        System.out.println(e.toString());
     return false;
    } finally{
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
     }
    }


    
   public boolean ModificarProveedor(Proveedor pr){
   con=conexion.conectando();
       
       try {
         con=conexion.conectando();
         ps=con.prepareStatement("UPDATE proveedor SET  rut=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=? ");
         ps.setInt(1, pr.getRut());
         ps.setString(2,pr.getNombre());
         ps.setString(3,pr.getTelefono());
         ps.setString(4,pr.getDireccion());
         ps.setString(5,pr.getRazon());
         ps.setInt(6,pr.getId());
         ps.execute();
         return true;
         
           
       } catch (Exception e) {
           
           System.out.println(e.toString());
        return false;
   
   }finally{
           
           
           try {
               con.close();
           } catch (Exception e) {
               System.out.println(e.toString());
               
           }
       }
   


   }

public Proveedor BuscarProveedor( int rut){



  
     
        Proveedor prove=new Proveedor();
        
          try {
              con=conexion.conectando();
               ps=con.prepareStatement("SELECT * FROM proveedor WHERE rut=?");
               ps.setInt(1, rut);
               rs=ps.executeQuery();
               if (rs.next()) {
                  
              prove.setNombre(rs.getString("nombre"));
              prove.setTelefono(rs.getString("telefono"));
              prove.setDireccion(rs.getString("direccion"));
              prove.setRazon(rs.getString("razon"));
               } 
               
          } catch (Exception e) {
              System.out.println(e.toString());
          }
        return prove;
      


}
        
}
