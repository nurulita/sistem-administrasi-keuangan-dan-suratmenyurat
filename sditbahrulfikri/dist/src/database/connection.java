/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author nurul
 */
public class connection {
    public static String PathReport = System.getProperty("user.dir") + "/src/laporan/";
    private static java.sql.Connection conn;    
      public static java.sql.Connection getConn(){
        if(conn == null){
          try{
              String url = "jdbc:mysql://localhost:3306/sditbahrulfikri";
              String user = "root";
              String password = "";
              
              DriverManager.registerDriver(new com.mysql.jdbc.Driver());
              
              conn = DriverManager.getConnection(url, user, password);
          }catch(SQLException t){
              System.out.println("Error Membuat Koneksi");
          }  
        }
        return conn;
}
}

