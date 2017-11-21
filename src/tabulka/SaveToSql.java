/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabulka;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomi
 */
public class SaveToSql {
    
    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "Schedule";
    private String driver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "1234";
    
    private Connection conn = null;
        
    private boolean OpenConnetion(){
        try{
            Class.forName(driver).newInstance();
            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(url + dbName,username,password);
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    private boolean CloseConnection(){
        try{
            conn.close();
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    private void removeOld(){
        if(OpenConnetion()){
            String query = "delete from chedule";
            try{
                Statement st = conn.createStatement();
                st.executeUpdate(query);
            }catch (Exception ex){
                
            }
            
            CloseConnection();
        }
    }
    
    public void save(List<TableData> data){
        removeOld();
        if(OpenConnetion()){
            
            for (int i=0;i<data.size();i++){
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    int stat = 0;
                    if(data.get(i).status)
                        stat=1;
                    String query = "insert into chedule(name,description,date_,starus)"
                            + " values('"+data.get(i).name+"', '"+data.get(i).description+"', "
                            + "'"+sdf.format(data.get(i).date)+"',"+stat+"  )";
                            
                    Statement state = conn.createStatement();
                    state.executeUpdate(query);
                    
                }catch(Exception ex){
                    System.out.println(ex.toString());
                }
            }
            CloseConnection();
        }
        
    }
    
    public List<TableData> loadData(){
        List<TableData> data = new ArrayList<TableData>();
        if(OpenConnetion()){
            String query = "select * from chedule";
            try{
                Statement state = conn.createStatement();
                ResultSet rs =  state.executeQuery(query);
                while (rs.next()){
                    TableData d = new TableData();
                    d.name = rs.getString("name");
                    d.description = rs.getString("description");
                    String dat = rs.getString("date_");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    d.date = format.parse(dat);
                    String st = rs.getString("starus");
                    d.status = Integer.parseInt(st) != 0;
                    data.add(d);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            CloseConnection();
        }
        return data;
    }
}
