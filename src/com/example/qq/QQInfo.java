package com.example.qq;

public class QQInfo {
	private String conclusion;
	private String analysis;
	
	public QQInfo(String conclusion, String analysis) {
		super();
		this.conclusion = conclusion;
		this.analysis = analysis;
	}
	public QQInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	@Override
	public String toString() {
		return "QQInfo [conclusion=" + conclusion + ", analysis=" + analysis
				+ "]";
	}
}
