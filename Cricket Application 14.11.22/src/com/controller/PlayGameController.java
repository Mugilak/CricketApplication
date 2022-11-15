package com.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.model.BatsMan;
import com.model.Bowler;
import com.model.Innings;
import com.model.Team;
import com.view.StartGame;

public class PlayGameController {
	private Innings firstInning, secondInning;
	private Team battingTeam, bowlingTeam;
	private BatsMan striker, nonStriker, temp;
	private Bowler bowler, currentBowler, nextBowler, tempB;
	private List<String> list;
	private String output;
	private StartGame start;
	private static int target = Integer.MAX_VALUE;

	public PlayGameController(StartGame startGame) {
		start = startGame;
	}

	public void setInput(Queue<List<String>> input) {
		System.out.println(input);
		list = input.poll();
		battingTeam = new Team();
		battingTeam.setTeamName(list.get(0));
		bowlingTeam = new Team();
		bowlingTeam.setTeamName(list.get(1));
		firstInning = new Innings();
		list = input.poll();
		for (int i = 0; i < 3; i++) {
			BatsMan batsman = new BatsMan();
			batsman.setName(list.get(i));
			battingTeam.setBatsmen(batsman);
		}
		for (int i = 3; i < 5; i++) {
			bowler = new Bowler();
			bowler.setName(list.get(i));
			battingTeam.setBowlers(bowler);
		}
		list = input.poll();
		for (int i = 0; i < 3; i++) {
			BatsMan batsman = new BatsMan();
			batsman.setName(list.get(i));
			bowlingTeam.setBatsmen(batsman);
		}
		for (int i = 3; i < 5; i++) {
			bowler = new Bowler();
			bowler.setName(list.get(i));
			bowlingTeam.setBowlers(bowler);
		}
		firstInning.setBattingTeam(battingTeam);
		firstInning.setBowlingTeam(bowlingTeam);
		playGame(firstInning, input, target);
		secondInning = new Innings();
		secondInning.setBattingTeam(firstInning.getBowlingTeam());
		secondInning.setBowlingTeam(firstInning.getBattingTeam());
		playGame(secondInning, input, firstInning.getTotalRuns());
	}

	private void playGame(Innings currentInning, Queue<List<String>> input, int target) {
		int wickets = 0, runs = 0, totalRuns = 0, extras = 0, balls = 0;
		String run = "";
		List<BatsMan> batsmen = currentInning.getBattingTeam().getBatsmen();
		List<Bowler> bowlers = currentInning.getBowlingTeam().getBowlers();
		striker = batsmen.get(0);
		striker.setStatus("In");
		currentBowler = bowlers.get(0);
		nextBowler = bowlers.get(1);
		nonStriker = batsmen.get(1);
		nonStriker.setStatus("In");
		list = input.poll();
		Queue<String> over = new LinkedList<>(list);
		while (over != null) {
			run = over.poll();
			runs = 0;
			if (run.matches("[0-9]+$")) {
				runs = Integer.valueOf(run);
				currentBowler.setRuns(currentBowler.getRuns() + runs);
				striker.addBalls(1);
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				striker.addRuns(runs);
				balls++;
			} else if (run.matches("W")) {
				wickets++;
				balls++;
				striker.addBalls(1);
				striker.setStatus("Out");
				striker = batsmen.get(2);
				striker.setStatus("In");
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				currentBowler.setWicket(currentBowler.getWicket() + 1);
			} else if (run.matches("^[0-9]*Wd$")) {
				runs = Integer.valueOf((String) run.subSequence(0, run.length() - 2));
				currentBowler.setRuns(currentBowler.getRuns() + runs);
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				striker.addBalls(1);
				totalRuns++;
				extras++;
			} else if (run.matches("^[0-9]*Nb$")) {
				runs = Integer.valueOf((String) run.subSequence(0, run.length() - 2));
				currentBowler.setRuns(currentBowler.getRuns() + runs);
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				striker.addBalls(1);
				totalRuns++;
				extras++;
			} else if (run.matches("^[0-9]*Lb$")) {
				runs = Integer.valueOf((String) run.subSequence(0, run.length() - 2));
				currentBowler.setRuns(currentBowler.getRuns() + runs);
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				striker.addBalls(1);
				totalRuns++;
				balls++;
				extras++;
			} else if (run.matches("^[0-9]*B$")) {
				runs = Integer.valueOf((String) run.subSequence(0, run.length() - 1));
				currentBowler.setRuns(currentBowler.getRuns() + runs);
				currentBowler.setBowledBalls(currentBowler.getBowledBalls() + 1);
				striker.addBalls(1);
				totalRuns++;
				balls++;
				extras++;
			}
			totalRuns = totalRuns + runs;
			if (runs % 2 == 1) {
				temp = striker;
				striker = nonStriker;
				nonStriker = temp;
			}
			if (balls % 6 == 0) {
				temp = striker;
				striker = nonStriker;
				nonStriker = temp;
				tempB = currentBowler;
				currentBowler = nextBowler;
				nextBowler = tempB;
			}
			if (wickets == 2) {
				currentInning.setTotalWickets(wickets);
				currentInning.setExtras(extras);
				currentInning.setTotalBalls(balls);
				currentInning.setTotalRuns(totalRuns);
				break;
			}
			if (totalRuns > target) {
				currentInning.setTotalWickets(wickets);
				currentInning.setExtras(extras);
				currentInning.setTotalBalls(balls);
				currentInning.setTotalRuns(totalRuns);
				break;
			}
			if (balls / 6 == 5) {
				break;
			}
			if (over.size() == 0) {
				list = input.poll();
				over = new LinkedList<>(list);
			}
		}
	}

