package de.hsh.pp.gallactictraveller;

import java.util.ArrayList;

public class Raumschiff {
	
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;
//	final int GROUND = 382;
	
	private int centerX = 100;
	private int centerY = 377;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean readyToFire = true;
	
	private int speedX = 0;
	private int speedY = 0;
	
	private static Background bg1 = Main.getBg1();
	private static Background bg2 = Main.getBg2();

	public void update() {

		// Moves Character or Scrolls Background accordingly.
		if (speedX < 0) {
            centerX += speedX;
        }
        if (speedX == 0 || speedX < 0) {
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
            bg1.setSpeedY(1);
            bg2.setSpeedY(1);

        }
        if (centerX <= 800 && speedX > 0) {
            centerX += speedX;
        }
//        if (speedX > 0 && centerX > 200){
//            bg1.setSpeedX(-MOVESPEED/5);
//            bg2.setSpeedX(-MOVESPEED/5);
//        }

        // Updates Y Position
        centerY += speedY;
        
        
//        if (centerY + speedY >= GROUND) {
//            centerY = GROUND;
//        }
//		// Handles Jumping
//		if (jumped == true) {
//			speedY += 1;
//
//			if (centerY + speedY >= 382) {
//				centerY = 382;
//				speedY = 0;
//				jumped = false;
//			}
//
//		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			centerX = 61;
		}
		if (centerX + speedX >=800) {
			centerX = 798;
		}
	}

	public void moveRight() {
		speedX = MOVESPEED;
	}

	public void moveLeft() {
		speedX = -MOVESPEED;
	}
	
	public void stopRight(){
		setMovingRight(false);
		stop();
	}
	
	public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }

        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }

        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }
        
	}
	
	public void shoot() {
		if (readyToFire) {
			Projectile p = new Projectile(centerX + 50, centerY - 25);
			projectiles.add(p);
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

//	public void jump() {
//		if (jumped == false) {
//			speedY = -15;
//			jumped = true;
//		}
//
//	}
	
	public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
    
    public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

}
