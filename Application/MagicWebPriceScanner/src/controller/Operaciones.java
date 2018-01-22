package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Operaciones {
	// --------- CONSULTAS SELECT -----------
    public Object [][] select(String table, String fields, String where, Connection con){
    	int registros = 0;      
    	String colname[] = fields.split(",");

    	//Consultas SQL
    	String q = "SELECT " + fields + " FROM " + table;
    	String q2 = "SELECT count(*) as total FROM " + table;
    	if(where!=null){
          q+= " WHERE " + where;
          q2+= " WHERE " + where;
    	}
    	try{
    		PreparedStatement pstm = con.prepareStatement(q2);
    		ResultSet res = pstm.executeQuery();
    		res.next();
    		registros = res.getInt("total");
    		res.close();
    	}catch(SQLException e){
    		JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    	}
    
    	//Se crea una matriz con tantas filas y columnas que necesite
    	Object[][] data = new String[registros][fields.split(",").length];
    	//Realizamos la consulta sql y llenamos los datos en la matriz "Object"
    	try{
    		PreparedStatement pstm = con.prepareStatement(q);
    		ResultSet res = pstm.executeQuery();
    		int i = 0;
    		while(res.next()){
	    		for(int j = 0; j <= fields.split(",").length - 1;j++){
	    			data[i][j] = res.getString(colname[j].trim());
	    		}
	    		i++;
    		}
    		res.close();
        } catch(SQLException e) { System.out.println(e); }
    	return data;
    }
	// --------- CONSULTAS INSERT -----------
    public boolean insert(String table, String fields, String values, Connection con) {
        boolean res = false;
        String[] valuesx = values.split(",");
        String q2 = "SELECT count(*) as total FROM " + table;
        String consult = "SELECT count(*) as total FROM " + table + " WHERE NOMBRE = " + valuesx[0] + " AND EDICION = " + valuesx[1]; 
        String consult4 = "UPDATE " + table + " SET CANTIDAD = " + valuesx[2] + " WHERE NOMBRE = " + valuesx[0] + " AND EDICION = " + valuesx[1]; 
        // -----------------------------------------------------------------
        try {
    		PreparedStatement pstm = con.prepareStatement(q2);
    		ResultSet res2 = pstm.executeQuery();
    		res2.next();
    		int registros2 = res2.getInt("total");
    		res2.close();
    		if(registros2 == 1) { delete(table, "NOMBRE = ''", con); }
    	} catch (SQLException e) { JOptionPane.showMessageDialog(null, e.getLocalizedMessage()); }
        // -----------------------------------------------------------------
        try {
    		PreparedStatement pstm4 = con.prepareStatement(consult);
    		ResultSet res4 = pstm4.executeQuery();
    		res4.next();
    		int registros4 = res4.getInt("total");
    		res4.close();
    		if(registros4 == 1) {
    			if(valuesx[2].equals("'0'")) {
    				String x2 = "NOMBRE = " + valuesx[0] + " AND EDICION = " + valuesx[1];
    				delete(table, x2, con); 
    				res = true;
    				JOptionPane.showMessageDialog(null, "Eliminado correctamente");
    			}
    			else {
    	    		PreparedStatement pstm5 = con.prepareStatement(consult4);
    	    		pstm5.execute();
    	    		pstm5.close();
    	    		res = true;
    	    		JOptionPane.showMessageDialog(null, "Actualizado correctamente");
    			}
    		}
    		else {
    	        String q = " INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
                PreparedStatement pstm6 = con.prepareStatement(q);
                pstm6.execute();
                pstm6.close();
                res = true;   
                JOptionPane.showMessageDialog(null, "Guardado correctamente");
    		}
        } catch (SQLException et) { JOptionPane.showMessageDialog(null, et.getLocalizedMessage()); }
        return res;
    }     
    // --------- CONSULTAS DELETE --------------
    public void delete(String table, String where, Connection con) {
    	String d = "DELETE FROM " + table + " WHERE " + where;
        try {
    		PreparedStatement pstm7 = con.prepareStatement("SELECT count(*) as total FROM " + table);
    		ResultSet res7 = pstm7.executeQuery();
    		res7.next();
    		int registros2 = res7.getInt("total");
    		res7.close();
            PreparedStatement pstm = con.prepareStatement(d);
            pstm.execute();
            pstm.close();
            if(registros2 == 1) {
            	String firstInsert = " INSERT INTO Cartas ( NOMBRE, EDICION, CANTIDAD ) VALUES ( '', '', '' ) ";
	            PreparedStatement pstm3 = con.prepareStatement(firstInsert);
	            pstm3.execute();
	            pstm3.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }
    }
    // --------- OBTENER LAS CARTAS ------------
    public Object[][] getCartas(Connection con){
    	Object[][] res = this.select("Cartas", "NOMBRE, EDICION, CANTIDAD", null, con);
        if(res.length > 0) { return res; }
        else { return null; }
    } 
}
