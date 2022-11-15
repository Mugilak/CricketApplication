package com.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	private String teamName;
	private List<BatsMan> batsmen;
	private List<Bowler> bowlers;

	public Team() {
		batsmen = new ArrayList<>();
		bowlers = new ArrayList<>();
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<BatsMan> getBatsmen() {
		return batsmen;
	}

	public void setBatsmen(BatsMan batsmen) {
		this.batsmen.add(batsmen);
	}

	public List<Bowler> getBowlers() {
		return bowlers;
	}

	public void setBowlers(Bowler bowlers) {
		this.bowlers.add(bowlers);
	}

}
