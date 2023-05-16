/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainningAndPlacement.Dao;

import TrainningAndPlacement.DButil.DBConnection;
import TrainningAndPlacement.Pojo.JobPojo;
import TrainningAndPlacement.Pojo.QuestionPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {
    public static int getNewJobId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select Max(jobid) from jobs");
        int jobId=101;
        rs.next();
        String str=rs.getString(1);
        if(str!=null){
            String id=str.substring(4);
            jobId=Integer.parseInt(id)+1;
        }
        return jobId;
        
    }
    public static boolean addNewJob(JobPojo job)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into jobs values(?,?,?,?,?)");
        ps.setString(1, job.getJobId());
         ps.setString(2,job.getJobTitle());
         ps.setString(3, job.getHrId());
         ps.setString(4, job.getTags());
         ps.setInt(5, job.getStatus());
         return 1==ps.executeUpdate();

    }
    public static List<JobPojo> getAllJobsByCurrentHrid(String Hrid)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from jobs where hrid=? and status!=-1");
        ps.setString(1, Hrid);
        ResultSet rs=ps.executeQuery();
        List<JobPojo> allJobs=new ArrayList();
        while(rs.next()){
            JobPojo job=new JobPojo();
            job.setJobId(rs.getString(1));
            job.setJobTitle(rs.getString(2));
            job.setTags(rs.getString(4));
            job.setStatus(rs.getInt(5));
            allJobs.add(job);
        }
        return allJobs;
    }
    public static boolean removeJobByJobId(String jobId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update jobs set status=-1 where jobId=?");
        ps.setString(1, jobId);
        return 1==ps.executeUpdate();
    }
    public static List<JobPojo> getAllEditableJobByCurrentHr(String hrId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select jobid,jobtitle,tags,status from jobs where hrid=? and status=0");
        ps.setString(1, hrId);
        ResultSet rs=ps.executeQuery();
        List<JobPojo> allJobs=new ArrayList();
        while(rs.next()){
            JobPojo job=new JobPojo();
            job.setJobId(rs.getString(1));
            job.setJobTitle(rs.getString(2));
            job.setTags(rs.getString(3));
            job.setStatus(rs.getInt(4));
            allJobs.add(job);
        }
        
        return allJobs;
    }
    public static boolean editJobByJobId(JobPojo job)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update jobs set jobtitle=?,tags=? where jobId=?");
        ps.setString(1, job.getJobTitle());
        ps.setString(2, job.getTags());
        ps.setString(3, job.getJobId());
        return 1==ps.executeUpdate();
    }
    public static void setJobStatus(String jobId)throws  SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update jobs set status=1 where jobId=?");
        ps.setString(1, jobId);
        ps.executeUpdate();
    }
    public static List<JobPojo> getAllOpenJobsByCurrentHrId(String hrId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select jobid,jobtitle,tags,status from jobs where hrid=? and status=1");
        ps.setString(1, hrId);
        ResultSet rs=ps.executeQuery();
        List<JobPojo> allJobs=new ArrayList();
        while(rs.next()){
            JobPojo job=new JobPojo();
            job.setJobId(rs.getString(1));
            job.setJobTitle(rs.getString(2));
            job.setTags(rs.getString(3));
            job.setStatus(rs.getInt(4));
            allJobs.add(job);
        }
        
        return allJobs;
    }
   

}
