package com.example.demodocker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

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
	
	@GetMapping("/iamrole")
	public String iamrole() {
		
		System.out.println("start of iam role test");
		String awsRegion = "us-east-1";
                String roleARN = "arn:aws:iam::530190180220:role/meh-ocp-dev-project-a-role";
                String roleSessionName = "AssumeRoleWithWebIdentity";
                String bucketName = "s3://meh-ocp-dev-project-a";
		
		try {
                                                
	     AssumeRoleRequest assumeRole = new AssumeRoleRequest().withRoleArn(roleARN).withRoleSessionName("AssumeRoleWithWebIdentity");
	     System.out.println("assumeRole: " + assumeRole);

             AWSSecurityTokenService sts = AWSSecurityTokenServiceClientBuilder.standard().withRegion(awsRegion).build();
	     System.out.println("sts: " + sts);
			
			
              Credentials credentials = sts.assumeRole(assumeRole).getCredentials();
	     System.out.println("credentials: " + credentials);

             BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                credentials.getAccessKeyId(),
                credentials.getSecretAccessKey(),
                credentials.getSessionToken());
	       
			
	    System.out.println("sessionCredentials: " + sessionCredentials.getSessionToken());

            // Provide temporary security credentials so that the Amazon S3 client 
	    // can send authenticated requests to Amazon S3. You create the client 
	    // using the sessionCredentials object.
	    AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(awsRegion).withCredentials(new AWSStaticCredentialsProvider(sessionCredentials)).build();
			
			
            
	   System.out.println("s3Client: " + s3Client);

            // Verify that assuming the role worked and the permissions are set correctly
            // by getting a set of object keys from the bucket.
            ObjectListing objects = s3Client.listObjects(bucketName);
            System.out.println("No. of Objects: " + objects.getObjectSummaries().size());
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
		
		
		
		
		return "end of iamrole test";
	}
	
	
	
	@GetMapping("/readwrite")
	public String readwrite() throws IOException {
	
	
	    String str = "File Handling in Java using "+ 
                " FileWriter and FileReader"; 
  
        // attach a file to FileWriter  
        FileWriter fw=new FileWriter("/var/output/output.txt"); 
  
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
