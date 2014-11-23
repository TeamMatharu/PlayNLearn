package com.playnlearn.classes;

public class Question {
	private   int Question_ID ;
	private   String Question_Text;
	private   String option1 ;
	private   String option2 ;
	private   String option3 ;
	private   String option4 ;
	private   String Answer;
	private   String Question_Comment;
	
	
	
	public int getQuestion_ID() {
		return Question_ID;
	}
	public void setQuestion_ID(int question_ID) {
		Question_ID = question_ID;
	}
	public String getQuestion_Text() {
		return Question_Text;
	}
	public void setQuestion_Text(String question_Text) {
		Question_Text = question_Text;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	public String getQuestion_Comment() {
		return Question_Comment;
	}
	public void setQuestion_Comment(String question_Comment) {
		Question_Comment = question_Comment;
	}
	
	
	
}
