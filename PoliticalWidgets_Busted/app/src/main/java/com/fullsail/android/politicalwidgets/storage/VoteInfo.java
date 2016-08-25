package com.fullsail.android.politicalwidgets.storage;

import java.io.Serializable;

public class VoteInfo implements Serializable {

	private static final long serialVersionUID = -8823944060928021588L;
	
	private int mId;
	private String mQuestion;
	private String mVote;
	
	public VoteInfo() {
		mId = -1;
		mQuestion = mVote = "";
	}
	
	public VoteInfo(int id, String question, String vote) {
		mId = id;
		mQuestion = question;
		mVote = vote;
	}
	
	public void setId(int id) {
		mId = id;
	}
	
	public int getId() {
		return mId;
	}
	
	public void setQuestion(String question) {
		mQuestion = question;
	}
	
	public String getQuestion() {
		return mQuestion;
	}
	
	public void setVote(String vote) {
		mVote = vote;
	}
	
	public String getVote() {
		return mVote;
	}
}