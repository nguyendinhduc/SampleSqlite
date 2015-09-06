package com.nac.bai18_ailatrieuphu;

public class Question {
	private String question;
	private String caseA;
	private String caseB;
	private String caseC;
	private String caseD;
	private int trueCase;

	public Question() {

	}

	public Question(String question, String caseA, String caseB, String caseC,
			String caseD, int trueCase) {
		super();
		this.question = question;
		this.caseA = caseA;
		this.caseB = caseB;
		this.caseC = caseC;
		this.caseD = caseD;
		this.trueCase = trueCase;
	}

	public String getQuestion() {
		return question;
	}

	public String getCaseA() {
		return caseA;
	}

	public String getCaseB() {
		return caseB;
	}

	public String getCaseC() {
		return caseC;
	}

	public String getCaseD() {
		return caseD;
	}

	public int getTrueCase() {
		return trueCase;
	}

}