	public String getTeamScore(Innings currentInning, Innings nextInning) {
		String isOut = "";
		output = "Team ";
		int wickets = currentInning.getTotalWickets();
		output = output + "" + currentInning.getBattingTeam().getTeamName() + " - " + currentInning.getTotalRuns() + "/"
				+ wickets;
		if (wickets == 2) {
			output = output + "  (all out, ";
		} else
			output = output + "  (";
		if (currentInning.getTotalBalls() % 6 != 0) {
			output = output + "" + currentInning.getTotalBalls() / 6 + "." + currentInning.getTotalBalls() % 6
					+ " overs)\n\n BATTING :\n";
		} else
			output = output + "" + currentInning.getTotalBalls() / 6 + " overs)\n BATTING :\n";
		List<BatsMan> batsmen = currentInning.getBattingTeam().getBatsmen();
		for (int i = 0; i < 3; i++) {
			isOut = batsmen.get(i).getStatus().equals("Out") ? " " : "*";
			output = output + "" + batsmen.get(i).getName() + "" + isOut + " - " + batsmen.get(i).getRuns() + " runs ("
					+ batsmen.get(i).getBalls() + " balls)\n";
		}
		List<Bowler> bowlers = nextInning.getBowlingTeam().getBowlers();
		output += "\nBOWLING : \n";
		for (int i = 0; i < 2; i++) {
			output += "" + bowlers.get(i).getName() + " - ";
			if (currentInning.getTotalBalls() % 6 != 0) {
				output = output + "" + bowlers.get(i).getBowledBalls() / 6 + "." + bowlers.get(i).getBowledBalls() % 6
						+ " overs - " + bowlers.get(i).getRuns() + "/" + bowlers.get(i).getWicket() + "\n";
			} else
				output = output + "" + bowlers.get(i).getBowledBalls() / 6 + " overs - " + bowlers.get(i).getRuns()
						+ "/" + bowlers.get(i).getWicket() + "\n";
		}
		output += "\nPLAYER STATS: \n";
		for (int i = 0; i < 3; i++) {
			output += "" + batsmen.get(i).getName() + " - " + batsmen.get(i).getRuns() + " ("
					+ batsmen.get(i).getBalls() + ") - SR "
					+ (Math.round(
							(double) ((double) batsmen.get(i).getRuns() / (double) batsmen.get(i).getBalls()) * 100))
					+ "% - C "
					+ (Math.round(
							(double) ((double) batsmen.get(i).getRuns() / (double) currentInning.getTotalRuns()) * 100))
					+ "%\n";
		}
		return output;
	}

	public void getScore() {
		start.alert(getTeamScore(firstInning, secondInning));
		start.alert(getTeamScore(secondInning, firstInning));
	}

}