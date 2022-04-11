package com.github.prgrms.utils;

public class Const {

	public enum State {
		REQUESTED("REQUESTED"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED"), SHIPPING(
				"SHIPPING"),
		COMPLETED("COMPLETED");

		public String state;

		State(String state) {
			this.state = state;
		}
	}

}
