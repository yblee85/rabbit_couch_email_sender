package com.yblee.mqcouch.control;

import org.ektorp.CouchDbConnector;
import org.ektorp.impl.StdCouchDbConnector;

import com.yblee.mqcouch.email.EmailSenderSwingWorker;
import com.yblee.mqcouch.model.AppModel;

public class CustomEmailSenderSwingWorker extends EmailSenderSwingWorker {
	private AppControl control;
	private String doc_id, rev;
	
	public CustomEmailSenderSwingWorker(String from, String to, String title, String content) {
		super(from, to, title, content);
		setAuthUserAndPass(AppModel.SENDER_EMAIL, AppModel.SENDER_EMAIL_PASS);
	}
	
	public void setAppControl(AppControl control) {
		this.control = control;
	}
	public void setDocIdAndRev(String docId, String rev) {
		doc_id = docId;
		this.rev = rev;
	}
	
	// customized part : what to do after send email in this case delete document
	@Override
	protected void done() {
		try {
			Boolean isEmailSent = get();
			System.out.println(isEmailSent);
			System.out.println("EMAIL SENT - " + ((isEmailSent==null||!isEmailSent)?"FAIL":"SUCCESS"));
			if(isEmailSent) deleteCouchDBDoc(doc_id, rev);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean deleteCouchDBDoc(String doc_id, String rev) {
		boolean isDeleted = false;
		CouchDbConnector dbConn = null;
		try {
			dbConn = new StdCouchDbConnector(AppModel.SERVER_COUCH_DB, control.getDbInstance());
			dbConn.delete(doc_id, rev);
			isDeleted = true;
			System.out.println("CouchDb EmailDoc Delected");
		} catch(Exception e) {
			e.printStackTrace();
			isDeleted = false;
		} finally {
			dbConn = null;
		}
		return isDeleted;
	}
}
