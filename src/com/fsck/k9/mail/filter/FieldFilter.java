/*
 * This class contains functionality to scan any header field for a pattern, and if
 * there is a match, to move the message to that destination folder.
 */
package com.fsck.k9.mail.filter;

import android.util.Log;

import com.fsck.k9.Account;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.Message.RecipientType;
import com.fsck.k9.mail.MessagingException;

public class FieldFilter {
	protected String mField;
	protected String mPattern;
	protected String mFolder;

	public FieldFilter(String field, String pattern) {
		mField = field;
		mPattern = pattern;
		mFolder = "Trash";
	}
	public FieldFilter(String field, String pattern, String folder) {
		mField = field;
		mPattern = pattern;
		mFolder = folder;
	}
	public boolean runFilter(MessagingController control, Account account, Message message) {		
		if (mField.equalsIgnoreCase("Subject")) {
			// Log.d("K9-Mail", "Checking Subject: " + message.getSubject());
			if (message.getSubject().matches(mPattern)) {
				Log.d("K9-Mail", "Matched Subject: " + message.getSubject());
				String inbox = account.getInboxFolderName();
				control.moveMessage(account, inbox, message, mFolder, null);
				return true;
			}
		} else if (mField.equalsIgnoreCase("To")) {
			try {
				Address[] addresses = message.getRecipients(RecipientType.TO);
		        for (Address address : addresses) {
					// Log.d("K9-Mail", "checking To Address: " + address.getAddress());
		        	if (address.toString().matches(mPattern)) {
		        		Log.d("K9-Mail", "Matched To: " + address.toString());
		        		String inbox = account.getInboxFolderName();
		        		control.moveMessage(account, inbox, message, mFolder, null);
		        		return true;
		        	}
		        }
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (mField.equalsIgnoreCase("Cc")) {
			try {
				Address[] addresses = message.getRecipients(RecipientType.CC);
				for (Address address : addresses) {
					// Log.d("K9-Mail", "checking Cc Address: " + address.getAddress());
					if (address.toString().matches(mPattern)) {
						Log.d("K9-Mail", "Matched Cc: " + address.toString());
						String inbox = account.getInboxFolderName();
						control.moveMessage(account, inbox, message, mFolder, null);
						return true;
					}
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (mField.equalsIgnoreCase("From")) {
				Address[] addresses = message.getFrom();
		        for (Address address : addresses) {
					// Log.d("K9-Mail", "checking From Address: " + address.getAddress());
		        	if (address.toString().matches(mPattern)) {
		        		Log.d("K9-Mail", "Matched To: " + address.toString());
		        		String inbox = account.getInboxFolderName();
		        		control.moveMessage(account, inbox, message, mFolder, null);
		        		return true;
		        	}
		        }
			}
		return false;
	}
	
	public void setPattern(String pattern) {
		mPattern = pattern;
	}
	public void setfield(String field) {
		mField = field;
	}
	public void setFolder(String folder) {
		mFolder = folder;
	}
}

