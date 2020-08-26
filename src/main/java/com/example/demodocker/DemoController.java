package com.example.demodocker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter; 
import java.io.IOException; 

@RestController
public class DemoController {
	
	/*
	
	@Value("${security.user.name}")
    private String username; 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Value("${security.user.password}")
    private String password; 
	
	*/
    
   @Value("${greeter.message}")
    private String greeterMessageFormat;
	
	
	@Value("${message}")
    private String message;

	@GetMapping("/hello")
	public String greeting() {
		
		String prefix = System.getenv().getOrDefault("GREETING_PREFIX", "Hi");
                System.out.println("  prefix" +  prefix);
		
		System.out.println("greeterMessageFormat" + greeterMessageFormat);
		System.out.println("message" + message);
		
		
		
		return "Hello World 1";
	}

	@GetMapping("/")
	public String greetingHello() {
		
		//System.out.println("password" + getPassword());
	    //System.out.println("username" + getUsername());
		
		
		System.out.println("greeterMessageFormat" + greeterMessageFormat);
		System.out.println("message" + message);
		
		return "Hello World Application 1";
	}
	
	@GetMapping("/readwrite")
	public String readwrite() throws IOException {
	
	
	    String str = "File Handling in Java using "+ 
                " FileWriter and FileReader"; 
  
        // attach a file to FileWriter  
        FileWriter fw=new FileWriter("/efs/test/output.txt"); 
  
        // read character wise from string and write  
        // into FileWriter  
        for (int i = 0; i < str.length(); i++) 
            fw.write(str.charAt(i)); 
  
        System.out.println("Writing successful");
        System.out.println("Writing successful"); 
        //close the file  
        
       
        fw.close(); 
	
        
        //
        int ch; 
        
        // check if File exists or not 
        FileReader fr=null; 
        try
        { 
            fr = new FileReader("/efs/test/output.txt"); 
        } 
        catch (FileNotFoundException fe) 
        { 
            System.out.println("File not found"); 
        } 
  
        StringBuffer sb ;
        // read from FileReader till the end of file 
        while ((ch=fr.read())!=-1) 
        {
        	System.out.println("inside read block");
            System.out.print((char)ch); 
           
            
        }
  
        // close the file 
        fr.close(); 
        
        
		return "readwrite";
		
	}
	
	@GetMapping("/read")
	public String read() throws IOException {
	
	
	    String str = "File Handling in Java using "+ 
                " FileWriter and FileReader"; 
  
        
	
        
        //
        int ch; 
        
        // check if File exists or not 
        FileReader fr=null; 
        try
        { 
            fr = new FileReader("/efs/test/output.txt"); 
        } 
        catch (FileNotFoundException fe) 
        { 
            System.out.println("File not found"); 
        } 
  
        StringBuffer sb ;
        // read from FileReader till the end of file 
        while ((ch=fr.read())!=-1) 
        {
        	System.out.println("inside read block");
            System.out.print((char)ch); 
           
            
        }
  
        // close the file 
        fr.close(); 
        
        
		return "read";
		
	}

}
