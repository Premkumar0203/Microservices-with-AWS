package com.demo.handlers;

import java.sql.Connection;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.demo.models.BookPurchase;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MyLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	List<BookPurchase> FetchBookpurchaseList() {
		List<BookPurchase> list = new ArrayList<BookPurchase>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://database-1.cps0awvmyr6y.ap-south-1.rds.amazonaws.com:3306/ReaderDb",
					"admin", "mypassword");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from book_purchase");
			while (rs.next())
			{
				BookPurchase b= new BookPurchase();
				b.setBookId(rs.getInt("book_id"));
				b.setEmailId(rs.getString("email_id"));
				b.setName(rs.getString("name"));
				list.add(b);
				
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return list;
	}
		
	void sendMail(String toMailId,String content)
	{
	      String from = "premkumarmar02@gmail.com";
	      String host = "localhost";
	      Properties props = System.getProperties(); 
	      props.put("mail.smtp.host", "smtp.gmail.com");
          props.put("mail.smtp.socketFactory.port", "465");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.prot", "465");   
          Session session = Session.getDefaultInstance(props,    
                  new javax.mail.Authenticator() {    
                  protected PasswordAuthentication getPasswordAuthentication() {    
                  return new PasswordAuthentication(from,"balifrydzmayxojo");  
                  }    
                 });  
	      
		try{  
			
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMailId));  
	         message.setSubject("Book Availability Status");  
	         message.setText(content);  
	  	     Transport.send(message);  
	         System.out.println("message sent successfully....");  
	  
	      }
		catch (MessagingException mex)
		{
			mex.printStackTrace();
		}  
	}

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
	{
		List<BookPurchase> list = FetchBookpurchaseList();
		List<String> emailList = new ArrayList<String>();
		String content = null;
		try {
			int bookId = event.getQueryStringParameters().get("bookId") != null ? Integer.parseInt(event.getQueryStringParameters().get("bookId")) : 0;
			String title = event.getQueryStringParameters().get("title") != null ? 
					event.getQueryStringParameters().get("title") : null;
			int status = event.getQueryStringParameters().get("status") != null 
					? Integer.parseInt(event.getQueryStringParameters().get("status")) : 0;

			if(bookId > 0 && title != null && status > 0 )
			{
				content =  "The "+ title + " Book is"+
						(status == 1 ? " Available Now." : " not available here after.");
				
				emailList = list.stream().filter( b -> b.getBookId() == bookId)
							.map(b-> b.getEmailId()).
							collect(Collectors.toList());
				if(emailList.isEmpty())
				{
					
					return new APIGatewayProxyResponseEvent().withBody("Success").withStatusCode(200);			
				}
				else
				{
					for(String mailId: emailList)
					{
						sendMail(mailId,content);
					}
					return new APIGatewayProxyResponseEvent().withBody(String.join(",", emailList)).withStatusCode(200);			

				}
				
			}
			return new APIGatewayProxyResponseEvent().withBody("Pass Query Parameter BookId ").withStatusCode(200);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new APIGatewayProxyResponseEvent().withBody(e.getMessage()+list).withStatusCode(200);
		}
	}

}
