
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexion {
    public static String driver="com.mysql.jdbc.Driver";
    public static String user="root";
    public static String pass="";
    public static String url="jdbc:mysql://localhost:33066/basesistema";
   
     public  static  Connection conectando(){
    
    Connection con=null;
        try {
             
             
             Class.forName(driver);
             con= DriverManager.getConnection(url,user,pass);
             
        } catch (ClassNotFoundException ex) {
            
            System.out.println("ha ocurrido un error:"+ ex);
        }catch(SQLException ex){
        
            System.out.println("ha ocurrido un error");
        }
        return  con;
    }
    
    }

   
     
    
   
   
