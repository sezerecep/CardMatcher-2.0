import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class TurnedCardManager implements ActionListener
{
	private Vector turnedCards;			
	private TurnCounterLabel turnCounterLabel;
	private Timer turnDownTimer;				
	private final int turnDownDelay = 1000;
	private int MatchedCardNum;
	private int gamesize;
	private String username;
	boolean flag=true;
	public TurnedCardManager(TurnCounterLabel turnCounterLabel,int size,String name)
	{
		this.turnCounterLabel = turnCounterLabel;
		this.turnedCards = new Vector(2);
		this.turnDownTimer = new Timer(this.turnDownDelay, this);
		this.turnDownTimer.setRepeats(false);
		this.MatchedCardNum=0;
		this.gamesize = size;
		this.username=name;
	}
	private boolean doAddCard(Card card)
	{
		this.turnedCards.add(card);
		if(this.turnedCards.size() == 2)
		{
			this.turnCounterLabel.increment();
			Card otherCard = (Card)this.turnedCards.get(0);
			if( otherCard.getNum() == card.getNum())
			{
				MatchedCardNum++;
				if(MatchedCardNum==gamesize)
				{
					this.turnCounterLabel.setText("Game Won by "+Integer.toString(this.turnCounterLabel.getTurns())+" Turns :)");
					this.flag=false;
					try {
						DatabaseConnection con= new DatabaseConnection();
						con.NonReturnQuery(username, this.turnCounterLabel.getTurns(),this.turnCounterLabel.getDiff() , gamesize);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if(this.turnCounterLabel.getLeftLifes()==0)
				{
						this.turnCounterLabel.setText("Game Over No Lifes Left :(");
						this.flag=false;
				}
				this.turnedCards.clear();
			}
			else {
				this.turnDownTimer.start();
				if(this.turnCounterLabel.getLeftLifes()==0)
				{
						this.turnCounterLabel.setText("Game Over No Lifes Left :(");
						this.flag=false;
				}
			}
				
		}
		return true;
	}
	public boolean turnUp(Card card)
	{
		if(flag)
		{
		if(this.turnedCards.size() < 2) 
			return doAddCard(card);
		}
		return false;
	}
	public void actionPerformed(ActionEvent e)
	{
		for(int i = 0; i < this.turnedCards.size(); i++ )
		{
			Card card = (Card)this.turnedCards.get(i);
			card.turnDown();
		}
		this.turnedCards.clear();
	}
}
