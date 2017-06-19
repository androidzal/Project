package com.fjnu.edu.message;

public class MyMessage {
	private int head;
	private String detail;
	private int raw;

	public MyMessage(int head, String detail) {
		this.head = head;
		this.detail = detail;
	}

	public MyMessage() {
	}

	public int gethead() {
		return this.head;
	}

	public String getdetail() {
		return this.detail;
	}

	public void sethead(int head) {
		this.head = head;
	}

	public void setdetail(String detail) {
		this.detail=detail;
	}

	public int getRaw() {
		return raw;
	}

	public void setRaw(int raw) {
		this.raw = raw;
	}
	
}
