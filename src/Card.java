import java.awt.event.*;
import javax.swing.*;

public class Card extends JLabel implements MouseListener
{
	TurnedCardManager turnedManager;
	Icon faceIcon;
	Icon backIcon;
	boolean faceUp = false; 
	int num; 	
	int iconWidthHalf, iconHeightHalf; 
	boolean mousePressedOnMe = false;
	
	public Card(TurnedCardManager turnedManager, Icon face, Icon back, int num)
	{
		super(back);
		this.turnedManager = turnedManager;
		this.faceIcon = face;
		this.backIcon = back;
		this.num = num;
		this.addMouseListener(this);
		this.iconWidthHalf = back.getIconWidth() / 2;
		this.iconHeightHalf = back.getIconHeight() / 2;
	}
	public void turnUp()
	{
		if(this.faceUp) return;
		this.faceUp = this.turnedManager.turnUp(this);
		if(this.faceUp) this.setIcon(this.faceIcon);
	}
	public void turnDown()
	{
		if(!this.faceUp) return;
		this.setIcon(this.backIcon);
		this.faceUp = false;
	}
	public int getNum() { return num; }
	private boolean overIcon(int x, int y)
	{
		int distX = Math.abs(x - (this.getWidth() / 2));
		int distY = Math.abs(y - (this.getHeight() / 2));
		if(distX > this.iconWidthHalf || distY > this.iconHeightHalf )
			return false;
		return true;
	}
	public void mouseClicked(MouseEvent e)
	{
		if(overIcon(e.getX(), e.getY())) this.turnUp();
	}
	public void mousePressed(MouseEvent e)
	{
		if(overIcon(e.getX(), e.getY())) this.mousePressedOnMe = true;
	}
	public void mouseReleased(MouseEvent e)
	{
		if(this.mousePressedOnMe)
		{
			this.mousePressedOnMe = false;
			this.mouseClicked(e);
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e)
	{
		this.mousePressedOnMe = false;
	}
}
