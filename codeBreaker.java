
//=======================================================================================================
//Program Name: codeBreaker
//Author: Wesley Liu & Sam Lee
//Date: 2023/06/06
//Java (Neon.1a Release (4.6.1))
//=======================================================================================================
//Problem Definition - Create the game of Codebreaker, in two modes, Player VS AI and AI vs Player. Display in a neat GUI as well as a text-based solve
//Input: Gamemode/menu choice, Guesses, Hints, File name to save to, File name to upload, etc.
//Output: GUI Menu and components, Visual representation of the gameboard and the 10 attempts, etc. User friendly prompts, simpler version in console solve
//Process: Use java swing for the GUI, use a variety of different methods and algorithms to bring the game to life
//=======================================================================================================
/*LIST OF IDENTIFIERS
 *
 * Let grid represent the visual grid on the GUI. This 6x10 2D array encompasses all the 10 attempts, 4 digit codes, and hints, Type: Global ArrayList <ArrayList<JLabel>>
 * Let key represent the computer's randomly generated key, type: Global ArrayList<String>
 * Let elapsedTime represent time passed during the gamemode in which the computer sets the code, Type: Int
 * Let seconds, minutes, and hours store the amount of seconds, minutes, and hours passed, Type: Int
 * Let seconds_string, minutes_string, and hours_string be used for formatting purposes for the GUI timer, Type: String
 * Let readSeconds, readMinutes, and readHours represent the seconds, minutes, and hours read from a file, Type: Global String
 * Let gameMode represent which gamemode the user is playing in the GUI, Type: Global Boolean
 * Let round and round2 keep track of which round the GUI game is in, for 2 seperate gamemodes, Type: Global Int
 * Let dimensionX and dimensionY be used to indicate the initial coordinates when generating the grid visual, Type: Global Int
 * Let numberOfWhite and numberOfBlack be used to keep track of the black and white pegs during gameplay, Type: Global String
 * Let readAttempts represent how many attempts a saved game took to solve
 * Let hintIsValid be used to determine if the user's inputted hint is possible, Type: Global Boolean
 * Let rand be an object of the random class, used to generate random values, Type: Global Random class object
 * Let VALID_CHARS, SIZE, and TRIES be constants that govern the appropriate colours, the size of the code, and the allowed tries respectively
 * Type: Global constant String [], Global constant int, Global constant int
 * Let PERMUTATIONS represent the total amount of possible code permuatations, Type: Global constant int
 * Let combinations represent the pool of possible combinations that can be the user's code, Type: Global ArrayList<ArrayList<String>
 * Let guess represent the AI's guess of the user's code, Type: Global ArrayList<String>
 * Let guess2 represent the user's guess of the computer's code, Type: Global ArrayList<String>
 * Let arrayGrid be a representation of a finished game that is meant to be read when trying to display a game, Type: Global ArrayList<ArrayList<String>>
 * Let guessedCorrect represent whether the code has been correctly guessed, Type: Global boolean
 * Let frame represent the frame of the GUI, Type: Global JFrame
 * Let panel represent a JPanel used to add GUI elements, Type: Global JPanel
 * Let TimeLabel help prompt the user towards the timer feature, Type: Global JLabel
 * Let TitleLabel display the title, Type: Global JLabel
 * Let GuessCode represent a friendly prompt on the gamemode where the computer sets the code, Type: Global JLabel
 * Let RedLabel, BlueLabel, GreenLabel, YellowLabel, OrangeLabel, and PurpleLabel display the name of the colours, Type: Global JLabel
 * Let EnterWhite and Enter Black be prompts to indicate where to enter the hints, Type: Global JLabel
 * Let WelcomeInstruction be a user friendly title for the instructions page, Type: Global JLabel
 * Let InstructionPVAITitle and InstructionAIVP be titles for the different instructions, Type: Global JLabel
 * Let InstructionAIVP and Instruction PVAI be the instructions for the gamemodes, Type: Global JLabel
 * Let SaveGamePrompt be used to prompt the user to save the game at the end, Type: Global JLabel
 * Let TimeElapsed be used to label the timer in the gamemode in which the computer sets the code, Type: Global JLabel
 * Let Blacks and Whites be used to accept the user's hints, Type: Global JTextField
 * Let fileName and fileName2 be used to accept the name of a file from the user, Type: Global JTextField
 * Let PlayerVsAI allow the user to choose this game mode, Type: Global JButton
 * Let AIVsPlayer allow the user to choose this game mode, Type: Global JButton
 * Let InstructionGame allow the user to view instructions, Type: Global JButton
 * Let viewGame allow the user to view a past saved game, Type: Global JButton
 * Let Red, Green, Blue, Yellow, Purple, and Orange allow the user to select colours for their guess, Type: Global JButton
 * Let Delete allow the user to delete a part of their guess, Type: Global JButton
 * Let Submit allow the user to submit their guess, Type: Global JButton
 * Let ReturnMenu allow the user to return back to the menu, Type: Global JButton
 * Let Yes and No allow the user to indicate if the AI guessed correctly, Type: Global JButton
 * Let Monitor1, Monitor2, Monitor3, and Monitor4 be used in various locations to display various prompts to the user, Type: Global JButton
 * Let SubmitBlackWhite allow the user to submit their hint, Type: Global JButton
 * Let saveButton allow the user to save a game, Type: Global JButton
 * Let uploadButton allow the user to upload a saved game, Type: Global JButton
 * Let RColor, GColor, BColor, YColor, OColor, and PColor be used as custom colours throughout the GUI, , Type: Global Color
 *
 */


