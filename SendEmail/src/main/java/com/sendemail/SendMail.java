package com.sendemail;
import java.util.*;
import java.util.Random;
import java.util.Arrays; 
import java.util.List; 
import java.util.stream.Collectors; 
import java.util.stream.IntStream; 

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	
	public static String[] shuffleStringArray(String[] ar) {
		Random rand = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			String a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}
	
	public static int[] shuffleIntArray(int[] ar) {
		Random rand = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
		return ar;
	}
	
	//public static HashMap<String,String> validHashMapppp(String[] to, String[] pngPaths){
		//HashMap<String,String> hs = new HashMap<String,String>();
		
		//String to2[] = shuffleStringArray(to);
       // String pngPath2[] = shuffleStringArray(pngPaths);

        
        //for (int i = 0; i < 8; i++) {
        	//String to3 = to2[i];
        	//String png3 = pngPath2[i];
        	//for (int j = 0; j < 8; j++) {
        		//if (png3.equals(pngPaths[j]) && to3.equals(to[j])) {
        			//StackOverflow
        			//hs = validHashMap(to2,pngPath2);
        		//}
        	//}
        	//hs.put(to2[i], pngPath2[i]);
        //}
		
		//return hs;
	//}
	
	public static HashMap<String,String> validHashMap(String[] toInput, String[] pngPathsInput){
		HashMap<String,String> hm = new HashMap<String,String>();
		
		boolean valid = true;
		String to[] = toInput;
		String pngPaths[] = pngPathsInput;
		
		ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7));
		int indexes[] = {0,1,2,3,4,5,6,7};
		int randomIndexes[] = shuffleIntArray(indexes);
		while(valid) {
			if (randomIndexes[7] == 7) {
				randomIndexes = shuffleIntArray(randomIndexes);
			} else valid = false;
		}
		
		Stack<Integer> s = new Stack<Integer>();
		for (int i = 0; i < 8; i++) {
			s.push(randomIndexes[i]);
		}
		
			while (!s.empty()){
				String keyValue = to[arr.get(0)];
				
				if (arr.get(0) == s.peek()){
					if (arr.size() > 1){
					hm.put(to[arr.get(1)], pngPaths[s.pop()]);
					arr.remove(1);
					} else {
						hm.put(to[arr.get(0)], pngPaths[s.pop()]);
						arr.remove(0);
					}
				} else {
					hm.put(keyValue, pngPaths[s.pop()]);
					arr.remove(0);
				}
			}
		return hm;
	}
	

    public static void main(String[] args) {
    	// Recipient's email ID needs to be mentioned.
    	String to[] = {"diegogacoga@hotmail.com","papia1999@hotmail.com","calvo.rodri.cobo@hotmail.com","alonso.rdzx@gmail.com","perezcamarenadiego@gmail.com","johnfborb@gmail.com","je.floresrion@gmail.com","zatarainrodrigo@gmail.com"};
        String pngPath[] = {"C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\DieguitoFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\CharlieFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\CalvoFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\LonchoFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\PerezFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\JohnFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\EmiFOTO.PNG","C:\\Users\\papia\\Eclipse Unziped\\eclipse\\Bizarre Code\\RoyFOTO.PNG"};
        HashMap<String,String> hm = new HashMap<String,String>();
        hm = validHashMap(to,pngPath);
        List<String> l = new ArrayList<String>(hm.keySet());
        // Sender's email ID needs to be mentioned
        String from = "charlesbueso@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        for (int i = 0; i < 8; i++) {
        // Get the Session object.// and pass 
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("charlesbueso@gmail.com", "Charly6265.");

            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           //  Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(l.get(i)));

           // Set Subject: header field
            message.setSubject("Intercambio Navideño Turtlerangers!");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

           MimeBodyPart textPart = new MimeBodyPart();

            try {

               File f =new File(hm.get(l.get(i)));

                attachmentPart.attachFile(f);
                textPart.setText("");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
             //Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } try {
        	Thread.sleep(10000);
        }catch(InterruptedException ex) {
        	System.out.println("Interruption failed.");
        }
    }
    }
}