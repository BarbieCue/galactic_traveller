package de.hsh.pp.gallactictraveller;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import de.hsh.pp.gallactictraveller.framework.Animation;

@SuppressWarnings("serial")
public class Main extends Applet implements Runnable, KeyListener {
	// TODO: Collision Detection Basics Lesen
	private static Raumschiff raumschiff;
	private Heliboy hb, hb2;
	private Image image, character, character2, character3, background,
			heliboy, heliboy2, heliboy3, heliboy4, heliboy5;

	public static Image tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt;

	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim;

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	@Override
	public void init() {
		setSize(910, 690);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Gallactic Taveller");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		/*
		 * Image Setup
		 */
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");

		background = getImage(base, "data/hintergrund.png");
//		background = getImage(base, "data/background.png");

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		tiledirt = getImage(base, "data/tiledirt.png");
        tilegrassTop = getImage(base, "data/tilegrasstop.png");
        tilegrassBot = getImage(base, "data/tilegrassbot.png");
        tilegrassLeft = getImage(base, "data/tilegrassleft.png");
        tilegrassRight = getImage(base, "data/tilegrassright.png");
	}

	@Override
	public void start() {
//		bg1 = new Background(0, 0);
//		bg2 = new Background(2160, 0);
		
		bg1 = new Background(0, 0);
		bg2 = new Background(0, -680);
		
		raumschiff = new Raumschiff();

		/*
		 * Initialize Tiles
		 */

		try {
            loadMap("data/map1.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		Thread thread = new Thread(this);
		thread.start();

	}

	private void loadMap(String filename) throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
        int width = 0;
        int height = 0;

        /*
         * SCHLECHTE WHILE SCHLEIFE ... bei gelegenheit selbst neuSchreiben
         */
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());

            }
        }
        height = lines.size();
        for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                System.out.println(i + "is i ");

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }

            }
        }
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			raumschiff.update();

			ArrayList<Projectile> projectiles = raumschiff.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}
			bg1.update();
			bg2.update();
			updateTiles();
			hb.update();
			hb2.update();

			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {
		anim.update(10);
		hanim.update(50);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		paintTiles(g);

		ArrayList<Projectile> projectiles = raumschiff.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		g.drawImage(character, raumschiff.getCenterX() - 61,
				raumschiff.getCenterY() - 63, this);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48, this);
	}

	/*
	 * Unit 3 nochmal Day 1 nochmal durchlesen falls es nicht klar wird
	 */
	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			raumschiff.setSpeedX(0);
			break;

		case KeyEvent.VK_LEFT:
			raumschiff.moveLeft();
			raumschiff.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			raumschiff.moveRight();
			raumschiff.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			raumschiff.shoot();
			raumschiff.setReadyToFire(false);
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			break;

		case KeyEvent.VK_LEFT:
			raumschiff.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			raumschiff.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			raumschiff.setReadyToFire(true);
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
	
	public static Raumschiff getRaumschiff(){
		return raumschiff;
	}

	/*
	 * benötigt man nur wenn man den Background ingame ändern mochte
	 */
	// public static void setBg1(Background bg1) {
	// Main.bg1 = bg1;
	// }
	//
	// public static void setBg2(Background bg2) {
	// Main.bg2 = bg2;
	// }

}
