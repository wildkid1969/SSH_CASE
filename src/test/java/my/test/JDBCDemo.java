package my.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 所有的JDBC应用程序都具有下面的基本流程。
	（1）注册JDBC驱动程序。
	（2）建立到数据库的连接。
	（3）创建SQL语句。
	（4）执行SQL语句。
	（5）处理结果。
	（6）从数据库断开连接。
 *
 */
  
public class JDBCDemo {  
    public static final String DB_URL = "jdbc:mysql://localhost:3306/flapjack?characterEncoding=UTF-8&useUnicode=true&autoReconnect=true";  
    public static final String DIRVER_NAME = "com.mysql.jdbc.Driver";  
    public static final String DB_USER = "root";  
    public static final String DB_PASSWORD = "root";  
  
    public static Connection conn = null;  
    public static PreparedStatement pst = null;  
  
    public static void JDBCUtil(String sql) {  
        try {  
            //注册(加载)JDBC驱动程序
            Class.forName(DIRVER_NAME);//作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
           
            //创建数据库的连接
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            //创建一个Statement 
            pst = conn.prepareStatement(sql);
            
            //执行SQL语句，得到结果集
        	ResultSet resultSet = pst.executeQuery();
        	
        	//处理结果集
    		while (resultSet.next()) {//显示数据
    			String uid = resultSet.getString(1);
    			String ufname = resultSet.getString(2);
    			String ulname = resultSet.getString(3);
    			String udate = resultSet.getString(4);
    			System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate );
    		}
    		
    		if(resultSet != null){//关闭记录集   
    		    resultSet.close() ;   
            } 
    		
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭JDBC对象、释放资源
            if(pst != null){//关闭声明   
                try{   
                    pst.close() ;   
                }catch(SQLException e){   
                    e.printStackTrace() ;   
                }   
            }   
            if(conn != null){//关闭连接对象   
                 try{   
                    conn.close() ;   
                 }catch(SQLException e){   
                    e.printStackTrace() ;   
                 }   
            }  
        }
    } 
    
    public static void main(String[] args) {
    	String sql = "select *from t_douban_top_film";
    	JDBCUtil(sql);

	}
} 

