package the5concurrentnodes.rest;

import java.util.Random;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Email {
        static DataInputStream is;
        static DataOutputStream os;
        static int PORT =25;
        
        public static String generate(){
            
            int randomNum = 1000 + (int)(Math.random() * ((9999 - 1000) + 1));
            Random r = new Random();
            String letters="";
            String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            
            for (int i = 0; i < 2; i++) {
                letters += Character.toString(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            
            letters += Integer.toString(randomNum);
  
            for (int i = 0; i < 2; i++) {
                letters += Character.toString(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            
            return letters;
        }
        
        public static String sendEmail(String to, String code) {
            try {
                Socket client =new Socket("kendy.up.ac.za", PORT);
                System.out.println("Connected? Getting streams");
                os =new DataOutputStream(client.getOutputStream());
                is =new DataInputStream(client.getInputStream());
                System.out.println("--Input: "+is.readLine());
                write("HELO tuks.co.za");
                System.out.println("--Input: "+is.readLine());
                
                String success =send(to, code);
                
                write("QUIT");
                System.out.println("--Input: "+is.readLine());
                return success;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "FAIL";
        }
        
        private static String send(String to, String code) throws Exception {
            write("MAIL FROM:<passwordRecover@eyeCrawler.co.za>");
            System.out.println("--Input: "+is.readLine());
            write("RCPT TO:<"+to+">");
           System.out.println("--Input: "+is.readLine());
            write("DATA");
            System.out.println("--Input: "+is.readLine());
            write("From: passwordRecover@eyeCrawler.co.za");
            write("To: "+to);
            write("Subject: PasswordRecover");
            write("Dear user");
            write("");
            write("This is your new password");
            write("Code: "+code);
            write("");
            write("");
            write("Kind Regards,");
            write(" eyeCrawler");
            write(".");
            String response =is.readLine();
            System.out.println("---Input: "+response);
            response =response.split(" ",2)[1];
            if (response.startsWith("OK")) {
                return code;
            }
            return "FAIL";
        }
        
        static void write(String line) throws Exception {
            os.writeBytes(line+"\n");
        }
    }
