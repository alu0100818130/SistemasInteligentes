package controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {
    private Connection conn = null;
    // ---------- CREAR LA BDD EN DERBY -------------
    public Connection CrearBD(){
       try{
         //Obtenemos el driver de para mysql
         Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
         //Obtenemos la conexion
         conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB;create=true");
         if (conn!=null){
            JOptionPane.showMessageDialog(null,"Creada con éxito la BDD");
            String creartabla = "CREATE TABLE Cartas(NOMBRE varchar(50), EDICION varchar(50), CANTIDAD varchar(50), PRIMARY KEY (NOMBRE, EDICION))";
            String firstInsert = " INSERT INTO Cartas ( NOMBRE, EDICION, CANTIDAD ) VALUES ( '', '', '' ) ";
            //String desc = "DISCONNECT ALL";
            try {
	            PreparedStatement pstm = conn.prepareStatement(creartabla);
	            pstm.execute();
	            pstm.close();
	            PreparedStatement pstm3 = conn.prepareStatement(firstInsert);
	            pstm3.execute();
	            pstm3.close();
	            /*
	            PreparedStatement pstm2 = conn.prepareStatement(desc);
	            pstm2.execute();
	            pstm2.close();
	            */
	            JOptionPane.showMessageDialog(null,"BDD Creada correctamente");
            } catch (SQLException ex) { JOptionPane.showMessageDialog(null, ex.getLocalizedMessage()); }
         }
      } catch (SQLException e) {
    	  JOptionPane.showMessageDialog(null,"Alguna instancia de la BDD continúa abierta");
      } catch(ClassNotFoundException e){
    	  JOptionPane.showMessageDialog(null,"Alguna instancia de la BDD continúa abierta");
      }
      return conn;
    }
    // ---------- ACCEDER A LA BDD EN DERBY -------------
    public Connection AccederBD(boolean mss){
    	try{
	        //Obtenemos el driver de para mysql
	        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	        //Obtenemos la conexion
	        conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby.DB");
	        if (conn!=null && mss){ JOptionPane.showMessageDialog(null,"Base de Datos reconocida"); }
    	}catch(SQLException e){
    		JOptionPane.showMessageDialog(null,"No se ha encontrado una BDD \nSe procede a su creación");
    		CrearBD();
    	}catch(ClassNotFoundException e){
    		JOptionPane.showMessageDialog(null,"No se ha encontrado una BDD \nSe procede a su creación");
    		CrearBD();
    	}
       return conn;
    }
    // ---------- CERRAR LA BDD DE DERBY -------------
    public void cerracon (){
    	try { conn.close(); } 
    	catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
