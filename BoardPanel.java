package Sprint4;

/* created by Garlic
 * Anna Davison	16382333
 * James Kearns	15467622
 * Orla Keating	15205679
 */

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import java.awt.image.BufferedImage;
import java.io.IOException;

class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 650, FRAME_HEIGHT = 650;  // must be even
	private static final float COL_OFFSET = 43f, ROW_OFFSET = 24f;
	private static final float COL_SCALE = 23.4f, ROW_SCALE = 23.5f;
	private static final int TOKEN_RADIUS = 10;   // must be even

	private final Tokens tokens;
	private final Weapons weapons;
	private BufferedImage boardImage;

	public BufferedImage book;
	public BufferedImage bored;
	public BufferedImage gradcap;
	public BufferedImage librocop;
	public BufferedImage microscope;
	public BufferedImage seagull;

	//creates instance of board panel
	BoardPanel(Tokens tokens, Weapons weapons) {
		this.tokens = tokens;
		this.weapons = weapons;
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(Color.WHITE);
		try {
			boardImage = ImageIO.read(this.getClass().getResource("cluedo board.jpg"));
		} catch (IOException ex) {
			System.out.println("Could not find the image file " + ex.toString());
		}
		weaponsReadIn();
	}

	//draws board, tokens and weapons
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D) g;
		g2.drawImage(boardImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
		for (Token token : tokens) {
			int x = Math.round(token.getPosition().getCol()*COL_SCALE + COL_OFFSET);
			int y = Math.round(token.getPosition().getRow()*ROW_SCALE + ROW_OFFSET);
			g2.setColor(Color.BLACK);
			Ellipse2D.Double ellipseBlack = new Ellipse2D.Double(x,y,2*TOKEN_RADIUS,2*TOKEN_RADIUS);
			g2.fill(ellipseBlack);
			Ellipse2D.Double ellipseColour = new Ellipse2D.Double(x+2,y+2,2*TOKEN_RADIUS-4,2*TOKEN_RADIUS-4);
			g2.setColor(token.getColor());
			g2.fill(ellipseColour);
		}
		for (Weapon weapon : weapons) {
			int x = Math.round(weapon.getPosition().getCol()*COL_SCALE + COL_OFFSET);
			int y = Math.round(weapon.getPosition().getRow()*ROW_SCALE + ROW_OFFSET);
			if(weapon.getName().equalsIgnoreCase("book"))
				g2.drawImage(book, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
			if(weapon.getName().equalsIgnoreCase("Bored"))
				g2.drawImage(bored, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
			if(weapon.getName().equalsIgnoreCase("gradcap"))
				g2.drawImage(gradcap, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
			if(weapon.getName().equalsIgnoreCase("librocop"))
				g2.drawImage(librocop, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
			if(weapon.getName().equalsIgnoreCase("microscope"))
				g2.drawImage(microscope, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
			if(weapon.getName().equalsIgnoreCase("seagull"))
				g2.drawImage(seagull, x, y, 4*TOKEN_RADIUS, 4*TOKEN_RADIUS, this);
		}
	}

	//redraws images 
	public void refresh() {
		revalidate();
		repaint();
	}

	//reads in images for weapons
	public void weaponsReadIn()
	{
		for(Weapon weapon : weapons)
		{
			if(weapon.getName().equals("Book"))
			{
				try
				{
					book = ImageIO.read(this.getClass().
							getResource("book.jpg"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
			else if(weapon.getName().equals("Bored"))
			{
				try
				{
					bored = ImageIO.read(this.getClass().
							getResource("bored_1.jpg"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
			else if(weapon.getName().equals("Gradcap"))
			{
				try
				{
					gradcap = ImageIO.read(this.getClass().
							getResource("gradcap.png"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
			else if(weapon.getName().equals("Librocop"))
			{
				try
				{
					librocop = ImageIO.read(this.getClass().
							getResource("librocop.jpg"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
			else if(weapon.getName().equals("Microscope"))
			{
				try
				{
					microscope = ImageIO.read(this.getClass().
							getResource("MICROSCOPE.png"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
			else if(weapon.getName().equals("Seagull"))
			{
				try
				{
					seagull = ImageIO.read(this.getClass().
							getResource("seagull.jpg"));		
				}catch(IOException ex)
				{
					System.out.println("Could not find the image file "
							+ ex.toString());
				}
			}
		}
	}

}
