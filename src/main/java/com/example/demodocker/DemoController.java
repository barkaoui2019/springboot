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
	public String greetingHello() {
		
		System.out.println("start of iam role test");
		String clientRegion = "us-east-1";
                String roleARN = "*** ARN for role to be assumed ***";
                String roleSessionName = "session1";
                String bucketName = "s3://meh-ocp-dev-project-a";
		
		try {
            // Creating the STS client is part of your trusted code. It has
            // the security credentials you use to obtain temporary security credentials.
            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                                                    .withCredentials(new ProfileCredentialsProvider())
                                                    .withRegion(clientRegion)
                                                    .build();

            // Obtain credentials for the IAM role. Note that you cannot assume the role of an AWS root account;
            // Amazon S3 will deny access. You must use credentials for an IAM user or an IAM role.
            AssumeRoleRequest roleRequest = new AssumeRoleRequest()
                                                    .withRoleArn(roleARN)
                                                    .withRoleSessionName(roleSessionName);
            AssumeRoleResult roleResponse = stsClient.assumeRole(roleRequest);
            Credentials sessionCredentials = roleResponse.getCredentials();
            
            // Create a BasicSessionCredentials object that contains the credentials you just retrieved.
            BasicSessionCredentials awsCredentials = new BasicSessionCredentials(
                    sessionCredentials.getAccessKeyId(),
                    sessionCredentials.getSecretAccessKey(),
                    sessionCredentials.getSessionToken());

            // Provide temporary security credentials so that the Amazon S3 client 
	    // can send authenticated requests to Amazon S3. You create the client 
	    // using the sessionCredentials object.
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                                    .withRegion(clientRegion)
                                    .build();

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
