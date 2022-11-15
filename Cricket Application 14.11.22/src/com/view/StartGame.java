package com.view;

import com.controller.InputController;
import com.controller.PlayGameController;

public class StartGame {
	private PlayGameController game;
	private InputController input;

	public StartGame() {
		game = new PlayGameController(this);
		input = new InputController();
	}

	public static void main(String[] args) {
		new StartGame().getInput();
	}

	private void getInput() {
		game.setInput(input.getInput());
		printOutput();
	}

	private void printOutput() {
		System.out.println("TEAM SCORES \n----------------------");
		game.getScore();
	}

	public void alert(String teamScore) {
		System.out.println(teamScore+"\n\n");
	}

}
