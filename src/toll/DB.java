/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author bill
 */
public class DB {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/toll", "root", "");
        return connect;
    }
    public static boolean check(String value){
        
        return false;
    }
    
    public static ResultSet getData(){
        ResultSet rs = null;
        
        try
        {Connection cn=getConnection();
        Statement st=cn.createStatement();
        
        String sql=" SELECT DATE,AMOUNT,TYPE,BOOTH FROM toll_data order by DATE desc";
        rs=st.executeQuery(sql);
        
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return rs;
        
    }
    
    public static String getImage(String tag){
        String master=null;
        try{
                    Connection cn=getConnection();
                
                    String sql="SELECT IMAGE_ID FROM toll_data WHERE DATE='"+tag.trim()+"'";
                    Statement statement=cn.createStatement();
                    ResultSet rs=statement.executeQuery(sql);
                    if(rs.next())
                        {
                master=rs.getString("IMAGE_ID");
                statement.close();
                cn.close();
                        }
            }catch(Exception e){
             master="Not Found";
        }       
        return master;  
    }
    
    public static void setImage(String imTag){
         try{
            Connection cn=getConnection();
            Statement st2=cn.createStatement();
            String sql2="UPDATE toll_data SET IMAGE_ID='"+imTag.trim()+".png"+"' WHERE DATE='"+imTag.trim()+"'";
            st2.executeUpdate(sql2);
            st2.close();
         }catch(Exception e){
             
        }      
    }
    
    public static String getAccD(String tag){
        String master=null;
        try{
                    Connection cn=getConnection();
                    String sql="SELECT ACC_NO FROM qr_tag WHERE QR_TAG='"+tag.trim()+"'";
                    Statement statement=cn.createStatement();
                    ResultSet rs=statement.executeQuery(sql);
                    if(rs.next())
                        {
                master=rs.getString("ACC_NO");
                        }
            }catch(Exception e){
             master="No Tag Found";
        }
        
        return master;
        
    }
    
   
      public static String numbOf(){
          String date="";
           try
        {
        Connection cn=getConnection();
        Statement st=cn.createStatement();
        String sql="SELECT * FROM toll_data order by DATE desc";
        ResultSet rs=st.executeQuery(sql);
        
            if(rs.next())
                        {
                date=rs.getString("DATE");
                cn.close();
                        }
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
          return date;
      } 
      public static boolean login(String username,String password){
          boolean dataCheck=false;
            try
        {
        Connection cn=getConnection();
        Statement st=cn.createStatement();
        String sql="SELECT * FROM user_detail WHERE TRIM(LOWER(USER_NAME))='"+username.trim().toLowerCase()+"' AND TRIM(PASSWORD)='"
                +password.trim().toLowerCase()+"'";
        ResultSet rs=st.executeQuery(sql);       
            if(rs.next())
                        {
                dataCheck=true;
                        }
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
          return dataCheck;
      }
      
      public static ArrayList userData(String username,String password){
          ArrayList arrResources=new ArrayList();
          try
        {
        Connection cn=getConnection();
        Statement st=cn.createStatement();
        String sql="SELECT * FROM user_detail WHERE TRIM(LOWER(USER_NAME))='"+username.trim().toLowerCase()+"' AND TRIM(PASSWORD)='"
                +password.trim().toLowerCase()+"'";
        ResultSet rs=st.executeQuery(sql);       
            if(rs.next())
                        {
                    arrResources.add(rs.getString("NAME"));
                    arrResources.add(rs.getString("ACC_NO"));
                    arrResources.add(rs.getString("AMOUNT"));
                        }
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        } 
          return arrResources;
      }
      public static boolean addTag(String accno,String tag){
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                String useDate=dateFormat.format(date);
          try
        {
            
        Connection cn=getConnection();
        Statement st=cn.createStatement();
        String sql="SELECT * FROM qr_tag WHERE ACC_NO='"+accno+"'";
        ResultSet rs=st.executeQuery(sql);       
            if(rs.next()){
                    try{
             
                Statement st2=cn.createStatement();
                String sql2="UPDATE qr_tag SET QR_TAG='"+tag.trim()+"',TIME='"+useDate.trim()+"' WHERE ACC_NO='"+accno.trim()+"'";
                st2.executeUpdate(sql2);
                st2.close();
            }catch(Exception er){
                 System.err.println(er.getMessage());
            }
             }else{
                 try{
             
                Statement st2=cn.createStatement();
                String sql2="INSERT INTO qr_tag(ACC_NO,QR_TAG,TIME)"
                      + "VALUES('" + accno.trim()+"','"+tag.trim()+"','"+useDate.trim()+"')";
                st2.executeUpdate(sql2);
                st2.close();
            }catch(Exception er){
                 System.err.println(er.getMessage());
            }
            }
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        } 
          
          return true;
      }
}
