import java.sql.*;
import java.util.Scanner;

public class Admin {
    public void option(){
        Scanner sc=new Scanner(System.in);
        System.out.println("1.add doctor");
        System.out.println("2.view patients list");
        System.out.println("3.view Doctors list");
        System.out.println("4.remove doctors");
        System.out.println("5. see feedback");
        System.out.println("6.view reports");
        System.out.println("7.logout");
        int opt=sc.nextInt();
        switch(opt){
           case 1:
           adoc();
           break;
           case 2:
           pList();
           break;
           case 3:
           vDoc();
           break;
           case 4:
           rDoc();
           break;
           case 5:
           sFeedBack();
           case 6:
           vreports();
           break;
           case 7:
           System.out.println("logout successfully");
           break;
        }
        sc.close();
    }
    private void adoc(){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter new doctors name");
        String name=sc.nextLine();
        System.out.print("choose speciality");
        System.out.println(
            "1.General practitioner\n2.Endocrinologist\n3.Cardiologist\n4.Onclogist\n5.Pyschiatrists\n6.Gastroenterologiat.");
        int ch = sc.nextInt();
        String dep=chooseDap(ch);
        System.out.print("Enter id");
        int id=sc.nextInt();
        System.out.print("Enter fee of doctor");
        int fee=sc.nextInt();
        System.out.print("Enter password");
        String pass=sc.next();
        try{
            Connection con=Connection1.pconect();
            PreparedStatement ps=con.prepareStatement("insert into doctor(docname,password,speciality,fee,d_id) values(?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,pass);
            ps.setString(3,dep);
            ps.setInt(4,fee);
            ps.setInt(5,id);
            int m=ps.executeUpdate();
            if(m>0){
              System.out.println("doctor added successfully");
              System.out.println("--------------------------------------------\n");
            }else{
                System.out.println("Something went wrong");
            }
            option();
        }catch(Exception e){
            e.printStackTrace();
        }
        sc.close();
    }
    private String chooseDap(int dep) {

       String Filename="";
        switch (dep) {
            case 1:
                Filename = "General Practitioner";
                break;
            case 2:
                Filename = "Endocrinologist";
                break;
            case 3:
                Filename = "Cardiologist";
                break;
            case 4:
                Filename = "Oncologist";
                break;
            case 5:
                Filename = "psychiatrists";
                break;
            case 6:
                Filename = "Gastroenterologist";
                break;
            default:
                System.out.println("please enter vaild details");
        }
        return Filename;
    }
private void pList(){
    try {
        Connection con =Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("select name from patient");
        ResultSet rs= ps.executeQuery();
        System.out.println("patient's name list are");
        while(rs.next()){
            System.out.println(rs.getString("name"));
        }
        System.out.println("--------------------------------------------------\n");
        option();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void vDoc(){
    try {
        Connection con =Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("select docname from doctor");
        ResultSet rs= ps.executeQuery();
        System.out.println("doctor's name list are");
        while(rs.next()){
            System.out.println(rs.getString("docname"));
        }
        System.out.println("--------------------------------------------------\n");
        option();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void sFeedBack(){
    try {
        Connection con =Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("select * from feedback");
        ResultSet rs= ps.executeQuery();
        System.out.println("feedback's are list are");
        while(rs.next()){
            System.out.println("name:"+rs.getString("name"));
            System.out.println("email:"+rs.getString("email"));
            System.out.println("phone number:"+rs.getString("phone"));
            System.out.println("overall_experience:"+rs.getString("overall_experience"));
            System.out.println("facilite"+rs.getString("facilite"));
            System.out.println("staff Experience:"+rs.getString("staffEx"));
            System.out.println("medical experience:"+rs.getString("medical"));
            System.out.println("parking area:"+rs.getString("parkarea"));
            System.out.println("covid protocol:"+rs.getString("covid_protocol"));
            System.out.println("recommandation:"+rs.getString("recommandation"));
        }
        System.out.println("--------------------------------------------------\n");
        option();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void rDoc(){
    Scanner sc=new Scanner(System.in);
    System.out.print("Enter doctor's id :");
    int id=sc.nextInt();
    try {
        Connection con =Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("delete from doctor where d_id = ?");
        ps.setInt(1,id);
      int m= ps.executeUpdate();
      if(m>0){
        System.out.println("remove successfully");
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("--------------------------------------------------\n");
    option();
    sc.close();
}
private void vreports(){
    Connection con =Connection1.pconect();
    try {
     PreparedStatement ps=con.prepareStatement("select reports.p_id,reports.d_id,reports.disease,patient.name,patient.age,patient.address from reports left join patient on patient.p_id=reports.p_id");
     ResultSet rs=ps.executeQuery();
     if(rs.next()){
       System.out.println("patient's id: "+rs.getInt("reports.p_id")+" \ndoctor's id:"+rs.getInt("reports.d_id")+"\nreports:"+rs.getString("reports.disease")+"n\name:"+rs.getString("patient.name")+"\nage"+rs.getInt("patient.age")+" \naddress"+rs.getString("patient.address"));
     }
    } catch (Exception e) {
     e.printStackTrace();
    }
    System.out.println("--------------------------------------------------\n");
    option();
}
}
