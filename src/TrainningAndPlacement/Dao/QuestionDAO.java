
package TrainningAndPlacement.Dao;

import TrainningAndPlacement.DButil.DBConnection;
import TrainningAndPlacement.Pojo.QuestionPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuestionDAO {
    public static boolean setPaper(List<QuestionPojo> questionList)throws SQLException{
        Connection conn=DBConnection.getConnection();
        int count=0;
        PreparedStatement ps=conn.prepareStatement("insert into questions values(?,?,?,?,?,?,?,?)");
        for(QuestionPojo question:questionList){
            ps.setString(1, question.getJobId());
            ps.setInt(2, question.getQno());
            ps.setString(3, question.getQuestion());
            ps.setString(4, question.getOption1());
            ps.setString(5, question.getOption2());
            ps.setString(6, question.getOption3());
            ps.setString(7, question.getOption4());
            ps.setInt(8, question.getCorrectOption());
            int res=ps.executeUpdate();            
            if(res==1)
                ++count;
        }
        if(count==questionList.size())
            return true;
        return false;
    }
    public static List<QuestionPojo> getQuestionPaperByJobId(String jobId)throws SQLException
    {
        PreparedStatement ps;
        ps=DBConnection.getConnection().prepareStatement("select * from questions where jobid=?");
        ps.setString(1, jobId);
        ResultSet rs=ps.executeQuery(); 
        List<QuestionPojo> questionsList = new ArrayList<QuestionPojo>();
        while(rs.next())
        {
            QuestionPojo que=new QuestionPojo();
            que.setJobId(rs.getString(1));
            que.setQno(rs.getInt(2));
            que.setQuestion(rs.getString(3));
            que.setOption1(rs.getString(4));
            que.setOption2(rs.getString(5));
            que.setOption3(rs.getString(6));
            que.setOption4(rs.getString(7));
            que.setCorrectOption(rs.getInt(8));
            questionsList.add(que);
        }   
        return questionsList;
    }

    public static boolean editQuestionPaper(List<QuestionPojo> questionsList)throws SQLException
    {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update questions set question=?,option1=?,option2=?,option3=?,option4=?,correctoption=? where jobid=? and qno=?");
        int count=0;
        for(QuestionPojo que:questionsList)
        {
                        
            ps.setString(1,que.getQuestion());
            ps.setString(2,que.getOption1());
            ps.setString(3,que.getOption2());
            ps.setString(4,que.getOption3());
            ps.setString(5,que.getOption4());
            ps.setInt(6,que.getCorrectOption());
            ps.setString(7,que.getJobId());
            ps.setInt(8,que.getQno());
            int res=ps.executeUpdate();            
            if(res==1)
                ++count;
        }
        if(count==questionsList.size())
            return true;
        return false;    
    }

}
