package com.yblee.mqcouch.email;

import javax.swing.SwingWorker;

public class EmailSenderSwingWorker extends SwingWorker<Boolean, String> {

	private String strRef, from, to, title, content;
	private String authUser, authPass;
	
	public EmailSenderSwingWorker(String from, String to, String title, String content) {
		this.from = from;
		this.to = to;
		this.title = title;
		this.content = content;
	}
	
	public void setStrRef(String strRef) {
		this.strRef = strRef;
	}
	
	public void setAuthUserAndPass(String authUser, String authPass) {
		this.authUser = authUser;
		this.authPass = authPass;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		Boolean isSent = null;
		try {
			System.out.println("sending emails...");
			
			SendEmailAWS awsEmailer = new SendEmailAWS();
			isSent = awsEmailer.sendEmailSSL(from, to, title, content);
			
			if(isSent) {
				System.out.println("sent emails....");
			} else {
				System.out.println("fail sending emails...");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSent;
	}

}
