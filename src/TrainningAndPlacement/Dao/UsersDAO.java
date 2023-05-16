
package TrainningAndPlacement.Dao;

import TrainningAndPlacement.DButil.DBConnection;
import TrainningAndPlacement.Pojo.CurrentUser;
import TrainningAndPlacement.Pojo.HrPojo;
import TrainningAndPlacement.Pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;



public class UsersDAO {
    public static boolean validateUser(UserPojo user)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from users where userid=? and Password=? and type=?");
        ps.setString(1, user.getUserId());
        ps.setString(2,user.getPassword());
        ps.setString(3, user.getType());
        
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            CurrentUser.setUserid(rs.getString(1));
            CurrentUser.setId(rs.getString(2));
            CurrentUser.setName(rs.getString(3));
            CurrentUser.setType(rs.getString(5));
            return true;
        }
        return false;
    }
    public static Map<String,HrPojo> getAllHrList()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select userid,id,name from users where type='Hr'");
        Map <String,HrPojo> allHrList=new HashMap<>();
        while(rs.next()){
            String userId=rs.getString(1);
            String id=rs.getString(2);
            String name=rs.getString(3);
            HrPojo obj=new HrPojo();
            obj.setUserId(userId);
            obj.setHrId(id);
            obj.setHrName(name);
            allHrList.put(userId, obj);
        }
        return allHrList;
    }
    public static boolean updatePwd(String userId,String pwd)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update users set password=? where userid=?");
        ps.setString(1, pwd);
        ps.setString(2, userId);
        return 1==ps.executeUpdate();
        
    }
    public static boolean removeUser(String userId)throws SQLException{
        PreparedStatement ps=DBConnection.getConnection().prepareStatement("update users set active='N' where userid=?");
        
        ps.setString(1,userId);
        return 1==ps.executeUpdate();
    }

}
