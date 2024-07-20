import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;
public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        LocalDate today =  LocalDate.now();
        System.out.println("     Date:"+today.toString());
        System.out.println(
                "                                --------------------------WELCOME----------------------------");
        System.out.println("Do you want to");
        System.out.println("1.SignUp");
        System.out.println("2.Login");
        int i = sc.nextInt();
        if (i == 1) {
            Signup s = new Signup();
            s.signup();
        } else if (i == 2) {
            Login l = new Login();
            l.login();
        }
        System.out.println(
                "                                  ----------------------THANKYOU FOR USING-----------------------------------");
        sc.close();
    }
}

class Signup {
    public void signup() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the name:");
        String name=sc.nextLine();
        System.out.print("Enter your age:");
        int age=sc.nextInt();
        System.out.print("Enter your address:");
        String add=sc.next();
        sc.nextLine();
        System.out.print("enter your phone number:");
        long ph_no=sc.nextLong();
        System.out.print("enter the diesase:");
        System.out.println("1.Asthma/cold/general flu\n2.Diabets\n3.Heart Disease\n4.Cancer\n5.High Blood Pressure\n6.Mental Health Disorder\n7.Gastrointestinal issue(Stomach)\n8.Any Other");
        int n=sc.nextInt();
        String dis=option(n);
        System.out.print("Create a password:");
        String pass=sc.next();
        try {
          Connection con=Connection1.pconect();
          PreparedStatement ps =con.prepareStatement ("insert into patient(name,pass,address,age,phone_no,diesase,fee) values(?,?,?,?,?,?,?)");
          ps.setString(1, name);
          ps.setString(2,pass);
          ps.setString(3,add);
          ps.setInt(4,age);
          ps.setLong(5,ph_no);
          ps.setString(6,dis);
          ps.setString(7,"n");
         int m= ps.executeUpdate();
          if(m>0){
            System.out.println("signup successfully");
            Patient p=new Patient(name, pass);
            p.option();
          }
          con.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        sc.close();
    }
    private String option(int n){
        String s1="";
        switch(n){
            case 1:
            s1="Asthma/cold/general flu";
            break;
            case 2:
            s1="Diabets";
            break;
            case 3:
            s1="Heart Disease";
            break;
            case 4:
            s1="Cancer";
            break;
            case 5:
            s1="High Blood Pressure";
            break;
            case 6:
            s1="Mental Health Disorder";
            break;
            case 7:
            s1="Gastrointestinal issue(Stomach)";
            break;
            case 8:
            s1="Any Other";
            break;
    }
    return s1;
}
}

class Login {
    public void login() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Login as a ");
        System.out.println("1.Admin");
        System.out.println("2.Doctor");
        System.out.println("3.Patient");
        String l = sc.nextLine();
        switch (l) {
            case "Admin":
            case "admin":
            case "1":
                admin();
                break;
            case "Doctor":
            case "doctor":
            case "2":
                doctor();
                break;
            case "Patient":
            case "patient":
            case "3":
                patient();
                break;
            default:
                System.out.println("choose the appropiate option");
        }
        sc.close();
}
public void patient(){
    Scanner sc=new Scanner(System.in);
    try{
    System.out.print("Enter your name:");
    String name=sc.nextLine();
    System.out.print("Enter your password:");
    String pass=sc.next();
    Connection con=Connection1.pconect();
    PreparedStatement ps=con.prepareStatement("select*from patient where pass=?");
    ps.setString(1,pass);
    ResultSet rs=ps.executeQuery();
    if(rs.next()){
        System.out.println("Welcomeback "+name);
        System.out.println("Login successfully");
        System.out.println("--------------------------------------------------\n");
    }else{
        System.out.println("your record is not found in our database");
        System.out.println("you have to signup first");
        Signup s=new Signup();
        s.signup();
    }
    Patient p=new Patient(name,pass);
    p.option();
    }catch(Exception e){
        e.printStackTrace();
    }
    sc.close();
        }
private void doctor(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your doctor id:");
        int docname=sc.nextInt();
        sc.nextLine();
        System.out.print("enter your password:");
        String pass=sc.nextLine();
        try{
Connection con=Connection1.pconect();
PreparedStatement ps=con.prepareStatement("select*from doctor where password=? and d_id=?");
ps.setString(1, pass);
ps.setInt(2,docname);
ResultSet rs=ps.executeQuery();
if(rs.next()){
    System.out.println("welcomeback dr"+docname);
    Doctor d=new Doctor(docname,pass);
    d.option();
}else{
    System.out.println("record was not found");
}
System.out.println("--------------------------------------------------\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        sc.close();
    }
private void admin(){
    Scanner sc=new Scanner(System.in);
    System.out.print("Enter your name:");
     String name=sc.nextLine();
     System.out.print("Enter your password:");
     String password=sc.nextLine();
     Connection con=Connection1.pconect();
     try {
        PreparedStatement ps=con.prepareStatement("select * from admin where name=? and password=?");
        ps.setString(1,name);
        ps.setString(2, password);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            String name1=rs.getString("name");
            String pass=rs.getString("password");
            if(name1.equalsIgnoreCase(name)&&pass.equalsIgnoreCase(password)){
System.out.println("login successfully");
            }else{
                System.out.println("username not found in database");
            }
Admin a=new Admin();
a.option();
        }else{
            System.out.println("Maybe you enter wrong password or name");
        }
        System.out.println("--------------------------------------------------\n");
     } catch (Exception e) {
       e.printStackTrace();
     }
     sc.close();
}
}