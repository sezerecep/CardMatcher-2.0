import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.*;
import java.util.Random;

public class MemoryGame implements ActionListener
{
	private JFrame mainFrame;
	private JFrame loginFrame;
	private Container mainContentPane;
	private TurnCounterLabel turnCounterLabel;
	private ImageIcon cardIcon[]; 
	private String ImageDirectory="Cards";
	private String loggedperson=null;
	private TextField name;
	private JPanel panel;
	private boolean FirsHighScores=false;
	private JScrollPane tableContainer;
	
	private static void newMenuItem(String text, JMenu menu, ActionListener listener)
	{
		JMenuItem newItem = new JMenuItem(text);
		newItem.setActionCommand(text);
		newItem.addActionListener(listener);
		menu.add(newItem);
	}
	private ImageIcon[] loadCardIcons()
	{
		ImageIcon icon[] = new ImageIcon[17];
		for(int i = 0; i < 17; i++ )
		{
			String fileName = "images/"+ImageDirectory+"/card" + i + ".jpg";
			icon[i] = new ImageIcon(fileName);
			if(icon[i].getImageLoadStatus() == MediaTracker.ERRORED)
			{
				JOptionPane.showMessageDialog(this.mainFrame
					, "The image " + fileName + " could not be loaded."
					, "Matching Game Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		return icon;
	}
	public MemoryGame()
	{
		this.loginFrame = new JFrame("Matching Game");
		this.loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.loginFrame.setSize(490,540);
		this.panel = new JPanel();
		loginFrame.setContentPane(panel);
		this.name = new TextField();
		name.setPreferredSize(new Dimension(200,25));
		panel.add(new JLabel("Please Enter Your Name:"));
		panel.add(name);
		JButton SBbutton = new JButton("Start Game");
		JButton HSbutton = new JButton("HighScores");
		panel.add(SBbutton);
		panel.add(HSbutton);
		SBbutton.addActionListener(this);
		HSbutton.addActionListener(this);
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		dprintln("actionPerformed " + e.getActionCommand());
		if(e.getActionCommand().equals("4 x 4"))
		{
			
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
			
		}
		else if(e.getActionCommand().equals("4 x 8"))
		{
			
			newGame("4 x 8");
			this.mainFrame.setSize(800,500);
		
			
		}
		else if(e.getActionCommand().equals("Cards"))
		{
			this.ImageDirectory="Cards";
			this.cardIcon= this.loadCardIcons();
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Cats"))
		{
			this.ImageDirectory="Cats";
			this.cardIcon= this.loadCardIcons();
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Football Teams"))
		{
			this.ImageDirectory="Football Teams";
			this.cardIcon= this.loadCardIcons();
			newGame("4 x 4");	
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Unlimited - 99999 Lifes"))
		{
			this.turnCounterLabel.setLeftLifes(99999);
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Easy - 128 Lifes"))
		{
			this.turnCounterLabel.setLeftLifes(128);
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Normal - 64 Lifes"))
		{
			this.turnCounterLabel.setLeftLifes(64);
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
			
		}
		else if(e.getActionCommand().equals("Hard - 32 Lifes"))
		{
			this.turnCounterLabel.setLeftLifes(32);
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
			
		}
		else if(e.getActionCommand().equals("Expert - 16 Lifes"))
		{
			this.turnCounterLabel.setLeftLifes(16);
			newGame("4 x 4");
			this.mainFrame.setSize(400,500);
		}
		else if(e.getActionCommand().equals("Exit")) 
		{
			this.mainFrame.setVisible(false);
			this.loginFrame.setVisible(true);
		}
		else if(e.getActionCommand().equals("Start Game"))
		{
			if(name.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null,"Please Enter a Name!","Cannot Start Game",1);
			}
			else
			{
				this.loggedperson=name.getText();
				this.loginFrame.setVisible(false);
				this.mainFrame = new JFrame("Matching Game");
				this.mainFrame.setVisible(true);
				this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.mainFrame.setSize(400,500);
				this.mainContentPane = this.mainFrame.getContentPane();
				this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS)); 
				this.turnCounterLabel = new TurnCounterLabel();
				JMenuBar menuBar = new JMenuBar();
				this.mainFrame.setJMenuBar(menuBar);
				JMenu gameMenu = new JMenu("Game");
				JMenu catMenu = new JMenu("Categories");
				JMenu lifeMenu = new JMenu("Lifes");
				menuBar.add(gameMenu);
				menuBar.add(catMenu);
				menuBar.add(lifeMenu);
				newMenuItem("4 x 4", gameMenu, this);
				newMenuItem("4 x 8", gameMenu, this);
				newMenuItem("Exit", gameMenu, this);
				String[] categories = this.getSubfolders();
				
				for(String item:categories)
				{
					newMenuItem(item,catMenu,this);
				}
				newMenuItem("Unlimited - 99999 Lifes",lifeMenu,this);
				newMenuItem("Easy - 128 Lifes",lifeMenu,this);
				newMenuItem("Normal - 64 Lifes",lifeMenu,this);
				newMenuItem("Hard - 32 Lifes",lifeMenu,this);
				newMenuItem("Expert - 16 Lifes",lifeMenu,this);
				this.cardIcon = loadCardIcons();
				newGame("4 x 4");
			}
		}
		else if(e.getActionCommand().equals("HighScores"))
		{
			
			DatabaseConnection con;
			try {
				con = new DatabaseConnection();
				JTable mytab=con.ReturnHighScores();
				if(this.FirsHighScores)
				tableContainer.removeAll();
				tableContainer = new JScrollPane(mytab);
				panel.add(tableContainer);
				this.loginFrame.setSize(491,541);
				this.FirsHighScores=true;
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	static public void dprintln( String message )
	{
		//System.out.println( message );
	}
	public static void randomizeIntArray(int[] a)
	{
		Random randomizer = new Random();
		for(int i = 0; i < a.length; i++ )
		{
			int d = randomizer.nextInt(a.length);
			int t = a[d];
			a[d] = a[i];
			a[i] = t;
		}
	}
	public JPanel makeCards4x4()
	{
		JPanel panel = new JPanel(new GridLayout(4, 4));
		TurnedCardManager turnedManager = new TurnedCardManager(this.turnCounterLabel,8,loggedperson);
		ImageIcon backIcon = this.cardIcon[16];
		int cardsToAdd[] = new int[16];
		for(int i = 0; i < 8; i++)
		{
			cardsToAdd[2*i] = i;
			cardsToAdd[2*i + 1] = i;
		}
		randomizeIntArray(cardsToAdd);
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			int num = cardsToAdd[i];
			Card newCard = new Card(turnedManager, this.cardIcon[num], backIcon, num);
			panel.add(newCard);
		}
		return panel;
	}
	public JPanel makeCards4x8()
	{
		JPanel panel = new JPanel(new GridLayout(4,8));
		TurnedCardManager turnedManager = new TurnedCardManager(this.turnCounterLabel,16,loggedperson);
		ImageIcon backIcon = this.cardIcon[16];
		int cardsToAdd[] = new int[32];
		for(int i = 0; i < 16; i++)
		{
			cardsToAdd[2*i] = i;
			cardsToAdd[2*i + 1] = i;
		}
		randomizeIntArray(cardsToAdd);
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			int num = cardsToAdd[i];
			Card newCard = new Card(turnedManager, this.cardIcon[num], backIcon, num);
			panel.add(newCard);
		}
		return panel;
	}
	public void newGame(String gametext)
	{
		
		this.turnCounterLabel.reset();
		this.mainContentPane.removeAll();
		if(gametext=="4 x 4")
			this.mainContentPane.add(makeCards4x4());
		if(gametext=="4 x 8")
			this.mainContentPane.add(makeCards4x8());
		this.mainContentPane.add(this.turnCounterLabel);
		this.mainFrame.setVisible(true);
	}
	public String[] getSubfolders()
	{
		File file = new File("./images");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		return directories;
	}
	public void Start()
	{
		loginFrame.setVisible(true);
	}
}