import java.awt.Color; //required imports
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class codeBreaker implements ActionListener { //class header

	//declaration of various variables outlined above
	static ArrayList <ArrayList<JLabel>> grid = new ArrayList<ArrayList<JLabel>>(10);
	static ArrayList <String> key = new ArrayList<String>();

	int elapsedTime = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", minutes);
	String hours_string = String.format("%02d", hours);

	static String readSeconds;
	static String readMinutes;
	static String readHours;
	static boolean gameMode=false;

	static int round=0;
	static int round2=0;
	static int dimensionX = 360;
	static int dimensionY = 90;

	static String numberOfWhite;
	static String numberOfBlack;

	static int readAttempts;

	static boolean hintIsValid=false;

	static Random rand = new Random();

	final static String [] VALID_CHARS = {"R","G","B","Y","O","P"};
	final static int SIZE = 4;
	final static int TRIES=10;

	final static int PERMUTATIONS = (int) Math.pow((VALID_CHARS.length), SIZE);

	static ArrayList<ArrayList<String> > combinations = new ArrayList<ArrayList<String>>(PERMUTATIONS);
	static ArrayList<String>guess = new ArrayList<String>();
	static ArrayList <String> guess2 = new ArrayList <String>(4);
	static ArrayList<ArrayList<String>> arrayGrid = new ArrayList<ArrayList<String>>();
	static boolean guessedCorrect=false;

	//GUI
	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	//Labels
	static JLabel timeLabel = new JLabel();
	static JLabel TitleLabel = new JLabel("Codebreaker");
	static JLabel GuessCode = new JLabel("Guess the Code!");
	static JLabel RedLabel = new JLabel("Red");
	static JLabel BlueLabel = new JLabel("Blue");
	static JLabel GreenLabel = new JLabel("Green");
	static JLabel YellowLabel = new JLabel("Yellow");
	static JLabel OrangeLabel = new JLabel("Orange");
	static JLabel PurpleLabel = new JLabel("Purple");
	static JLabel EnterBlack = new JLabel("Black Pegs:");
	static JLabel EnterWhite = new JLabel("White Pegs:");
	static JLabel WelcomeInstruction = new JLabel("Instructions");
	static JLabel InstructionPVAITitle = new JLabel("Player Vs AI (AI sets the code)");
	static JLabel InstructionAIVPTitle = new JLabel("AI Vs Player (You set the code)");
	static JLabel InstructionAIVP = new JLabel("<html>- The AI will be guessing a code you make<br>- Write down a four color code somewhere using only the colors: Blue, Green, Red, Purple, Orange and Yellow<br> The computer will show you its guess on the grid<br>- If its guess is the same as your code, press Yes<br> If its guess is not the same as your code press No<br>- When you press No, the AI will prompt you to add the number of blacks and whites<br> Press submit to enter number of blacks and whites<br> Refer to Player Vs AI for what blacks and whites are</html>");
	static JLabel InstructionPVAI = new JLabel("<html>- The computer randomly generates a 4 color code<br>- The player must guess this code using the colored buttons to input a guess<br>- Once your guess is in, you can delete or submit your guess by pressing the corresponding buttons<br>- The numbers on the side of the grid indicate how many blacks and whites you have<br>- Blacks mean that your guess had a correct color in the right spot<br>- Whites mean that your guess had a correct color but the wrong spot<br>- No blacks or whites means your guess does not have a correct color</html>");
	static JLabel SaveGamePrompt = new JLabel("Would you like to save this game? Enter a file name:");
	static JLabel TimeElapsed = new JLabel("Time Elapsed");

	//Textfields
	static JTextField Blacks = new JTextField();
	static JTextField Whites = new JTextField();
	static JTextField fileName = new JTextField();
	static JTextField fileName2 = new JTextField();

	//Buttons
	static JButton PlayerVsAI = new JButton("Player Vs AI (AI sets the code)");
	static JButton AIVsPlayer = new JButton("AI Vs Player (You set the code)");
	static JButton InstructionGame = new JButton("See Instructions");
	static JButton viewGame = new JButton ("View a Saved Game");
	static JButton Red = new JButton(" ");
	static JButton Green = new JButton("  ");
	static JButton Blue = new JButton("   ");
	static JButton Yellow= new JButton("    ");
	static JButton Purple = new JButton("     ");
	static JButton Orange = new JButton("      ");
	static JButton Delete = new JButton("Delete");
	static JButton Submit = new JButton("Submit");
	static JButton ReturnMenu = new JButton("Return to Menu");
	static JButton Yes = new JButton("YES");
	static JButton No = new JButton("NO");
	static JButton Monitor = new JButton("");
	static JButton Monitor2 = new JButton("");
	static JButton Monitor3 = new JButton("");
	static JButton Monitor4 = new JButton("");
	static JButton SubmitBlackWhite = new JButton("Confirm");
	static JButton saveButton = new JButton ("Save");
	static JButton uploadButton = new JButton ("Upload");

	//custom colours
	static Color RColor = new Color(255,179,186,255);
	static Color GColor = new Color(186,255,201,255);
	static Color BColor = new Color(186,225,255,255);
	static Color YColor = new Color(255,255,186,255);
	static Color OColor = new Color(255,223,186,255);
	static Color PColor = new Color(225,205,254,255);

	/**Codebreaker (construtor) method:
	 *
	 * This method is essentially used to construct the GUI.
	 * It includes many details such as specific layouts, adds various
	 * actionListeners, initializes the starting behavours of various elements, and adds various elements to the main panel
	 * @param n/a
	 * @return n/a
	 * @throws n/a
	 */

	public codeBreaker() { //start of constructor

		//set up frame
		initializeGrid();
		frame.setSize(1600, 1000);
		frame.setVisible(true);
		frame.setTitle("Codebreaker");
		frame.setResizable(false);
		frame.add(panel);
		panel.setLayout(null);

		//Set bounds of elements on title screen
		TitleLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		TitleLabel.setBounds(640, 150, 300, 300);

		InstructionGame.setBounds(40, 500, 350, 230);
		PlayerVsAI.setBounds(420, 500, 350, 230);
		AIVsPlayer.setBounds(800, 500, 350, 230);
		viewGame.setBounds(1180, 500, 350, 230);

		//add items to panel
		panel.add(TitleLabel);
		panel.add(PlayerVsAI);
		panel.add(AIVsPlayer);
		panel.add(InstructionGame);
		panel.add(viewGame);

		//add action listeners
		PlayerVsAI.addActionListener(this);
		AIVsPlayer.addActionListener(this);
		Red.addActionListener(this);
		Blue.addActionListener(this);
		Green.addActionListener(this);
		Yellow.addActionListener(this);
		Purple.addActionListener(this);
		Orange.addActionListener(this);
		Submit.addActionListener(this);
		Delete.addActionListener(this);
		ReturnMenu.addActionListener(this);
		InstructionGame.addActionListener(this);
		Yes.addActionListener(this);
		No.addActionListener(this);
		SubmitBlackWhite.addActionListener(this);
		fileName.addActionListener(this);
		saveButton.addActionListener(this);
		viewGame.addActionListener(this);
		fileName2.addActionListener(this);
		uploadButton.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end of constructor

	/**main method:
	 *
	 * The main method (procedural) is in charge of mostly the console solve of the game.
	 * Although this is a mainly GUI based program, the game can still be experienced in the console
	 *
	 * LOCAL VARIABLES
	 * VALID_CHARS: represents the valid characters used to represent colours, Type: String [] constant
	 * SIZE: represents the size of the code, Type: int constant
	 * TRIES: represent sthe amount of given tries, Type: int constant
	 * br: an object used for I/O: Type: BufferedReader
	 * frame: an object of the Codebreaker class, Type: Codebreaker
	 * black and white: used to indicate number of blacks and whites, Type: int
	 * count and count2: used as an accumulator throughout the game, Type: int
	 * gameMode: used to determine which gamemode to run, Type: String
	 * validHint: used to determine if the hint is valid, Type: boolean
	 * combinations: used to contain all possible combinations, Type: ArrayList<ArrayList<String>>
	 * guess: used to represent the computer's guess for the code, Type: ArrayList<String>
	 * guess2: used to represent the user's guess, Type: ArrayList<String>
	 *
	 * @param String[] args
	 * @return void
	 * @throws n/a
	 */
	public static void main(String[] args) throws IOException { //method start

		//initialization
		final String [] VALID_CHARS = {"R","G","B","Y","O","P"};
		final int SIZE = 4;
		final int TRIES=10;

		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		codeBreaker frame = new codeBreaker();

		int black = 0;
		int white = 0;
		int count=1;
		int count2=1;

		String gameMode;
		boolean validHint=false;


		//UFP
		System.out.println("Welcome to Codebreaker!");
		System.out.println("Please select a game mode");
		System.out.println("1. YOU set the secret code");
		System.out.println("2. The COMPUTER sets the secret code");
		gameMode = br.readLine(); //decide what gamemode to run based on the user's choice

		switch(gameMode) { //switch case based on the user's choice

		case "1": //if the user chooses to set the secrect code

			System.out.println("__________________________________________");
			System.out.println("Please create a secret code. Don't tell me what it is!");
			System.out.println("__________________________________________"); //UFP

			ArrayList<ArrayList<String> > combinations = new ArrayList<ArrayList<String>>(PERMUTATIONS);
			//create a new 2D String ArrayList called combinations


			for (int i =0; i<VALID_CHARS.length; i++){ //Quadruple for loop, each running from 0-5, the indexes of the possible colours
				for (int j=0; j<VALID_CHARS.length; j++){
					for (int k=0; k<VALID_CHARS.length;k++){
						for (int l=0; l<VALID_CHARS.length;l++){
							ArrayList<String>combination = new ArrayList<String>(); //create a new string arrayList
							combination.add(VALID_CHARS[i]);
							combination.add(VALID_CHARS[j]);
							combination.add(VALID_CHARS[k]);
							combination.add(VALID_CHARS[l]);//create a unique combination
							combinations.add(combination);//add this unique combination to combinations
						}
					}
				}
			} //essentially adds all the possibilities to combinations.

			ArrayList<String>guess = new ArrayList<String>(); //make a guess arrayList

			while(combinations.size()>1) { //continues until 1 possibility is left

				guess = combinations.get(rand.nextInt(combinations.size())); //get a random guess from pool of possibilities
				System.out.println("ATTEMPT "+count2+": "+guess);//UFP


				while(!validHint){//while loop ensures valid input

					//get input
					System.out.println("Please enter hints");


					System.out.println("Black: ");
					black = getInput();

					System.out.println("White: ");
					white = getInput();

					//check validity
					validHint = validHint(guess,combinations,Integer.toString(black),Integer.toString(white));
					if(!validHint){
						System.out.println("Those hints are impossible! Please try again");//UFP
					}

				}
				validHint=false;//reset for future use

				removePossibilities(guess, combinations, black, white);

				count2++; //removes possibilities and accumulates count

			}
			System.out.println("Your code is "+combinations.get(0));  //UFP
			System.out.println("GG, thanks for playing!");
			break;

		case "2": //if player chooses to be the one guessing the computer's code
			System.out.println("________________________________");
			System.out.println("I have created a secret code! Try to find it in max 10 attempts");
			System.out.println("________________________________"); //UFP

			key=getRandomCode(); //get a random code
			// System.out.println(key);

			ArrayList <String> guess2 = new ArrayList <String>(); //new guess arrayList, not to be confused with the previous one called guess
			System.out.println("Please enter guess "+count); //UFP
			guess2=getUserCode(); //get the user's guess

			System.out.println("Black: "+getHint(key,guess2).get(0));
			System.out.println("White: "+getHint(key,guess2).get(1));  //get hints displayed


			count++; //accumulate

			while (!checkEquals(key, guess2)){//while loop continues while the guesses are wrong. Breaks out if the user is correct, or if max tries are exceeded

				if (count==11) {//condition to check if the user is out of attempts
					System.out.println("Sorry, you lose. The code was "+key); //UFP
					break;
				}
				System.out.println("Please make guess "+count); //UFP
				guess2 = getUserCode();//get guess

				System.out.println("Black: "+getHint(key,guess2).get(0)); //display hints
				System.out.println("White: "+getHint(key,guess2).get(1));

				count++;//accumulate

			}

			if (!(count==11)) {
				System.out.println("Congrats!");//UFP
			}
			break;
		}
	}//method end
	//GUI METHODS



	Timer timer = new Timer(1000, new ActionListener() { //create a new timer object, add an action listener
		public void actionPerformed(ActionEvent e) { //actionPerformed method used for the timer
			elapsedTime = elapsedTime + 1000; //formatting time
			hours = (elapsedTime/3600000);
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			hours_string = String.format("%02d", hours);
			timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string); //this is how it will be displayed
		}
	});
	void start() { //simple procedural method used to start the timer
		timer.start();
	}


	void stop() { //simple procedural method used to stop the timer
		timer.stop();
	}

	void reset() { //simple procedural method used to reset the timer
		timer.stop();
		elapsedTime = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		hours_string = String.format("%02d", hours);
		timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
	}

	/**main actionPerformed method:
	 *
	 * This method is used to handle events, like the press of a button. This
	 * governs what happens when the user interacts with the GUI
	 *
	 * LOCAL VARIABLES
	 * command - used to store the text of a toggled JButton - Type: String
	 *
	 * @param ActionEvent event
	 * @return void
	 * @throws n/a
	 */

	public void actionPerformed(ActionEvent e) {//method start

		String command = e.getActionCommand(); //initialize command

		if (command.equals("Player Vs AI (AI sets the code)")) {
			GuessCode.setText("Guess the Code!");
			gameMode=true; //true represents this game mode

			//timer settings and setup
			reset();
			timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
			timeLabel.setBounds(1370, 20, 200, 100);
			timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35)); //creating the visual display
			timeLabel.setBorder(BorderFactory.createBevelBorder(1));
			timeLabel.setOpaque(true);
			timeLabel.setHorizontalAlignment(JTextField.CENTER);
			panel.add(timeLabel);
			TimeElapsed.setBounds(1395, 40, 200, 200);
			TimeElapsed.setFont(new Font("Arial", Font.PLAIN, 25));
			panel.add(TimeElapsed);
			Monitor.setText("(Timer will begin after first guess)");

			key = getRandomCode();//get the random code
			//System.out.println(key);


			panel.remove(TitleLabel);
			panel.remove(PlayerVsAI);
			panel.remove(AIVsPlayer);
			panel.remove(InstructionGame); //remove various elements from the screen

			TitleLabel.setVisible(false);
			PlayerVsAI.setVisible(false);
			AIVsPlayer.setVisible(false);
			InstructionGame.setVisible(false);
			viewGame.setVisible(false); //change various visibility settings

			fileName.setText(""); //reset fileName's default text

			frame.setSize(1599, 1000);
			frame.setSize(1600, 1000); //reset frame properties

			GuessCode.setFont(new Font("Arial", Font.PLAIN, 40));
			Monitor.setFont(new Font("Arial", Font.PLAIN, 25));
			ReturnMenu.setFont(new Font("Arial", Font.PLAIN, 20));
			GuessCode.setBounds(630, -200, 500, 500);
			Red.setBounds(20, 840, 150, 80);
			Blue.setBounds(220, 840, 150, 80);
			Green.setBounds(420, 840, 150, 80);
			Yellow.setBounds(620, 840, 150, 80);
			Orange.setBounds(820, 840, 150, 80);
			Purple.setBounds(1020, 840, 150, 80);
			Delete.setBounds(1220, 840, 150, 80);
			Submit.setBounds(1420, 840, 150, 80);
			Monitor.setBounds(498, 798, 561, 30); //set up various boundaries and font styles

			//file elements setup
			SaveGamePrompt.setBounds(300, 860, 700, 50);
			fileName.setBounds(1000,860,130,50);
			saveButton.setBounds(1140,860,130,50);


			Monitor.setOpaque(false);
			Monitor.setContentAreaFilled(false);
			Monitor.setBorderPainted(false);
			ReturnMenu.setBounds(20, 20, 200, 100);
			Red.setBackground(RColor);
			Blue.setBackground(BColor);
			Green.setBackground(GColor);
			Yellow.setBackground(YColor);
			Orange.setBackground(OColor);
			Purple.setBackground(PColor);
			RedLabel.setBounds(80, 860, 150, 150);
			BlueLabel.setBounds(280, 860, 150, 150);
			OrangeLabel.setBounds(873, 860, 150, 150);
			YellowLabel.setBounds(675, 860, 150, 150);
			PurpleLabel.setBounds(1077, 860, 150, 150);
			GreenLabel.setBounds(475, 860, 150, 150);
			panel.add(GuessCode);
			panel.add(ReturnMenu);
			panel.add(Red);
			panel.add(Blue);
			panel.add(Green);
			panel.add(Yellow);
			panel.add(Orange);
			panel.add(Purple);
			panel.add(Submit);
			panel.add(Delete);
			panel.add(RedLabel);
			panel.add(BlueLabel);
			panel.add(YellowLabel);
			panel.add(GreenLabel);
			panel.add(OrangeLabel);
			panel.add(PurpleLabel);
			panel.add(Monitor);
			//set up monitor and colour buttons/labels


			SaveGamePrompt.setFont(new Font("Arial", Font.PLAIN, 30));
			panel.add(SaveGamePrompt);
			SaveGamePrompt.setVisible(false);

			panel.add(fileName);
			panel.add(saveButton);
			fileName.setVisible(false);
			saveButton.setVisible(false);
			//finish setting up the file saving setup

			dimensionX = 360;
			dimensionY= 90;
			grid.clear();
			initializeGrid();
			round = 0;
			guess2.clear();
			//additional setup, making sure various elements are reset for every new round


			Red.setVisible(true);
			Blue.setVisible(true);
			Orange.setVisible(true);
			Purple.setVisible(true);
			Green.setVisible(true);
			Yellow.setVisible(true);
			RedLabel.setVisible(true);
			BlueLabel.setVisible(true);
			OrangeLabel.setVisible(true);
			PurpleLabel.setVisible(true);
			GreenLabel.setVisible(true);
			YellowLabel.setVisible(true);
			Delete.setVisible(true);
			Submit.setVisible(true);
			//changing visibility settings

			for (int i = 0; i < 10; i++) {  //sets up a visual grid made up of JLabels. The edges of the grid are used for hints, centers for colours
				for (int j = 0; j < 6; j++) {
					JLabel label = new JLabel();
					if (j==0 || j==5) { //edges

						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(false);//make this JLabel invisible except the text. Creates an illusion that the grid is actually 4x10
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);  //add to the panel

					}else {//middle
						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(true);
						label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //make these JLabels have boundaries, for a visual effect
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);//add to the panel
					}
				}
				dimensionX = 360;
				dimensionY += 70; //adjust dimensions for a nice looking grid
			}
		}

		else if (command.equals("AI Vs Player (You set the code)")) {
			gameMode=false;//false represents this game mode

			Blacks.setText("");
			Whites.setText("");
			panel.removeAll();
			frame.setSize(1599, 1000);
			frame.setSize(1600, 1000);
			fileName.setText(""); //setup


			//font
			ReturnMenu.setFont(new Font("Arial", Font.PLAIN, 20));
			InstructionGame.setFont(new Font("Arial", Font.PLAIN, 20));
			Monitor.setFont(new Font("Arial", Font.PLAIN, 30));
			Yes.setFont(new Font("Arial", Font.PLAIN, 45));
			No.setFont(new Font("Arial", Font.PLAIN, 45));
			EnterBlack.setFont(new Font("Arial", Font.PLAIN, 30));
			EnterWhite.setFont(new Font("Arial", Font.PLAIN, 30));
			//set bounds
			ReturnMenu.setBounds(20, 20, 200, 100);
			Monitor.setBounds(500, 17, 555, 65);
			No.setBounds(1270, 680, 300, 270);
			Yes.setBounds(21, 680, 300, 270);
			Blacks.setBounds(430, 850, 200, 100);
			Whites.setBounds(669, 850, 200, 100);
			EnterWhite.setBounds(680, 730, 200, 200);
			EnterBlack.setBounds(455, 730, 200, 200);
			SubmitBlackWhite.setBounds(910, 850, 200, 99);

			//file setup
			SaveGamePrompt.setBounds(300, 860, 700, 50);
			fileName.setBounds(1000,860,130,50);
			saveButton.setBounds(1140,860,130,50);

			SaveGamePrompt.setFont(new Font("Arial", Font.PLAIN, 30));
			panel.add(SaveGamePrompt);
			SaveGamePrompt.setVisible(false);

			panel.add(fileName);
			panel.add(saveButton);
			fileName.setVisible(false);
			saveButton.setVisible(false);



			//set color
			Yes.setBackground(GColor);
			No.setBackground(RColor);
			//misc
			Monitor.setText("ATTEMPT 1: Is this your code?");
			panel.add(Monitor);
			panel.add(ReturnMenu);
			panel.add(Yes);
			panel.add(No);
			panel.add(SubmitBlackWhite);
			panel.add(EnterBlack);
			panel.add(Blacks);
			panel.add(Whites);
			panel.add(EnterWhite);
			Monitor.setOpaque(false);
			Monitor.setContentAreaFilled(false);
			Monitor.setBorderPainted(false);
			dimensionX = 360;
			dimensionY= 90;
			grid.clear();
			initializeGrid();
			round2 = 0;
			guess.clear();
			No.setVisible(true);
			Yes.setVisible(true);
			SubmitBlackWhite.setVisible(false);
			EnterBlack.setVisible(false);
			Blacks.setVisible(false);
			Whites.setVisible(false);
			EnterWhite.setVisible(false);

			for (int i = 0; i < 10; i++) {  //sets up a visual grid made up of JLabels. The edges of the grid are used for hints, centers for colours
				for (int j = 0; j < 6; j++) {
					JLabel label = new JLabel();
					if (j==0 || j==5) { //edges

						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(false);//make this JLabel invisible except the text. Creates an illusion that the grid is actually 4x10
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);  //add to the panel

					}else {//middle
						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(true);
						label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //make these JLabels have boundaries, for a visual effect
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);//add to the panel
					}
				}
				dimensionX = 360;
				dimensionY += 70; //adjust dimensions for a nice looking grid
			}

			combinations.clear();//reset combinations


			for (int i =0; i<VALID_CHARS.length; i++){ //Quadruple for loop, each running from 0-5, the indexes of the possible colours
				for (int j=0; j<VALID_CHARS.length; j++){
					for (int k=0; k<VALID_CHARS.length;k++){
						for (int l=0; l<VALID_CHARS.length;l++){
							ArrayList<String>combination = new ArrayList<String>(); //create a new string arrayList
							combination.add(VALID_CHARS[i]);
							combination.add(VALID_CHARS[j]);
							combination.add(VALID_CHARS[k]);
							combination.add(VALID_CHARS[l]);//create a unique combination
							combinations.add(combination);//add this unique combination to combinations
						}
					}
				}
			} //essentially adds all the possibilities to combinations.

			guess = combinations.get(rand.nextInt(combinations.size())); //choose one of these combinations and assign it to guess

			addGuessToGrid();
			displayGrid(); //methods used to display the first guess onto the screen


		}else if (command.equals("View a Saved Game")) {
			panel.removeAll();
			frame.setSize(1599, 1000);
			frame.setSize(1600, 1000);
			fileName2.setText(".txt"); //set up the new screen

			ReturnMenu.setFont(new Font("Arial", Font.PLAIN, 20));
			ReturnMenu.setBounds(20, 20, 200, 100);
			panel.add(ReturnMenu);//set up return to menu button


			//misc setup
			fileName2.setBounds(750, 34, 150, 35);
			uploadButton.setBounds(900, 34, 150, 35);

			Monitor2.setOpaque(false);
			Monitor2.setContentAreaFilled(false);
			Monitor2.setBorderPainted(false);
			Monitor2.setFont(new Font("Arial", Font.PLAIN, 30));
			Monitor2.setBounds(482, 17, 270, 65);
			Monitor2.setText("Enter a file name:");
			panel.add(Monitor2);
			panel.add(fileName2);
			panel.add(uploadButton);
			Monitor2.setVisible(true);
			fileName2.setVisible(true);
			uploadButton.setVisible(true);


			Monitor3.setOpaque(false);
			Monitor3.setContentAreaFilled(false);
			Monitor3.setBorderPainted(false);
			Monitor3.setFont(new Font("Arial", Font.PLAIN, 30));
			Monitor3.setBounds(500, 800, 555, 65);
			Monitor3.setText("");

			Monitor4.setOpaque(false);
			Monitor4.setContentAreaFilled(false);
			Monitor4.setBorderPainted(false);
			Monitor4.setFont(new Font("Arial", Font.PLAIN, 30));
			Monitor4.setBounds(500, 850, 555, 65);
			Monitor4.setText("");

			panel.add(Monitor3);
			Monitor3.setVisible(true);
			panel.add(Monitor4);
			Monitor4.setVisible(true);

			for (int i = 0; i < 10; i++) {  //sets up a visual grid made up of JLabels. The edges of the grid are used for hints, centers for colours
				for (int j = 0; j < 6; j++) {
					JLabel label = new JLabel();
					if (j==0 || j==5) { //edges

						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(false);//make this JLabel invisible except the text. Creates an illusion that the grid is actually 4x10
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);  //add to the panel

					}else {//middle
						label.setBackground(Color.WHITE);
						label.setBounds(dimensionX, dimensionY, 140, 71);
						dimensionX += 139;
						label.setOpaque(true);
						label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); //make these JLabels have boundaries, for a visual effect
						(grid.get(i)).add(label);//set up the grid static object
						panel.add(label);//add to the panel
					}
				}
				dimensionX = 360;
				dimensionY += 70; //adjust dimensions for a nice looking grid
			}

		}else if (command.equals("Upload")) {

			try {//use try catch to address errors related to file reading

				try{
					grid.get(readAttempts-1).get(0).setForeground (Color.BLACK);
					grid.get(readAttempts-1).get(5).setForeground (Color.BLACK); //reset previous green hints back to black, if applicable
				}catch(Exception ee){
				}

				readFile();
				modifyGridFile();
				displayGrid(); //methods to read the file and display it
				Monitor3.setText(fileName2.getText()+" has been uploaded"); //UFP



				if (readHours.equals("x")) {
					Monitor4.setText("AI Vs P, "+readAttempts+" attempt(s)"); //logically deduces what type of game was played, based on the first line of the file.
					//x means AI VS Player, and other value will be a Player Vs AI Game
				}else {
					Monitor4.setText("P Vs AI, "+readHours+"h, "+readMinutes+"m, "+readSeconds+"s, "+readAttempts+" attempt(s)"); //display the time spent on the game
				}

			} catch (Exception e1) {
				Monitor3.setText("Sorry, error/file not found"); //display in the case of an error
			}

		}


		else if (command.equals("NO")) {
			No.setVisible(false); //adjust visibility
			Yes.setVisible(false);


			Monitor.setText("Please enter hints");
			SubmitBlackWhite.setVisible(true);
			EnterBlack.setVisible(true);
			Blacks.setVisible(true);
			Whites.setVisible(true);
			EnterWhite.setVisible(true);
			//UFP and adjust visibility to transform the UI


		}
		else if (command.equals("Confirm")) {
			hintIsValid=false; //reset


			numberOfBlack=Blacks.getText();
			numberOfWhite=Whites.getText(); //get the user's inputted hints

			if(!validHint(guess,combinations,numberOfBlack,numberOfWhite)) {
				Monitor.setText("Impossible hints! Please try again"); //checks if the hints are valid
				//if invalid, the next parts of this code are inaccessible until hintIsValid is true
			}else {
				hintIsValid=true;//set to true
			}

			if (hintIsValid) { //this part of the code only runs when the hint is valid
				SubmitBlackWhite.setVisible(false);
				EnterBlack.setVisible(false);
				Blacks.setVisible(false);
				Whites.setVisible(false);
				EnterWhite.setVisible(false); //adjust visibility


				grid.get(round2).get(0).setFont(new Font("Arial", Font.PLAIN, 55));
				grid.get(round2).get(0).setText(" B: "+numberOfBlack); //set the font style and display the black hint

				grid.get(round2).get(5).setFont(new Font("Arial", Font.PLAIN, 55));
				grid.get(round2).get(5).setText(" W: "+numberOfWhite); //set the font style and display the white hint

				round2++;//accumulate the round number

				//now, generate the next guess

				removePossibilities(guess, combinations, Integer.parseInt(numberOfBlack), Integer.parseInt(numberOfWhite));
				//remove all the impossible cases based on the given hint

				guess = combinations.get(rand.nextInt(combinations.size()));
				//choose a new guess randomly from the remaining pool of possibilities


				addGuessToGrid();
				displayGrid();//display the next guess

				if (combinations.size()==1) { //if combinations only has one more possibility, this means that the user's
					//code is pinpointed
					Yes.setVisible(true); //adjust visibility
					Monitor.setText("Okay, this has to be it! Correct?"); //UFP
					No.setVisible(false); //make the no button invisible, as it is impossible to be wrong if the user
					//gave accurate hints
				}else {

					Monitor.setText("ATTEMPT "+(round2+1)+": Is this your code?"); //UFP
					No.setVisible(true);;//Adjust visibility
					Yes.setVisible(true);
				}

				Blacks.setText(""); //reset
				Whites.setText("");
			}

			
		}else if (command.equals("YES")) { // tracks to see if the button that has "yes" on it is clicked. In this case, this means that the AI has correctly guessed the code the user came up with

			Yes.setVisible(false); // sets buttons "Yes" and "No" to invisible (the user cannot see them anymore)
			No.setVisible(false);

			grid.get(round2).get(0).setFont(new Font("Arial", Font.PLAIN, 55)); //sets the font of the text that will tell the user how many blacks the current guess of the AI has (entered by the user)
			grid.get(round2).get(0).setForeground (Color.green); //sets the color of the text displayed to green
			grid.get(round2).get(0).setText(" B: 4"); //sets the text to "B: 4" as this means that the AI has guessed the code correctly thus every color is in the right spot thus the guess has 4 blacks

			grid.get(round2).get(5).setFont(new Font("Arial", Font.PLAIN, 55)); //sets the font of the text that will tell the user how many whites the current guess of the AI has (entered by the user)
			grid.get(round2).get(5).setForeground (Color.green); //sets the color of the text displayed to green
			grid.get(round2).get(5).setText(" W: 0"); //sets the text to "W: 0" as the AI has correctly guessed the code meaning that there are no whites, only blacks


			Monitor.setText("GG! Attempts: "+(round2+1)); //sets the text on the monitor label

			SaveGamePrompt.setVisible(true); // sets the SaveGamePrompt, fileName and saveButton buttons, labels and text fields to visible (the user can see this on the GUI)
			fileName.setVisible(true);
			saveButton.setVisible(true);
		}

		else if (command.equals("See Instructions")) { //tracks to see if the button with the text "See Instructions" has been clicked.

			panel.removeAll(); //remove all elements on the panel/frame
			frame.setSize(1599, 1000); //adjusts the size of the panel. This and the next line essentially act as a refresh allowing for the panel to remove all elements
			frame.setSize(1600, 1000);

			ReturnMenu.setFont(new Font("Arial", Font.PLAIN, 20)); //this is simply setting the font of the corresponding label, the parameters are font type, how to format the font and the font size
			ReturnMenu.setBounds(20, 20, 200, 100); //sets the location of the ReturnMenu button, parameters are x location, y location, width, height
			panel.add(ReturnMenu); //adds the ReturnMenu button to the panel

			InstructionPVAI.setFont(new Font("Arial", Font.PLAIN, 25));
			InstructionPVAITitle.setFont(new Font("Arial", Font.BOLD, 35));

			InstructionAIVP.setFont(new Font("Arial", Font.PLAIN, 25));
			InstructionAIVPTitle.setFont(new Font("Arial", Font.BOLD, 35));

			WelcomeInstruction.setFont(new Font("Arial", Font.BOLD, 50));
			WelcomeInstruction.setBounds(620, 10, 500, 200); //sets location of WelcomeInstruction label

			InstructionPVAI.setBounds(50, 150, 700, 900);//setting the location of each individual java GUI element
			InstructionPVAITitle.setBounds(50, 280, 1000, 100);

			InstructionAIVP.setBounds(900, 100, 700, 900);
			InstructionAIVPTitle.setBounds(900, 280, 1000, 100);

			panel.add(InstructionPVAI); //adding corresponding elements to the panel so that the user can see them
			panel.add(WelcomeInstruction);
			panel.add(InstructionPVAITitle);
			panel.add(InstructionAIVP);
			panel.add(InstructionAIVPTitle);
			// stop commenting
		}

		else if (command.equals("Return to Menu")) { //if the user presses the button "Return to Menu"

			panel.removeAll(); //remove all elements of the panel
			frame.setSize(1599, 1000); //refresh the screen
			frame.setSize(1600, 1000);

			TitleLabel.setBounds(640, 150, 300, 300);//setting location of corresponding elements (how they are arranged on the panel)
			InstructionGame.setBounds(40, 500, 350, 230);
			PlayerVsAI.setBounds(420, 500, 350, 230);
			AIVsPlayer.setBounds(800, 500, 350, 230);
			viewGame.setBounds(1180, 500, 350, 230);

			//add items to panel
			panel.add(TitleLabel); //adding elements to panel
			panel.add(PlayerVsAI);
			panel.add(AIVsPlayer);
			panel.add(InstructionGame);
			panel.add(viewGame);

			TitleLabel.setVisible(true); //setting corresponding elements visibility to true so that the user can see them
			PlayerVsAI.setVisible(true);
			AIVsPlayer.setVisible(true);
			InstructionGame.setVisible(true);

			viewGame.setVisible(true);
			dimensionX = 360; //sets dimensionX to 360 so as to reset the grid's (where colors are placed depending on game mode) starting location, dimensionX and dimensionY are both used within the setBounds function as parameters (x location ,y location respectively)
			dimensionY= 90; //sets dimensionY to 90 so as to reset the grid's starting position
			grid.clear(); //clears the array list grid of all elements within it. This is done to restart the game's grid (erase all colors that were stored within for the game)
			initializeGrid(); //uses initializeGrid() which is a method that essentially prepares the array list grid for the next game by adding the labels that will represent colors to it.
			round = 0; //round is set to 0 so as to restart the game (clear the progress of the game last played) and make sure that the next game that is played, it will start at the first 4 grid squares. Essentially just restarting the game
			guess2.clear(); // clears the elements within the array list guess2

		}

		else if (command.equals(" ")) { //This checks to see if the button with the color red on it is pressed, the button is blank so the number of spaces is used to differentiate the buttons with color on them
			Monitor.setText(""); //sets the text on the monitor label to blank



			if (guess2.size()<4) { //if guess2 size is less than 4 meaning that they have not entered in 4 colors yet

				guess2.add("R"); //add "R" to the guess2 array list
				grid.get(round).get(guess2.size()).setText(" "); //finds the corresponding row (indicated by the round variable, basically how many guesses they have made) then uses the length of the current guess to figure out where to place the " " which indicates red
				displayGrid(); //uses the method displayGrid which basically generates the grid that will hold the user's guesses and creates a visual display using labels

			}else {
				Monitor.setText("Max reached!"); //basically checks to see if they have already entered in 4 colors and if they have, the monitor will display this text notifying them that they cannot enter any more colors in the current guess

			}


		}
		else if (command.equals("   ")) { //basically the same as the one above, its the same as the if statement that checks to see if red is pressed
			Monitor.setText(""); //the rest of the code is adjusted accordingly to the colors that the else if statement checks for.
			// this particular else if statement, checks to see if blue is pressed


			if (guess2.size()<4) {
				guess2.add("B");
				grid.get(round).get(guess2.size()).setText("   ");
				displayGrid();

			}else {
				Monitor.setText("Max reached!");

			}

		}
		else if (command.equals("  ")) { //checks to see if green is pressed
			Monitor.setText("");


			if (guess2.size()<4) {
				guess2.add("G");
				grid.get(round).get(guess2.size()).setText("  ");
				displayGrid();

			}else {
				Monitor.setText("Max reached!");

			}

		}
		else if (command.equals("      ")) { //checks to see if orange is pressed
			Monitor.setText("");

			if (guess2.size()<4) {
				guess2.add("O");
				grid.get(round).get(guess2.size()).setText("      ");
				displayGrid();

			}else {
				Monitor.setText("Max reached!");

			}

		}
		else if (command.equals("    ")) { //checks to see if yellow is pressed
			Monitor.setText("");

			if (guess2.size()<4) {
				guess2.add("Y");
				grid.get(round).get(guess2.size()).setText("    ");
				displayGrid();

			}else {
				Monitor.setText("Max reached!");

			}

		}
		else if (command.equals("     ")) { //checks to see if purple is pressed
			Monitor.setText("");

			if (guess2.size()<4) {
				guess2.add("P");
				grid.get(round).get(guess2.size()).setText("     ");
				displayGrid();

			}else {
				Monitor.setText("Max reached!");

			}

		}
		else if (command.equals("Delete")) { //checks to see if the button with the text "Delete" is pressed
			Monitor.setText(""); // set text on monitor to blank

			if(guess2.size()>0) { //basically checks to make sure that there is already a color input in the guess2 array, basically to make sure that there is actually something to delete
				guess2.remove(guess2.size()-1); //removes the last element in the guess2 array using its length minus 1


				grid.get(round).get(guess2.size()+1).setText("       "); //this finds the index in the last position of the corresponding round and sets it to the specific number of blanks indicating a deleted color

				displayGrid(); //displays the grid using the displayGrid method

			}else {
				Monitor.setText("Cannot delete!"); //if there isn't any colors currently within the guess2 array list, the monitor label's text will be set to this indicating that there isn't anything for the user to delete


			}
		}
		else if (command.equals("Submit")) {//checks to see if the user clicked the button with the text "Submit" and executes code if the button is pressed by the user


			Monitor.setText(""); //sets monitor text to blank
			if (!(guess2.size()==4)) { //if the size of the guess2 array list is not 4 that means they haven't entered in 4 colors thus their guess is incomplete

				Monitor.setText("Please enter a full guess!"); //this message will pop up on the monitor label telling the user they need to enter 4 colors before they can submit
			}

			else { //if they have entered in 4 colors they can now properly submit
				if (round==0){ //after they submit their first guess, the timer will start
					start(); //start method that starts the timer in the top right of the GUI
				}

				if(checkEquals(key, guess2)) { //checkEquals method using that essentially checks the user's guess in guess2 array list and the key array list which contains the randomly generated pass code and if it does that means the user has correctly guessed the code
					//this code will execute when the user correctly guesses the code, basically telling the user they have won
					stop(); //stop method that stops the timer
					Monitor.setText("YOU WON! Time: "+hours+"hrs, "+minutes+"mins, "+seconds+"secs"); //sets the monitor text to display the message that the user has won as well as the time it took them to correctly guess the code
					GuessCode.setText("GG! Attempts: "+(round+1));//UFP
					grid.get(round).get(0).setFont(new Font("Arial", Font.PLAIN, 55)); //sets text of the label that tells the user how many blacks their guess has
					grid.get(round).get(0).setForeground (Color.green); //sets the text to green
					grid.get(round).get(0).setText(" B: "+getHint(key,guess2).get(0)); //sets the number of blacks to 4 using the getHint method that finds the number of blacks and by this point, it should be 4 blacks

					grid.get(round).get(5).setFont(new Font("Arial", Font.PLAIN, 55)); //same as previous lines except for whites
					grid.get(round).get(5).setForeground (Color.green);
					grid.get(round).get(5).setText(" W: "+getHint(key,guess2).get(1)); //sets the number of whites to 0 using getHint method which should return 0 upon winning the game


					Red.setVisible(false); //setting certain elements of the GUI (labels, buttons, text fields) to being visible or not visible depending on need
					Blue.setVisible(false);
					Orange.setVisible(false);
					Purple.setVisible(false);
					Green.setVisible(false);
					Yellow.setVisible(false);

					RedLabel.setVisible(false);
					BlueLabel.setVisible(false);
					OrangeLabel.setVisible(false);
					PurpleLabel.setVisible(false);
					GreenLabel.setVisible(false);
					YellowLabel.setVisible(false);

					Delete.setVisible(false);
					Submit.setVisible(false);
					SaveGamePrompt.setVisible(true); //erase all the user inputs for colors and add the save game stuff
					fileName.setVisible(true);
					saveButton.setVisible(true);



				}else { // this will activate if the user's guess is not the same as the randomly generated pass code
					GuessCode.setText("Enter Attempt "+(round+2));//UFP

					grid.get(round).get(0).setFont(new Font("Arial", Font.PLAIN, 55)); // sets font of the text displaying number of blacks
					grid.get(round).get(0).setText(" B: "+getHint(key,guess2).get(0)); // uses the getHint method to find the number of blacks

					grid.get(round).get(5).setFont(new Font("Arial", Font.PLAIN, 55)); //sets the font of the text displaying number of whites
					grid.get(round).get(5).setText(" W: "+getHint(key,guess2).get(1)); //uses the getHint method to find the number of whites

					if(round==9) { //this activates if the user has used all 10 of their tries.
						GuessCode.setText("GAME OVER");//UFP
						Red.setVisible(false); //disables all user inputs (makes the buttons invisible) because they  have used up all their tries
						Blue.setVisible(false);
						Orange.setVisible(false);
						Purple.setVisible(false);
						Green.setVisible(false);
						Yellow.setVisible(false);

						RedLabel.setVisible(false);
						BlueLabel.setVisible(false);
						OrangeLabel.setVisible(false);
						PurpleLabel.setVisible(false);
						GreenLabel.setVisible(false);
						YellowLabel.setVisible(false);

						Delete.setVisible(false);
						Submit.setVisible(false);
						Monitor.setText("You have used all your tries!"); //displays text on the monitor
					}

					guess2.clear(); //clears the guess2 array list
					round++; //increments round by 1 signifying that they have used up one of their 10 guesses

				}
			}

		}else if(command.equals("Save")) { //activates if the user presses the button with the text "Save"
			try { //try catch
				writeFile(fileName.getText(),gameMode); //uses the file reading method to create a file with the corresponding name entered into the text field
				Monitor.setText("Successfully Saved to "+fileName.getText()); //displays this message on the monitor label
			} catch (IOException e1) { //
				e1.printStackTrace();
			}

		}


	} //end of action performed method


	/**addGuessToGrid method:

This procedural method adds the user's input (color) into the grid array list
@param array list guess
@param array list grid
@return void
	 */
	public void addGuessToGrid(){ //start of addGuessToGrid method



		for (int x=0; x<guess.size();x++){ //the code runs through the guess array list
			// number of spaces indicate a specific color
			if (guess.get(x)=="R"){ //if the current index in the guess array list contains "R", it will add the corresponding number of spaces as an element in the grid array list
				grid.get(round2).get(x+1).setText(" ");

			}else if (guess.get(x)=="G"){ //the rest of the else if statements do the same thing, they check what color is in the guess array list and add it in the corresponding index in the grid array list
				grid.get(round2).get(x+1).setText("  ");

			}else if (guess.get(x)=="B"){
				grid.get(round2).get(x+1).setText("   ");

			}else if (guess.get(x)=="Y"){
				grid.get(round2).get(x+1).setText("    ");

			}else if (guess.get(x)=="O"){
				grid.get(round2).get(x+1).setText("      ");

			}else if (guess.get(x)=="P"){
				grid.get(round2).get(x+1).setText("     ");

			}
		}


	} //end of addGuessToGrid method


	// end of GUI Methods




	/*writeFile method:
	 *
	 * This procedural method writes to a specified file. It takes the game that the user decides to save, and converts it into a
	 * text based mapping of each guess and the hints associated with it. For games where the computer sets the code, the time taken is also written to the file.
	 * Through the timerNeeded paramater, this method can create
	 * text files unique to both game modes, as only one game mode has a timer attached to it.
	 *
	 * LOCAL VARIABLES:
	 * temp: used to temporarily store the text of a JLabel, used to determine specific character(s) to write, Type: String
	 * writer: used to write to the file, Type: BufferedReader
	 *
	 *
	 * @param n/a
	 * @return void
	 * @throws n/a
	 */
	public void writeFile(String fileName,boolean timerNeeded) throws IOException{
		String temp;//initialize
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));

		if(timerNeeded) {//if the game mode involves a timer

			writer.write(Integer.toString(hours));
			writer.newLine();

			writer.write(Integer.toString(minutes));
			writer.newLine();


			writer.write(Integer.toString(seconds));
			writer.newLine(); //write the hours, minutes, and seconds onto the first 3 lines of the file
		}else {
			writer.write("x"); //if the user is saving from the other game mode, where there is no timer, fill the first 3 lines with placeholders
			writer.newLine();
			writer.write("x");
			writer.newLine();
			writer.write("x");
			writer.newLine();
		}



		for (int x=0;x<grid.size();x++) {
			for (int y=0;y<grid.get(x).size();y++) { //run through the grid

				temp=grid.get(x).get(y).getText(); //extract the text value of each element in the grid

				if (temp==" "){
					writer.write("R");
				}
				else if (temp=="  "){
					writer.write("G");
				}
				else if (temp=="   "){
					writer.write("B");
				}
				else if (temp=="    "){
					writer.write("Y");;
				}
				else if (temp=="      "){
					writer.write("O");
				}
				else if (temp=="     "){
					writer.write("P"); //each colour has a corresponding number of spaces. Extract the colour based on spaces
				}else {
					if (temp.length()>0) { //for the edges (hints)
						writer.write(temp.substring(temp.length() - 1)); //extract only the numberical part of the hint, and write it
					}
				}
			}
			writer.newLine();///essential to proper writing
		}

		writer.close();//close the writer
	}


	/*readFile method:
	 *
	 * This procedural method reads a file and converts it into a 2D String Arraylist. This Arraylist will then be processed to be displayed
	 * for the user in another part of the code
	 * LOCAL VARIABLES:
	 * file: an object refering to the file that is read, Type: java.io.File
	 * input: used to read the file, type: BufferedReader
	 *
	 * @param n/a
	 * @return void
	 * @throws n/a
	 */
	public void readFile()throws IOException{
		arrayGrid.clear(); //reset arrayGrid

		File file = new java.io.File(fileName2.getText()); //creates an object named file, an object of the File class

		BufferedReader input = new BufferedReader(new FileReader(file)); //creates a new object called input (of the BufferedReader class). This will be used to read from a file

		for (int x=0; x<10;x++){
			arrayGrid.add(new ArrayList<String>());
		}//initializes arrayGrid to length 10

		readHours=input.readLine();
		readMinutes=input.readLine();
		readSeconds=input.readLine();//read the first 3 lines

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				char character=(char)input.read(); //read the next character
				arrayGrid.get(i).add(Character.toString(character));//add that character to the respective spot in arrayGrid
			}
			input.readLine();//next line

		}
		input.close();//close the reader



	}

	/*modifyGridFile method:
	 *
	 * This procedural method uses the filled in arrayGrid and modifies grid accordingly. This helps the user to see visually the
	 * game that they saved
	 * LOCAL VARIABLES:n/a
	 *
	 * @param n/a
	 * @return void
	 * @throws n/a
	 */
	public static void modifyGridFile (){

		readAttempts=0; //reset
		for (int i = 0; i < arrayGrid.size(); i++) {
			for (int j = 0; j < arrayGrid.get(i).size(); j++) { //double for loop runs through arrayGrid


				if (j==0) { //if on the leftmost column

					if(arrayGrid.get(i).get(0).equals("0") ||arrayGrid.get(i).get(0).equals("1") || arrayGrid.get(i).get(0).equals("2")||arrayGrid.get(i).get(0).equals("3")||arrayGrid.get(i).get(0).equals("4")) {
						readAttempts++;//accumulate
						//if statmement to check if a hint is present in that row
						if (arrayGrid.get(i).get(0).equals("4")) { //if arrayGrid here is 4, this means that this was the correct guess
							grid.get(i).get(0).setForeground (Color.green);
							grid.get(i).get(5).setForeground (Color.green);//set colour to green, for corrrect
						}

						grid.get(i).get(0).setFont(new Font("Arial", Font.PLAIN, 55));
						grid.get(i).get(0).setText(" B: "+arrayGrid.get(i).get(0));//else, modify the black hint by adding some text before it

					}else {
						grid.get(i).get(0).setText("");//set the text to nothing, if no hint is present
					}

				}

				else if (j==5) {//if on the rightmost column

					if(arrayGrid.get(i).get(0).equals("0") ||arrayGrid.get(i).get(0).equals("1") || arrayGrid.get(i).get(0).equals("2")||arrayGrid.get(i).get(0).equals("3")||arrayGrid.get(i).get(0).equals("4")) {
						//if statement to check if a hint is present in that row

						grid.get(i).get(5).setFont(new Font("Arial", Font.PLAIN, 55));
						grid.get(i).get(5).setText(" W: "+arrayGrid.get(i).get(5));//modify the white hint by adding some text before it
					}else {
						grid.get(i).get(5).setText("");//set the text to nothing, if no text is present
					}

				}

				else if (arrayGrid.get(i).get(j).equals("R")){
					grid.get(i).get(j).setText(" ");;
				}
				else if (arrayGrid.get(i).get(j).equals("G")){
					grid.get(i).get(j).setText("  ");;
				}
				else if (arrayGrid.get(i).get(j).equals("B")){
					grid.get(i).get(j).setText("   ");;
				}
				else if (arrayGrid.get(i).get(j).equals("Y")){
					grid.get(i).get(j).setText("    ");;
				}
				else if (arrayGrid.get(i).get(j).equals("P")){
					grid.get(i).get(j).setText("     ");;
				}
				else if (arrayGrid.get(i).get(j).equals("O")){
					grid.get(i).get(j).setText("      ");; //set the colours to their respective amount of spaces
				}else {
					grid.get(i).get(j).setText("            ");//if empty, set to a placeholder
				}

			}

		}
	}

	/**displayGrid method:

This procedural method checks the grid array list and uses the information within it to create a grid containing all the user's inputs and guesses
@param grid array list
@return void
	 */
	public static void displayGrid (){ // start of displayGrid method

		for (int i = 0; i < 10; i++) { //creates a grid that is 6 by 10, 2 of those spaces are used for the blacks and whites counters.
			for (int j = 0; j < 6; j++) {

				JLabel label = (grid.get(i).get(j));; //sets a label object to what is currently in the grid array list at the index i, j
				if (label.getText()==" "){ // if the label's text is the corresponding number of spaces for a color, change the color of the label to the corresponding color. Example if the number of spaces indicates red, change the background of the label to red
					label.setBackground(RColor); //checks for red
				}
				else if (label.getText()=="  "){ //same as previous lines except different colors this one is for green
					label.setBackground(GColor);
				}
				else if (label.getText()=="   "){ //checks for blue
					label.setBackground(BColor);
				}
				else if (label.getText()=="    "){ //checks for yellow
					label.setBackground(YColor);
				}
				else if (label.getText()=="      "){ //checks for orange
					label.setBackground(OColor);
				}
				else if (label.getText()=="     "){ //checks for purple
					label.setBackground(PColor);
				}else {
					label.setBackground(Color.WHITE); //if there isn't any spaces in the grid array list index (at i, j) this means the user has yet to input anything here thus it is a white spot
				}
				panel.add(label); //adds the label to the panel
			}

		}
	} //end of displayGrid method


	/**removePossibilities method:

This procedural method uses the array list combinations that generates every possible combination given the six colors.
This method in particular will remove possibilities from this array list based the number of blacks and whites
@param array list guess
@param array list combinations
@param int black
@param int white
@return void
	 */
	public static void removePossibilities (ArrayList<String>guess, ArrayList<ArrayList<String>> combinations, int black, int white){ //start of removePossibilities method
		ArrayList<ArrayList<String> > impossibleCases = new ArrayList<ArrayList<String>>(); //creates a new array list in order to store combinations that are impossible given the user's hints


		for (int x=0; x<combinations.size();x++){
			if (!validCombination(combinations.get(x), guess, black, white )){ //uses validCombination method to see if the combination is valid

				impossibleCases.add(combinations.get(x)); //if the combination is not valid add it to the impossibleCases array list

			}
		}

		for (int x=0; x<impossibleCases.size();x++) {
			combinations.remove(impossibleCases.get(x)); //removes all the cases inside the impossibleCases array list from combinations array list
		}
	} //end of removePossibilities method



	/**validHint method:

This procedural method uses the array list combinations that generates every possible combination given the six colors.
This method in particular will check if a hint is valid
@param array list guess
@param array list combinations
@param String sBlack
@param String sWhite
@return boolean
	 */
	public static boolean validHint (ArrayList<String>guess, ArrayList<ArrayList<String>> combinations,String sBlack, String sWhite){ //start of validHint method
		int black;
		int white;

		try { //reads the black input from user and stores it in int black
			black=Integer.parseInt(sBlack);
		}catch(Exception e) {
			return false;
		}

		try { //reads the white input from user and stores it in int white
			white=Integer.parseInt(sWhite);

		}catch(Exception e) {
			return false;
		}


		ArrayList<Integer> hint = new ArrayList <Integer>(); //declare new array list hint
		hint.add(black); //adds number of blacks to the array list
		hint.add(white); //adds number of whites to the array list

		ArrayList<ArrayList<Integer>> allPossibleHints = new ArrayList<ArrayList<Integer>>(); //declaration of new array list allPossibleHints

		for (int x=0;x<combinations.size();x++){
			allPossibleHints.add(getHint(combinations.get(x),guess)); //uses getHint method to check if the hints given by the user is a possible hint
		}

		if (allPossibleHints.contains(hint)){ //if the hint given by the user exists in the allPossibleHints array list, that means it is a valid hint
			return true; //return boolean value of true
		}

		return false; //otherwise return value of true
	} //end of validHint method

	/*validCombination method:
	 *
	 * This functional method is an integral part of the AI algorithm that guesses the user's code. It works by accepting 4 parameters,
	 * a test combination, the computer's guess, and the user's inputted amount of blacks and whites. It will return either true or false,
	 * depending on if the test combination (hypothetical key) compared to the guess returns the same number of blacks and whites as the hint
	 * (making it a possibility for being the user's key)
	 *
	 * LOCAL VARIABLES
	 * dupeTest: used to represent the same value as test, but in a different variable to avoid aletering the original by reference, Type: ArrayList<String>
	 * dupeGuess: used to represent the same value as guess, but in a different variablle to avoid altering the original by reference, Type: ArrayList<String>
	 * remove: an arraylist used to store indexes to be removed (to avoid double counting after blacks have been counted), ArrayList<Int>
	 * black and white: used to represent the black and white pegs, Type: int
	 * colour: used to store the colour of a white peg to avoid double counting, Type; String
	 *
	 *
	 * @param ArrayList<String> test, ArrayList<String> guess, int numBlack, int numWhite
	 * @return boolean
	 * @throws n/a
	 */

	public static boolean validCombination (ArrayList<String> test, ArrayList<String> guess, int numBlack, int numWhite){

		ArrayList<String> dupeTest = new ArrayList<String>(); //initialize
		ArrayList<String> dupeGuess= new ArrayList<String>();

		for (int x=0;x<test.size();x++){
			dupeTest.add(test.get(x));
		}

		for (int x=0;x<guess.size();x++){
			dupeGuess.add(guess.get(x));
		}

		//safely set dupeTest and dupeGuess equal to the original, without changing the original

		ArrayList<Integer> remove = new ArrayList<Integer>(); //initialize

		String colour;
		int black=0;
		int white=0;//initialize

		for (int x=0;x<dupeGuess.size();x++){
			if (dupeGuess.get(x).equals(dupeTest.get(x))){ //runs through the guess and test, checks if any 2 same indexes match
				black++;//accumulate black
				remove.add(x);//add x to the remove array
			}
		}

		remove=improvedBubbleSort(remove);//sort the remove array, for easy removal

		for (int x=0; x<remove.size(); x++){
			int temp=remove.get(x);
			dupeGuess.remove(temp);
			dupeTest.remove(temp);//remove the indexes included in the remove array
		}

		for (int x=0;x<dupeGuess.size();x++){
			if (dupeTest.contains(dupeGuess.get(x))){//checks to see if any part of the guess has a colour included in the test
				colour = dupeGuess.get(x); //extract that colour
				white++;//accumulate
				dupeTest.set(dupeTest.indexOf(colour),"X");//change that index to a placeholder to avoid double counting
				dupeGuess.set(x,"X");//change that index to a placeholder to avoid double counting
			}
		}

		if (black==numBlack && white == numWhite){ //checks if the calculated amount of blacks and whites agree with the user
			return true;
		}

		return false;//return false otherwise
	}

	/*improvedBubbleSort method:
	 *
	 * This functional method is in charge of taking an integer arraylist and sorting it highest to lowest.
	 * This method is to be used on the to sort the remove arrayList in the above method. It is important that the remove
	 * arrayList is sorted in this fashion, to ensure that when removing indexes, nothing shifts in a way that reorders the indexes
	 *
	 *
	 * LOCAL VARIABLES
	 * array2: used as a copy of array to avoid changing any original values by reference, Type: ArrayList<Integer>
	 * x: used as an accumulator, Type: Int
	 * swapped: used to see if a swap took place, Type: boolean
	 * temp: used to temporarily store a value during a swap, Type: int
	 *
	 *
	 * @param ArrayList<Integer> array
	 * @return ArrayList<Integer
	 * @throws n/a
	 */


	public static ArrayList<Integer> improvedBubbleSort (ArrayList <Integer> array){//method start
		ArrayList<Integer> array2 = new ArrayList<Integer>();  //initialization
		for (int x=0; x<array.size();x++){
			array2.add(array.get(x));
		}//set array2 equal to array using for loop

		int x=0;
		boolean swapped=false;
		int temp; //initialize
		do{
			swapped=false;
			for (int y=0; y<array2.size()-1-x;y++){
				if (array2.get(y)<array2.get(y+1)){ //compares the sizes of adjacent elements
					swapped=true;//set swapped to true
					temp=array2.get(y);
					array2.set(y,array2.get(y+1));
					array2.set(y+1, temp); //swap
				}
			}
			x++;//accumulate
		}while(swapped);//do while loop runs while swapped is true
		return array2;//return the array
	}//method end



	/**getInput method:

this return type method returns user input in the form of an integer
reader - A BufferedReader object used to read user input - type: BufferedReader
String number
@return int
@throws IOException
	 */
	public static int getInput () throws IOException{ //start of getInput method
		boolean valid=false; //declaration of variables
		String number;

		BufferedReader br = new BufferedReader (new InputStreamReader(System.in)); //declaration of BufferedReader object

		while(true) //while loop and try and catch to read user input and check if its a valid input or not and if it isn't display the message
			try{
				number = br.readLine();
				return Integer.parseInt(number);
			}catch (Exception e){
				System.out.println("Invalid input, please try again"); //prompt telling user they entered an invalid input
			}
	} //end of getInput Method

	/**getRandomCode method:
This return type method generates a random code and returns it
random - a Random object used to generate a random code - type: Random
@return string array list
	 */
	public static ArrayList<String> getRandomCode(){ //start of getRandomCode method
		ArrayList<String> code = new ArrayList<String>(); //declaration of array list code that holds the randomly generated code
		Random rand = new Random (); //declaration of random object

		for (int x=0; x<SIZE; x++){ //creates a random code generated using characters from the VALID_CHARS array (R, G, B, Y, O, P)
			code.add(VALID_CHARS[rand.nextInt(VALID_CHARS.length)]); //adds a character from the VALID_CHARS array to the code array list
		}

		return code; //returns the code array list
	} //end of getRandomCode method
	/**getUserCode method:
This return type method reads the user's guess and makes sure that it is a valid one, it then translates the character to a string
reader - A BufferedReader object used to read user input - type: BufferedReader
@return string array list
@throws IOException
	 */
	public static ArrayList <String> getUserCode() throws IOException{  //start of getUserCode method
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in)); //declaration of BufferedReader object
		String guess = br.readLine(); //read the user's input and store in guess

		while (!checkValid(guess)){ //uses checkValid method to make sure guess is a valid one
			System.out.println("Invalid guess, please try again:"); //if the guess is not valid, print this message
			guess = br.readLine(); ///read guess again (retry)
		}

		ArrayList<String> arrayGuess = new ArrayList<String>(); //declaration of arrayGuess string array list

		for (int x=0;x<SIZE;x++){ // adds the user's guess to the arrayGuess array list (char to string)
			arrayGuess.add(Character.toString(guess.charAt(x)));
		}
		return arrayGuess; //returns the arrayGuess array list
	} //end of getUserCode method

	/**checkValid method:
This return type method checks to see if the user's guess is valid or not
@param String guess
@return boolean
	 */
	public static boolean checkValid(String guess) { //start of checkValid method
		if (guess.length()!=SIZE){ //checks if guess length is equal to the size of a guess, meaning they need a full guess
			return false;
		}

		for (int x=0; x<guess.length();x++){  //uses the check method to check validity of guess
			if (!check(Character.toString(guess.charAt(x)))){
				return false;
			}
		}
		return true;
	} //end of checkValid method
	/**check method:
this return type method checks and makes sure that the user's guess consists of only the characters in the VALID_CHARS array
@param String target
@return boolean
	 */
	public static boolean check (String target){ //start of check method
		for (int x=0;x<VALID_CHARS.length;x++){ // loop that makes sure that each character in guess is equal to at least one of the characters in VALID_CHAR array
			if (VALID_CHARS[x].equals(target)){
				return true;
			}
		}
		return false; //returns false if their isn't a character in guess that is one of the characters in array VALID_CHAR
	} //end of check method
	/**checkEquals method:
This return type method checks to see if two array lists are equal to each other or not
@param ArrayList<String> a
@param ArrayList<String> b
@return boolean
	 */
	public static boolean checkEquals (ArrayList<String> a, ArrayList<String> b){ //start of checkEquals method
		for (int x=0; x<a.size();x++){
			if (!a.get(x).equals(b.get(x))){ //checks each element in each index of array list a and compares it to the corresponding index in array list b and checks for equality
				return false; //returns false if the array lists are not equal

			}
		}

		return true; //returns true if they are equal (the array list a & b)
	} //end of checkEquals method
	/**getHint method:
This return type method finds the correct hint for the AI to give to the player and returns blacks and whites based on the user's guess
@param ArrayList<String> key
@param ArrayList<String> guess
@return int array list getHint
	 */
	public static ArrayList<Integer> getHint (ArrayList<String> key, ArrayList<String> guess){ //start of getHint method
		ArrayList<Integer> hint = new ArrayList<Integer>(); //declaration of array list hint


		ArrayList<String> dupeKey = new ArrayList<String>(); //declaration of array list dupeKey
		ArrayList<String> dupeGuess= new ArrayList<String>(); //declaration of array dupeGuess

		for (int x=0;x<key.size();x++){ //duplicates the array list key and creates the array dupeKey (duplicate of key)
			dupeKey.add(key.get(x)); //stores every element in key to dupeKey
		}

		for (int x=0;x<guess.size();x++){//duplicates the array list guess and creates the array dupeGuess (duplicate of guess)
			dupeGuess.add(guess.get(x)); //stores every element in guess to dupeGuess
		}

		ArrayList<Integer> remove = new ArrayList<Integer>(); //declaration of array list remove


		String colour; //declaration of local variables colour, black, white
		int black=0;
		int white=0;

		for (int x=0;x<dupeGuess.size();x++){
			if (dupeGuess.get(x).equals(dupeKey.get(x))){ //if the color in the index x of dupeGuess is equal to the color in the index of dupeKey, that means the color is in the right spot meaning that is a black
				black++; //increment black by 1 meaning the user has a black in their guess
				remove.add(x); //add the x value (for loop) to array list remove
			}
		}


		remove=improvedBubbleSort(remove); // sorts the contents of the array list remove



		for (int x=0; x<remove.size(); x++){ //for loop basically erases the contents of remove in that index from dupeGuess and dupeKey
			int temp=remove.get(x);
			dupeGuess.remove(temp);
			dupeKey.remove(temp);
		}


		for (int x=0;x<dupeGuess.size();x++){
			if (dupeKey.contains(dupeGuess.get(x))){ //checks for whites, if the color exists in dupeKey from dupeGuess but not at the same index, add 1 to white to indicate the user has a white (correct color, wrong spot)
				colour = dupeGuess.get(x); //store the color in this (white one, correct color, wrong spot)
				white++;
				dupeKey.set(dupeKey.indexOf(colour),"X"); //setting dupeKey index with the value in variable colour, and changing the value to "X" for future referencing
				dupeGuess.set(x,"X"); //set dupeGuess value at index x (for loop) as "X"
			}
		}



		hint.add(black); //add number of blacks and whites to the hint array list, it should be formatted like [blacks,whites]
		hint.add(white);
		return hint; //return hint array list
	}//end of getHint method
	/**intializeGrid method:
This procedural method initializes the base state of the grid, making placeholder labels essentially.
@param array list grid
@return void
	 */
	public static void initializeGrid(){ //start of initializeGrid method
		for (int x=0; x<10;x++){ //adds an array list for storing labels into grid array list 10 times (for ten guesses)
			grid.add(new ArrayList<JLabel>()); //adding array lists to grid
		}
	}//end of initializeGrid method


} //end of codeBreaker Class
