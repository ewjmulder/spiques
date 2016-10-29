package com.programyourhome.spiques.master.model;

public class TileImpl implements Tile {

	private Position position;
	private String ip;
	
	public TileImpl(Position position, String ip) {
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
