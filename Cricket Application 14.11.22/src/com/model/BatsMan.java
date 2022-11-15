package com.model;

public class BatsMan {
	private String name;
	private String Status;
	private int runs, balls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBalls(int balls) {
		this.balls += balls;
	}

	public void addRuns(int runs) {
		this.runs += runs;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public int getBalls() {
		return balls;
	}

	public void setBalls(int ball) {
		this.balls = ball;
	}

}
