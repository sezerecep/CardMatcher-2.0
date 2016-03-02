import javax.swing.JLabel;

public class TurnCounterLabel extends JLabel
{
	public int numTurns = 0;
	private int leftlifes  = 99999;
	private final String DESCRIPTION = " Turns: ";
	private String LIFEDESCRIPTION = "Lifes:";
	private String diff="Unlimited";
	private void update()
	{
		if(leftlifes!=0)
		{
		setText(LIFEDESCRIPTION + Integer.toString(this.leftlifes)+" "+ DESCRIPTION + Integer.toString(this.numTurns));
		}
	}
	public TurnCounterLabel()
	{
		super();
		reset();
	
	}
	public void increment()
	{
		this.numTurns++;
		this.leftlifes--;
		update();
	}
	public void reset()
	{
		this.numTurns = 0;
		update();
	}
	public int getLeftLifes()
	{
		return this.leftlifes;
	}
	public int getTurns()
	{
		return this.numTurns;
	}
	public void setLeftLifes(int LefLif)
	{
		this.leftlifes=LefLif;
		if(LefLif==99999)
			diff="Unlimited";
		else if(LefLif==128)
			diff="Easy";
		else if(LefLif==64)
			diff="Normal";
		else if(LefLif==32)
			diff="Hard";
		else if(LefLif==16)
			diff="Expert";
		else
			diff="Unlimited";
	}
	public String getDiff()
	{
		return diff;
	}
}
