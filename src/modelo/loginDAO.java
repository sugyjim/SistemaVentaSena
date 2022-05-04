
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

public class loginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion cn= new conexion();
  
    
    

    public loginDAO() {
    }
         
    public login log(String usuario,String pass,String roll){
        login l=new login();
    
        try {
            con=conexion.conectando();
          
            ps=con.prepareStatement("SELECT *FROM usuario WHERE usuario= ? AND pass= ? AND roll=?");
            
            ps.setString(1,usuario);
            ps.setString(2,pass);
            ps.setString(3, roll);
            rs=ps.executeQuery();
            if (rs.next()) {
              l.setId(rs.getInt("id"));
              l.setIdentificacion(rs.getString("identificacion"));
              l.setNombre(rs.getString("nombre"));
              l.setUsuario(rs.getString("usuario"));
              l.setPass(rs.getString("pass"));
              l.setRoll(rs.getString("roll"));
            }
            
            
        } catch (SQLException e) {
            System.out.println(e. toString());
        }
    
return l;
    }
    
    
    public boolean RegistrarU(login reg){
    
        try {
            con=conexion.conectando();
          
            ps=con.prepareStatement ("INSERT INTO usuario( identificacion,nombre,usuario, pass, roll) VALUES(?,?,?,?,?)");
            
            
            ps.setString(1,reg.getIdentificacion());
            ps.setString(2, reg.getNombre());
           
            ps.setString(3,reg.getUsuario());
            ps.setString(4,reg.getPass());
            ps.setString(5,reg.getRoll());
            ps.execute();
            return true;
            
            
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    
    
    
    
    
    }
    public List listarlogin(){
    
     List<login> Listarlogin = new ArrayList();
             con = conexion.conectando();
        try {
            
            ps = con.prepareStatement("SELECT *FROM  usuario");
            rs = ps.executeQuery();

            while (rs.next()) {
               login log = new login();
                log.setId(rs.getInt("id"));
                log.setIdentificacion(rs.getString("identificacion"));
                log.setNombre(rs.getString("nombre"));
                log.setUsuario(rs.getString("usuario"));
                log.setPass(rs.getString("pass"));
                 log.setRoll(rs.getString("roll"));
                
                Listarlogin.add(log);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Listarlogin;
    }
    public boolean Modificarlogi(login log){
   con=conexion.conectando();
       
       try {
         con=conexion.conectando();
         ps=con.prepareStatement("UPDATE usuario SET identificacion=?, nombre=?, usuario=?, pass=?, roll=? WHERE id=?" );
        
         ps.setString(1,log.getIdentificacion());
         ps.setString(2, log.getNombre());
         ps.setString(3,log.getUsuario());
         ps.setString(4,log.getPass());
         ps.setString(5,log.getRoll());
        ps.setInt(6,log.getId());
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
     
    public boolean Eliminarlogin(int id){
     
     con=conexion.conectando();
     try {
        
         
        ps=con.prepareStatement("DELETE  FROM  usuario WHERE id=?");
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

    public login BuscarUsuario(String identificacion){
    
 
     login l=new login();
        con=conexion.conectando(); 
          try {
             
               ps=con.prepareStatement("SELECT * FROM usuario WHERE identificacion=?");
               ps.setString(1, identificacion);
               rs=ps.executeQuery();
               if (rs.next()) {
                  
              l.setNombre(rs.getString("nombre"));
              l.setUsuario(rs.getString("usuario"));
              l.setPass(rs.getString("pass"));
               l.setRoll(rs.getString("roll"));
               } 
               
         
             
               
          } catch (Exception e) {
              System.out.println(e.toString());
          }
        return l;
      
    
    
    
    }
    }
    
