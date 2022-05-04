/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author GAMER
 */
public class ClienteDAO {
    conexion cn;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarcliente(Cliente cl){
     con=conexion.conectando();
        try {
            
            ps=con.prepareStatement("INSERT INTO clientes(identificacion,nombre,telefono,direccion,razonsocial)VALUE(?,?,?,?,?)" );
            ps.setString(1,cl.getIdentificacion());
            ps.setString(2,cl.getNombre());
            ps.setString(3,cl.getTelefono());
            ps.setString(4,cl.getDireccion());
            ps.setString(5,cl.getRazonsocial());
            ps.execute();
            return (true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return (false);
        }finally{
        
        
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
}
    }

public List listarCliente()  {
    List<Cliente>Listacl= new ArrayList();
    
    
     
    try {
         con=conexion.conectando();
         ps=con.prepareStatement("SELECT *FROM  clientes");
         rs=ps.executeQuery();
        
         while (rs.next()) {
              Cliente cl=new Cliente();
             cl.setId(rs.getInt("id"));
             cl.setIdentificacion(rs.getString("identificacion"));
             cl.setNombre(rs.getString("nombre"));
             cl.setTelefono(rs.getString("telefono"));
             cl.setDireccion(rs.getString("direccion"));
             cl.setRazonsocial(rs.getString("razonsocial"));
             Listacl.add(cl);
         }
             
                     
     
            
        
    } catch (Exception e) {
            System.out.println(e.toString());
    }
     return Listacl;
     
}

 public boolean EliminarCliente(int id){
     String sql="DELETE  FROM  clientes WHERE id=?";
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
    
    public boolean ModificarCliente(Cliente cl){
        String sql="UPDATE  clientes SET identificacion=?,nombre=?, telefono=?, direccion=?, razonsocial=? WHERE id=?";
          con=conexion.conectando();
        try {
          
         ps=con.prepareStatement(sql);
         ps.setString(1, cl.getIdentificacion());
         ps.setString(2,cl.getNombre());
         ps.setString(3,cl.getTelefono());
         ps.setString(4,cl.getDireccion());
         ps.setString(5,cl.getRazonsocial());
         ps.setInt(6,cl.getId());
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
    
    public Cliente BuscarCliente(String identificacion){
        Cliente cl= new Cliente();
        String sql= "SELECT *FROM clientes WHERE identificacion =?";
        
        try {
              con=conexion.conectando();
              ps=con.prepareStatement(sql);
              ps.setString(1, identificacion);
              rs=ps.executeQuery();
              if (rs.next()) {
                  cl.setNombre(rs.getString("nombre"));
                  cl.setTelefono(rs.getString("telefono"));
                  cl.setDireccion(rs.getString("direccion"));
                  cl.setRazonsocial(rs.getString("razonsocial"));
                  
                
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
               return cl;
    
    }


}

 







