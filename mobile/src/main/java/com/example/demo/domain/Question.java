package com.example.demo.domain;

public class Question {
	private String id;
	private String content;
	private String answer;
	private String testId;
	private String score;

	/**
	 * @param id
	 * @param content
	 * @param answer
	 * @param testId
	 * @param score
	 */
	public Question(String id, String content, String answer, String testId, String score) {
		super();
		this.id = id;
		this.content = content;
		this.answer = answer;
		this.testId = testId;
		this.score = score;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @return the testId
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @param testId the testId to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", content=" + content + ", answer=" + answer + ", testId=" + testId + "]";
	}

}
