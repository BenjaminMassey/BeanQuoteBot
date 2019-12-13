package bqb;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.io.IOException;
import java.util.ArrayList;

public class TwitchChat extends PircBot {

	// A part of BeanBetBot
	// Copyright 2019 Ben Massey
	// https://github.com/BenjaminMassey/BeanBoyBot

	// This class handles actually reading from and talking to Twitch Chat

	// Code for Twitch Chat functionality based on tutorial from this channel:
	// https://www.youtube.com/channel/UCoQuKOXYxUBeNWbendo3sF

	public static boolean connected = false;
	private static String channel; // What channel the bot should talk to/read from
	private static TwitchChat bot;
	//private static bbb.TwitchChat whisperBot;

	public TwitchChat() {
		// Quick mini setup

		this.setName(AccountsManager.getBotName());
		this.isConnected();
	}

	public static void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		// Set up the Twitch Bot to be in chat
		channel = AccountsManager.getChatChannel();
		connected = true;
		bot = new TwitchChat();
		bot.setVerbose(true);
		bot.connect("irc.twitch.tv", 6667, AccountsManager.getBotOauth());
		bot.sendRawLine("CAP REQ :twitch.tv/membership"); // Allows special stuff (viewer list)
		// Below is common permission but I don't need it yet
		//bot.sendRawLine("CAP REQ :twitch.tv/tags");
		bot.sendRawLine("CAP REQ :twitch.tv/commands"); // Need it to parse whispers
		bot.joinChannel(channel);
	}

	public static void deactivate() throws IOException, IrcException {
		bot.disconnect();
		connected = false;
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		// React to a given message
		
		// Here are the commands that should be taken action for

		if (message.startsWith("!addquote ")) {
			QuotesHandler.addQuote(message);
			messageChat("Added your quote! (#" +
					(FileHandler.getFileLength("Quotes") - 1)+ ")");
		}

		if (message.startsWith("!searchquotes ")) {
			ArrayList<Integer> quoteNumbers = QuoteSearcher.searchThroughQuote(message); // Gets integer arraylist with all quote #s with the query
			messageChat("Here is a list of quotes that match your query:");
			String quoteList = "";
			//Takes the integers and convert them to string, and then add them to the quote list
			for(Integer number : quoteNumbers) { 
				String num = String.valueOf(number.intValue());
				quoteList = quoteList + num + ", ";
			}
			messageChat(quoteList);
		}



		if (message.startsWith("!quote"))
			messageChat(QuotesHandler.getQuote(message));

		if (message.startsWith("!delquote ")) {
			boolean deleted = QuotesHandler.delQuote(message);
			if (deleted)
				messageChat("Deleted!");
			else
				messageChat("Failed to delete... D:");
		}

	}

	protected void onUnknown(String line) {}

	public static String[] getViewers() {
		try {
			User[] users = bot.getUsers(channel);
			String[] viewers = new String[users.length];
			for(int i = 0; i < users.length; i++)
				viewers[i] = users[i].getNick();
			return viewers;
		}catch(Exception e) {
			return new String[0];
		}
	}

	public static void outsideMessage(String message) {
		bot.messageChat(message);
	}
	
	public static void outsidePM(String person, String message) {
		bot.privateMessage(person, message);
	}
	
	private void privateMessage(String person, String message) {
		messageChat("/w " + person + " " + message);
	}

	private void messageChat(String message) {
		sendMessage(channel, message);
	} // Simply puts a string in chat

}
