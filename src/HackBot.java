import java.io.*;
import java.util.Random;
import java.net.*;

public class HackBot {

    public static void main(String[] args) throws Exception {

        // The server to connect to and our details.
        String server = "irc.freenode.net";
        String nick = "bot3";
        String login = "simple_bot";
        String[] quotes = new String[5];
        Random r = new Random();

        // The channel which the bot will join.
        String channel = "#botbotbot";
        
        // Coeqwdnnect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
        
        // Log on to the server.
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
        writer.flush( );
        
        // Read lines from the server until it tells us we have connected.
        String line = null;
        while ((line = reader.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
        
        // Join the channel.
        writer.write("JOIN " + channel + "\r\n");
        writer.flush( );
        
        // Keep reading lines from the server.
        BufferedReader kReader = new BufferedReader(new InputStreamReader(System.in));

        
        while ((line = reader.readLine( )) != null) {
        	/*
        	if (kReader.readLine() != null){
        		writer.write("PRIVMSG " + channel + " :world!\r\n");
            	writer.flush();
        	}
        	System.out.println(kReader.readLine());
        	*/
        	
        	if (line.toLowerCase( ).startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush( );
            }
            
            if (line.toLowerCase( ).contains("hello")) {
            	writer.write("PRIVMSG " + channel + " :world!\r\n");
            	writer.flush();
            }
            if (line.toLowerCase( ).contains("!quote")) {
            	int num = r.nextInt(quotes.length) + 1;
            	System.out.println(num);
            	String msg = "";
            	
            	switch (num){
	            	case 1:  msg = "I love the smell of napalm in the morning.";
	                break;
	            	case 2:  msg = "Here's looking at you, kid.";
	                break;
	            	case 3:  msg = "I'll be back.";
	                break;
	            	case 4:  msg = "Get to the chooper!";
	                break;
	            	case 5:  msg = "You're a funny guy, Sully. I like you. That's why I'm going to kill you last.";
	                break;
	            	default: msg = "Invalid month";
                    break;
                    
            	}
            	System.out.println(msg);
            	writer.write("PRIVMSG " + channel + " :" + msg + "\r\n");
            	writer.flush();
            }
            else {
                // Print the raw line received by the bot.
                System.out.println(line);
            }
        }
    }

}