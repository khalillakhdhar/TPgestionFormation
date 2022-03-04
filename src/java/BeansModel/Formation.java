/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeansModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author khali
 */
@ManagedBean
@RequestScoped
public class Formation {
   private int id;
   private String description;
   private double prix;
   private String titre;
Connection connection;
    ArrayList<Formation> listFormations;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    // scripts de connexion à la BD
     public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");   //initialiser
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/formation","root",""); //chaine(drive=jdbc:mysql, url="localhost:3306/base,username=root,password="")
            System.out.println("connecté");
        }catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
     
      public List<Formation> showList()
      {
          try
          {
               listFormations=new ArrayList<Formation>();
          //connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from formation"); 
            while(rs.next()) //tant que rs à des valeurs
            {
                Formation f= new Formation();
                f.setTitre(rs.getString("titre"));
                f.setDescription(rs.getString("description"));
                f.setPrix(rs.getDouble("prix"));
                f.setId(rs.getInt("id"));
                
                listFormations.add(f);
                
            }
      
                  connection.close();        

      
          }
          catch(Exception ex)
          {
          }
                  //Formateur f2=new Formateur("nom2", "prenom2", "email2", "mdp2", "grade2", "tel2", 30, 2020);

         // listFormateurs.add(f2);
      
      return listFormations;
      
      }
      
      public String addFormation()
      {
       connection = getConnection();
         int result = 0;
        try{
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `formation` (`titre`, `description`, `prix`) VALUES (?,?,?)");
            stmt.setString(1, this.titre);
            stmt.setString(2, this.description);
            stmt.setDouble(3, this.prix);
           
            result = stmt.executeUpdate();
                        connection.close(); //optionelle

            System.out.println("ajouté!");
            
         } catch(Exception e){
            System.out.println(e);
        }
             if(result !=0)
            return "formations.xhtml?faces-redirect=true";
        else return "index.xhtml?faces-redirect=true";
    
      
      
      
      
      }
      
      public void deleteFormation(int id)
       {
    try
    {
    connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from formation where id = "+id);  
            stmt.executeUpdate();  
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    
    }
    
    
    }
    
    
}
