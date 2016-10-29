package com.programyourhome.spiques.master.model;

public class SlaveImpl implements Slave {

	private Position position;
	private String ip;
	
	public SlaveImpl(Position position, String ip) {
		this.position = position;
		this.ip = ip;
	}
	
	@Override
	public Position getPosition() {
		return this.position;
	}
	
	@Override
	public String getIp() {
		return this.ip;
	}
	
}
