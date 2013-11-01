package de.hsh.pp.gallactictraveller;

import java.awt.Image;

public class Tile {

	private int tileX, tileY, speedX, type;
	private Image tileImage;
	private Raumschiff raumschiff = Main.getRaumschiff();
	private Background bg = Main.getBg1();

	public Tile(int x, int y, int typeInt) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeInt;

		/*
		 * Eventuell verbessern mit switch case oder ähnliches
		 */
		if (type == 5) {
			tileImage = Main.tiledirt;
		} else if (type == 8) {
			tileImage = Main.tilegrassTop;
		} else if (type == 4) {
			tileImage = Main.tilegrassLeft;

		} else if (type == 6) {
			tileImage = Main.tilegrassRight;

		} else if (type == 2) {
			tileImage = Main.tilegrassBot;
		}
	}

	/*
	 * für bewegende Tiles
	 */
	// public void update() {
	// // TODO Auto-generated method stub
	// if (type == 1) {
	// if (bg.getSpeedX() == 0) {
	// speedX = -1;
	// } else {
	// speedX = -2;
	// }
	//
	// } else {
	// speedX = bg.getSpeedX() * 5;
	// }
	//
	// tileX += speedX;
	// }

	public void update() {
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

}
