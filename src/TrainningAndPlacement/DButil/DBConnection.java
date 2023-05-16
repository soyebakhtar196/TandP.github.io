
package TrainningAndPlacement.DButil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 
import javax.swing.JOptionPane;

public class DBConnection {
     private static Connection conn;
    
    static {
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-0J8TO13:1521/XE","tnp","project");
            
            JOptionPane.showMessageDialog(null, "connection opened seccefully ","Success",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "error in driver loading ","Driver error!",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error in the sql connection","sql error! ",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    
    public static void closeConnection(){
        
        try{
            conn.close();
            JOptionPane.showMessageDialog(null, "connection close seccefully","DB close ",JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error in closing the DB","Error DB!",JOptionPane.INFORMATION_MESSAGE);
            ex.printStackTrace();
        }
        
    }
}
