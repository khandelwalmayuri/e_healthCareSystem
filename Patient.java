import java.util.Scanner;
import java.net.URI;
import java.sql.*;
import java.awt.Desktop;
public class Patient {
  private String name;
  private String pass;

  Patient(String name,String pass){
    this.name=name;
    this.pass=pass;
  }
    public void option(){
        Scanner sc=new Scanner(System.in);
        System.out.println(
            "what do you want to do: \n1.View your profile\n2.View Doctors\n3.Book Appointment\n4.View appointments \n5.give feedback\n6.pay online\n7.view your report \n8.logout");
            int n=sc.nextInt();
          switch (n) {
            case 1:
            pView();
            option();
            break;
            case 2:
            dView();
            option();
            break;
            case 3:
            bApp();
            option();
            break;
            case 4:
            vApp();
            option();
            break;
            case 5:
            feedback();
            option();
            break;
            case 6:
            onlinepay();
            option();
            break;
            case 7:
            vReport();
            break;
            case 8:
            System.out.println("logout succesfully");
            break;
        }
    } 
    private void pView(){
       Connection con=Connection1.pconect();
       try{
      PreparedStatement ps=con.prepareStatement("select*from patient where pass=? and name=?");
      ps.setString(1,pass);
      ps.setString(2,name);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
        String name1=rs.getString("name");
        String add=rs.getString("address");
        int age1=rs.getInt("age");
        long ph_no=rs.getLong("phone_no");
        String dis=rs.getString("diesase");
        String fee1=rs.getString("fee");
        System.out.println("patient's name:"+name1);
      System.out.println("patient's address:"+add);
      System.out.println("patient's age:"+age1);
      System.out.println("patient's phone number:"+ph_no);
      System.out.println("patient's diesase:"+dis);
      System.out.println("patient's fee status(paid/not paid):"+fee1);
      }
      System.out.println("--------------------------------------------------\n");

       }catch(Exception e){
        e.printStackTrace();
       }
    }
    private void onlinepay(){
      Scanner sc=new Scanner(System.in);
      try {
        Connection con=Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("select doc_id from patient where pass = ?");
        ps.setString(1,pass);
        ResultSet rs=ps.executeQuery();
        int docId=0;
        if(rs.next()){
            docId=rs.getInt("doc_id");
        }
        ps=con.prepareStatement("select fee from doctor where d_id=?");
        ps.setInt(1,docId);
        ResultSet rs1 =ps.executeQuery();
        if(rs1.next()){
        System.out.println("you have to pay :"+rs1.getInt("fee"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("which app would you like to pay");
      System.out.println("1.Phone pay\n2.Google pay");
      int n=sc.nextInt();
     
       try{
        if (Desktop.isDesktopSupported()) {
           Desktop desktop = Desktop.getDesktop();
           if(n==2){
          URI uri = new URI("https://pay.google.com/");
          desktop.browse(uri);
           }else if(n==1){
            URI uri = new URI("https://www.phonepe.com/");
            desktop.browse(uri);
           }
         PreparedStatement ps=Connection1.pconect().prepareStatement("update patient set fee='y' where name=? and pass=?");
         ps.setString(1,name);
         ps.setString(2,pass);
        int m= ps.executeUpdate();
         if(m>0){
          System.out.println("payment make successfully");
         }else{
          System.out.println("something went wrong try again");
         }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("Payment successfully");
      option();
      sc.close();
    }
    private void feedback(){
      Scanner sc=new Scanner(System.in);
      System.out.println("Enter your full name");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        String email = sc.next();
        sc.nextLine();
        System.out.println("Enter your phone number");
        Long phone = sc.nextLong();
        System.out.println("Enter your overall expriance");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String oe=sc.next();
        System.out.println("How would you rate our hospitals Facilities and Environment:");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String hf= sc.next();
        System.out.println("How would you rate our staff interactions");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String si= sc.next();
        System.out.println("How would you rate our medical services:");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String ms= sc.next();
        System.out.println("How would you rate our parking area");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String pa= sc.next();
        System.out.println("how was our covid protocol");
        System.out.println("Excellent\nVery Good\nGood\nFair\nPoor");
        String covid= sc.next();
        System.out.println("Would you recommend this hospital to others?\nYes\nNo\nMaybe");
        String r = sc.next();
        try {
          Connection con=Connection1.pconect();
          PreparedStatement ps=con.prepareStatement("insert into feedback values(?,?,?,?,?,?,?,?,?,?)");
          ps.setString(1,name);
          ps.setString(2,email);
          ps.setLong(3,phone);
          ps.setString(4,oe);
          ps.setString(5,hf);
          ps.setString(6,si);
          ps.setString(7,ms);
          ps.setString(8,pa);
          ps.setString(9,covid);
          ps.setString(10,r);
         int m= ps.executeUpdate();
          if(m>0){
            System.out.println("your feedback is successfully stored");
            System.out.println("--------------------------------------------------\n");
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
        sc.close();
        option();
    }
    private void dView(){
      try {
        Connection con=Connection1.pconect();
        PreparedStatement ps=con.prepareStatement("select diesase from patient where name = ? and pass = ?");
        ps.setString(1,name);
        ps.setString(2,pass);
        ResultSet rs=ps.executeQuery(); 
        String dis="";
        if (rs.next()) {
          dis = rs.getString("diesase");
      }
        String dep="";
        switch(dis){
          case "Asthma/cold/general flu":
          dep="immunologist";
          break;
          case "Diabets":
          dep="Endocrinologist";
          break;
          case "Heart Disease":
          dep="Cardiologist";
          break;
          case "Cancer":
          dep="Oncologist";
          break;
          case "High Blood Pressure":
          dep="Cardiologist";
          break;
          case "Mental Health Disorder":
          dep="Psychiatrists";
          break;
          case "Gastrointestinal issue(Stomach)":
          dep="Gastroenterologist";
          break;
          case"Any Other":
          dep="General Practitioner";
          break;
        }
        PreparedStatement ps1=con.prepareStatement("select docname,d_id from doctor where speciality=?");
        ps1.setString(1, dep);
        ResultSet rs1=ps1.executeQuery();
        System.out.println("doctors for your diesase are:");
        while(rs1.next()){
        System.out.print(rs1.getInt("d_id")+". "+rs1.getString("docname"));
        System.out.println();
        }
        System.out.println("--------------------------------------------------\n");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
     public void bApp(){
      Connection con=Connection1.pconect();
      Scanner sc=new Scanner(System.in);
      dView();
      System.out.println("Enter your id");
      int Pid=sc.nextInt();
      System.out.println("enter doctor id you want book appointment with");  
      int id=sc.nextInt();    
      System.out.print("choose the date(yyyy/mm/dd)formate");
      String Date=sc.next();
      System.out.println("choose the slot 1.morning\n2.evening");
      int slot1=sc.nextInt();
      String slot="";
      switch(slot1){
        case 1:
        slot="morning 9'o clock";
        break;
        case 2:
        slot="Evening 5'o clock";
        break;
        default:
        System.out.println("please enter the right option from above");
      }
     try{ PreparedStatement ps=con.prepareStatement("update patient set doc_id=? where name=?");
     System.out.println("please wait");
      ps.setInt(1,id);
      ps.setString(2,name);
      ps.executeUpdate();
      ps=con.prepareStatement("insert into appointment values(?,?,?,?,?,?)");
      ps.setInt(1,id);
      ps.setString(2,name);
      ps.setString(3,Date);
      ps.setString(4,slot);
      ps.setString(5,pass);
      ps.setInt(6,Pid);
      ps.executeUpdate();
      System.out.println("appointment booked succesfully");
      System.out.println("--------------------------------------------------\n");
    }catch(Exception e){
      System.out.println(e);
    }
    sc.close();
    }
   private void vApp(){
    Connection con=Connection1.pconect();
    try{
    PreparedStatement ps=con.prepareStatement("select appointment.name,appointment.Date,appointment.slot,doctor.docname from appointment inner join doctor on doctor.d_id=appointment.D_id where name=? ");
    ps.setString(1,name);
    ResultSet rs=ps.executeQuery();
    if(rs.next()){
      System.out.println("patient's name: "+rs.getString("appointment.name")+"\ndate: "+rs.getString("appointment.Date")+"\nslot: "+rs.getString("appointment.slot")+"\ndoctor's name "+rs.getString("doctor.docname"));
    }else{
      System.out.println("Something went wrong");
    }
    System.out.println("--------------------------------------------------\n");
    }catch(Exception e){
      System.out.println(e);
    }
   }
   private void vReport(){
       Connection con =Connection1.pconect();
       try {
        PreparedStatement ps=con.prepareStatement("select p_id from patient where name=? and pass = ?");
        ps.setString(1,name);
        ps.setString(2,pass);
        ResultSet rs1=ps.executeQuery();
        int p_id=0;
        if(rs1.next()){
          p_id=rs1.getInt("p_id");
        }
        ps=con.prepareStatement("select reports.p_id,reports.d_id,reports.disease,patient.name,patient.age,patient.address from reports left join patient on patient.p_id=reports.p_id where reports.p_id=?");
        ps.setInt(1,p_id);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
          System.out.println("patient's id"+rs.getInt("reports.p_id")+" doctor's id"+rs.getInt("reports.d_id")+"reports:"+rs.getString("reports.disease")+"name:"+rs.getString("patient.name")+"age"+rs.getInt("patient.age")+" address"+rs.getString("patient.address"));
        }else{
          System.out.println("you don't have any reports");
        }
        System.out.println("--------------------------------------------------\n");
       } catch (Exception e) {
        e.printStackTrace();
       }
   }
  }
