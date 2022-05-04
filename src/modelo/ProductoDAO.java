 package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ProductoDAO {

    conexion cn;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarProducto(Producto pro) {

        con = conexion.conectando();
        try {

            ps = con.prepareStatement("INSERT INTO producto(codigo,descripcion,proveedor,stock,precio)VALUE(?,?,?,?,?)");
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getDescripcion());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.execute();
            return (true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return (false);
        } finally {

            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    }

    public void ConsultarProveedor(JComboBox proveedor) {
        String sql="SELECT nombre FROM proveedor";
        try {

              con = conexion.conectando();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public List listarproducto() {
        List<Producto> Listarpro = new ArrayList();
             con = conexion.conectando();
        try {
            
            ps = con.prepareStatement("SELECT *FROM  producto");
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
                Listarpro.add(pro);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Listarpro;
    }
    
    
    public boolean EliminarProducto(int id){
     String sql="DELETE  FROM  producto WHERE id=?";
     con=conexion.conectando();
     try {
        
         
        ps=con.prepareStatement(sql);
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
     
      public boolean ModificarProducto(Producto pro){
        
          
        try {
          con=conexion.conectando();
         ps=con.prepareStatement("UPDATE  producto SET codigo=?, descripcion=?, proveedor=?, stock=?, precio=? WHERE id=?");
         ps.setString(1, pro.getCodigo());
         ps.setString(2,pro.getDescripcion());
         ps.setString(3,pro.getProveedor());
         ps.setInt(4,pro.getStock());
         ps.setDouble(5,pro.getPrecio());
         ps.setInt(6,pro.getId());
         ps.execute();
         return true;
        } catch (Exception e) {
            
            System.out.println(e.toString());
            return  false;
        } finally{
        
            try {
                con.close();
            } catch (Exception e) {
                
                System.out.println(e.toString());
            }
        }
        
    }
      
      
      public Producto BuscarPro(String codigo){
        Producto producto=new Producto();
        
          try {
              con=conexion.conectando();
               ps=con.prepareStatement("SELECT * FROM producto WHERE codigo=?");
               ps.setString(1, codigo);
               rs=ps.executeQuery();
               if (rs.next()) {
                  
              producto.setDescripcion(rs.getString("descripcion"));
              producto.setPrecio(rs.getDouble("precio"));
              producto.setStock(rs.getInt("stock"));
              
               } 
               
          } catch (Exception e) {
              System.out.println(e.toString());
          }
        return producto;
      
      }
      
       public config BuscarDatos(){
        config conf=new config();
        
          try {
              con=conexion.conectando();
               ps=con.prepareStatement("SELECT * FROM configuracion");
               
               rs=ps.executeQuery();
               if (rs.next()) {
                  
              conf.setId(rs.getInt("id"));
              conf.setNombre_empresa(rs.getString("nombre_empresa"));
              conf.setRuc(rs.getInt("rut"));
              conf.setTelefono(rs.getString("telefono"));
              conf.setDireccion(rs.getString("direccion"));
              conf.setRazon(rs.getString("razon"));
              
              
               } 
               
          } catch (Exception e) {
              System.out.println(e.toString());
          }
        return conf;
      
      }
         public boolean ModificarDatos(config conf){
        
          
        try {
          con=conexion.conectando();
         ps=con.prepareStatement("UPDATE  configuracion SET nombre_empresa=?, rut=?, telefono=?, direccion=?, razon=? WHERE id=?");
         
         ps.setString(1,conf.getNombre_empresa());
         ps.setInt(2,conf.getRut());
         ps.setString(3,conf.getTelefono());
         ps.setString(4,conf.getDireccion());
         ps.setString(5,conf.getRazon());
         ps.setInt(6, conf.getId());
         ps.execute();
         return true;
        } catch (Exception e) {
            
            System.out.println(e.toString());
            return  false;
        } finally{
        
            try {
                con.close();
            } catch (Exception e) {
                
                System.out.println(e.toString());
            }
        }
        
    }
         
         
         public Producto Listarid(int id){
          Producto pro=new Producto();
                  try {
              con=conexion.conectando();
               ps=con.prepareStatement("SELECT * FROM producto WHERE id=?");
               ps.setInt(1, id);
                 rs=ps.executeQuery();
                      while (rs.next()) {   
                      pro.setId(rs.getInt(1));
                       pro.setCodigo(rs.getString(2));
                      pro.setDescripcion(rs.getString(3));
                       pro.setProveedor(rs.getString(4));
                        pro.setStock(rs.getInt(5));
                        pro.setPrecio(rs.getDouble(6));
                     
                      }
                         
             } catch (Exception e) {
             }
         return  pro;
         
         }
         
         public boolean registrarDatos(config conf){
         
       
          con = conexion.conectando();
        try {

            ps = con.prepareStatement("INSERT INTO configuracion(nombre_empresa,rut,telefono,direccion, razon)VALUE(?,?,?,?,?)");
            ps.setString(1, conf.getNombre_empresa());
            ps.setInt(2, conf.getRut());
            ps.setString(3, conf.getTelefono());
            ps.setString(4,conf.getDireccion());
            ps.setString(5,conf.getRazon());
            ps.execute();
            return (true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return (false);
        } finally {

            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    }

         
           public config Buscarconfig(int rut){
        config conf=new config();
        
          try {
              con=conexion.conectando();
               ps=con.prepareStatement("SELECT * FROM configuracion");
               
               rs=ps.executeQuery();
               if (rs.next()) {
              conf.setId(rs.getInt("id"));
              conf.setRuc(rs.getInt("rut"));
              conf.setNombre_empresa(rs.getString("nombre_empresa"));
              conf.setTelefono(rs.getString("telefono"));
              conf.setDireccion(rs.getString("direccion"));
               conf.setRazon(rs.getString("razon"));
               } 
               
          } catch (Exception e) {
              System.out.println(e.toString());
          }
        return conf;
      
         
         
         }
         
}