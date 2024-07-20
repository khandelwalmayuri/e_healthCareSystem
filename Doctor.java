import java.util.Scanner;
import java.sql.*;
public class Doctor {
    int id;
    String pass;
    public Doctor(int id,String pass){
        this.id=id;
        this.pass=pass;
    }
    public void option(){
        Scanner sc=new Scanner(System.in);
        System.out.println("view profile");
        System.out.println("viewAppointments");
        System.out.println("Attend Patients");
        System.out.println("logout");
        int opt=sc.nextInt();
        switch(opt){
            case 1:
            vprofile();
            break;
            case 2:
            vApp();
            break;
            case 3:
            aPatients();
            break;
            case 4:
            System.out.println("logout successfully");
            break;
        }
        sc.close();
    }
private void vprofile(){
Connection con=Connection1.pconect();
try {
    PreparedStatement ps=con.prepareStatement("select d_id,docname,speciality from doctor where d_id=? and password=?");
    ps.setInt(1,id);
    ps.setString(2,pass);
    ResultSet rs=ps.executeQuery();
    if(rs.next()){
        System.out.println("doctor's ID:"+rs.getInt("d_id"));
        System.out.println("docname:"+rs.getString("docname"));
        System.out.println("speciality:"+rs.getString("speciality"));
    }
} catch (Exception e) {
   System.out.println(e);
}
option();
    }
private void vApp(){
    Connection con=Connection1.pconect();
    try {
        PreparedStatement ps=con.prepareStatement("select p_id,name ,Date,slot from appointment where D_id=?;");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            System.out.println("patient's id"+rs.getInt("p_id"));
            System.out.println("patient's name:"+rs.getString("name"));
            System.out.println("date:"+rs.getString("date"));
            System.out.println(rs.getString("slot"));
            System.out.println();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    option();
    }
    private void aPatients(){
        Scanner sc=new Scanner(System.in);
        Connection con=Connection1.pconect();
        System.out.print("Enter patient's id you want to attend:");
        int p_id=sc.nextInt();
        try {
            PreparedStatement ps=con.prepareStatement("delete from appointment where p_id=? and D_id=?");
            ps.setInt(1,p_id);
            ps.setInt(2,id);
            ps.executeUpdate();
            System.out.println("do you want to make report now(y/n)");
            char ch=sc.next().charAt(0);
            if(ch=='y'){
                System.out.print("Enter the date(in yyyy/mm/dd):");
               String date=sc.next();
               sc.nextLine();
               System.out.print("Enter the report:");
               String r=sc.nextLine();
                ps=con.prepareStatement("insert into reports values(?,?,?,?)");
               ps.setInt(1,p_id);
               ps.setInt(2,id);
               ps.setString(3,date);
               ps.setString(4,r);
               int m=ps.executeUpdate();
               if(m>0){
                System.out.println("report stored in database successfully");
                System.out.println();
                option();
               }
               con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }
}
