package com.verizon.maven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MavenPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MavenPracticeApplication.class, args);
	  }
	
	@RestController
	@RequestMapping(value="/")
	public class Controller {
		
		@RequestMapping(value="/get",method=RequestMethod.GET)
		public List<String> getUsers() {
			List<String> result = new ArrayList<String>();
			try
		    {
		      // create our mysql database connection
		      String myDriver = "com.mysql.jdbc.Driver";
		      String myUrl = "jdbc:mysql://localhost:3306/nytestdb?useSSL=false";
		      Class.forName(myDriver);
		      Connection conn = DriverManager.getConnection(myUrl, "root", "admin");
		      
		      // our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "SELECT * FROM users";

		      // create the java statement
		      Statement st = conn.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs = st.executeQuery(query);
		      
		      // iterate through the java resultset
		      while (rs.next())
		      {
		        int id = rs.getInt("id");
		        String firstName = rs.getString("fname");
		        String lastName = rs.getString("lname");
		        String email = rs.getString("email");
		        
		        // print the results
		        // System.out.format("%d %s, %s, %s\n", id, firstName, lastName, email);
		        result.add(Integer.toString(id)+firstName+lastName+email);  
		      }
		      st.close();
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
			return result;
		}
	}
	
}
