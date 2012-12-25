/*
 * This class contains functionality to scan any header field for a pattern, and if
 * there is a match, to move the message to that destination folder.
 */
package com.fsck.k9.mail.filter;

import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.Message.RecipientType;
import com.fsck.k9.mail.MessagingException;

public class Filter {
	protected String mField;
	protected String mPattern;
	protected String mFolder;

	public Filter(String field, String pattern) {
		mField = field;
		mPattern = pattern;
		mFolder = "Trash";
	}
	public Filter(String field, String pattern, String folder) {
		mField = field;
		mPattern = pattern;
		mFolder = folder;
	}
	public boolean runFilter(Message message) {
		if (mField.equalsIgnoreCase("Subject")) {
			return message.getSubject().matches(mPattern);			
		} else if (mField.equalsIgnoreCase("To")) {
			try {
				Address[] addresses = message.getRecipients(RecipientType.TO);
		        for (Address address : addresses) {
		        	if (address.toString().matches(mPattern)) {
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
					if (address.toString().matches(mPattern)) {
						return true;
					}
				}
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (mField.equalsIgnoreCase("From")) {
				return message.getFrom().toString().matches(mPattern);
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