/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainningAndPlacement.Dao;

import TrainningAndPlacement.DButil.DBConnection;
import TrainningAndPlacement.Pojo.ParticipantPojo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ParticipantDAO {
    
    public static int getNewParticipantId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select max(pid) from  participants");
        int pId=101;
        rs.next();
        String strid=rs.getString(1);
        if(strid!=null){
            String id=strid.substring(3);
            pId=Integer.parseInt(id)+1;
        }
        return pId;
    } 
    public static int addNewParticipants(ParticipantPojo pt)throws SQLException,FileNotFoundException
    {
          
            Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement("select userid from users where userid=?");
            ps.setString(1,pt.getUserId().trim());
            
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                return -1;   
            
            ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
            ps.setString(1, pt.getUserId());
            ps.setString(2, pt.getId());
            ps.setString(3,pt.getName());
            ps.setString(4,pt.getPassword());
            ps.setString(5,pt.getType());
            ps.setString(6, "Y");
            int x=ps.executeUpdate();
            int y=0;
            if(x==1){
                ps=conn.prepareStatement("insert into PARTICIPANTS values(?,?,?,?,?)");                
                ps.setString(1,pt.getId());
                ps.setLong(2,pt.getPhone());
                ps.setString(3,pt.getSkills());
                ps.setString(4,pt.getQualifications());
                FileInputStream resume = new FileInputStream(pt.getResume());
                
                ps.setBinaryStream(5, resume);
                
                y=ps.executeUpdate();        
                
            }
            return y;
    }

}
