
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 */

/**
 * @author US 
 * fix and develop by Vinh - legend1250
 * https://gitlab.com/vinhnguyen1211/cHw
 */
public class cHW01_Calculator_T151487 extends JFrame {

	JTextArea txtFormula = new JTextArea();
	JTextField txtInput = new JTextField();
	int y0 = 150; // panel start from this y
	int y1 = 10, widthInput = 35, widthFml = 70;

	public cHW01_Calculator_T151487() {
		setTitle("Calculator");
		setSize(500, 500);
		this.setLayout(null);
		initMenu();
		initComponent();
		displayMode(1);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//
		add(txtFormula);
		add(txtInput);
		//
		txtFormula.setBounds(10, y1, this.getWidth() - 30, widthFml);
		txtFormula.setEditable(false);
		txtInput.setBounds(10, y1 + widthFml+15, this.getWidth() - 30, widthInput);
		txtInput.setEditable(false);
	}

	String[][] sStandard = { 
			{ "MC", "MR", "MS", "M+", "M-" },
			{ "<", "CE", "C", "+/-", "sqrt" }, 
			{ "7", "8", "9", "/", "%" },
			{ "4", "5", "6", "*", "1/x" }, 
			{ "1", "2", "3", "-", "=" },
			{ "0", ".", "+", "", "" }, };

	JButton[][] btnStandards = new JButton[6][5];
	JPanel panStandards = new JPanel();
	JPanel panScientific = new JPanel();
	int w = 40, h = 40, d = 10;
	int x = 0, y = 0;

	private void initComponent() {
		// panStandard here
		panStandards.setLayout(null);
		Insets isMargin = new Insets(1, 1, 1, 1);
		y = 0;
		for (int i = 0; i < 6; i++) {
			x = 0;
			for (int j = 0; j < 5; j++) {
				btnStandards[i][j] = new JButton(sStandard[i][j]);
				panStandards.add(btnStandards[i][j]);
				btnStandards[i][j].setBounds(x, y, w, h);
				btnStandards[i][j].setMargin(isMargin);
				x = x + w + d;
			}
			y = y + h + d;
		}
		btnStandards[4][4].setSize(w, h + d + h);
		btnStandards[5][0].setSize(w + d + w, h);
		btnStandards[5][1].setLocation((w + d) * 2, y - h - d);
		btnStandards[5][2].setLocation((w + d) * 3, y - h - d);
		btnStandards[5][3].setVisible(false);
		btnStandards[5][4].setVisible(false);

		this.add(panStandards);
		

		// Actionlistener
		ActionListener num = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton btnNum = (JButton) arg0.getSource();
				pressNumber(btnNum.getText());
			}
		};

		for (int i = 2; i <= 4; i++) {
			for (int j = 0; j < 3; j++) {
				btnStandards[i][j].addActionListener(num);
			}
		}
		btnStandards[5][0].addActionListener(num);
		btnStandards[5][1].addActionListener(num);

		ActionListener cal = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton btnCal = (JButton) arg0.getSource();
				
				if(!txtInput.getText().isEmpty()){
					if(!addCal){
						cResult = Double.parseDouble(txtInput.getText());
						setsCal(btnCal.getText());
						addCal = true;
						
						//Adding formula
						setFml(txtInput.getText() + " " +btnCal.getText());
					}
					else{
						
						if(!number.isEmpty()){
							calcualting();
							setsCal(btnCal.getText());
							//set formula
							setFml(getFml() +" " + number + " "+ btnCal.getText());
						}
						else{
							setsCal(btnCal.getText());
							//set formula
							setFml(getFml().substring(0, getFml().length()-1));
							setFml(getFml() + btnCal.getText());
						}
						
						
					}
				}
				
				number = "";
				firstEq = true;
				blAppend = false;
				setFmlTxtArea();
				
			}
		};

		for (int i = 1; i <= 4; i++) {
			btnStandards[i][3].addActionListener(cal);
		}
		btnStandards[5][2].addActionListener(cal);

		
		// btn Equal
		btnStandards[4][4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				calEqual();
			}
		});
	
		
		//btn M
		btnStandards[0][0].setEnabled(false);
		btnStandards[0][1].setEnabled(false);
		ActionListener btnM = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btnM = (JButton)e.getSource();
				//{ "MC", "MR", "MS", "M+", "M-" }
				if(btnM.getText().equals("MC")){
					MS = 0;
				}
				if (!txtInput.getText().isEmpty()){
					if (btnM.getText().equals("MR")){
						txtInput.setText(MS+"");
					}
					else if (btnM.getText().equals("MS")){
						MS = Double.parseDouble(txtInput.getText());
					}
					else if (btnM.getText().equals("M+")){
						MS += Double.parseDouble(txtInput.getText());
					}
					else if (btnM.getText().equals("M-")){
						MS -= Double.parseDouble(txtInput.getText());
					}
				}
				blAppend = false;
				chkMC(MS);
			}
		};
		for(int i = 0 ; i < 5 ; i++){
			btnStandards[0][i].addActionListener(btnM);
		}
		
		//btn "+/-"
		
		ActionListener someBtn = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btnF = (JButton)e.getSource();
				if(btnF.equals(btnStandards[1][3])){
					if(!txtInput.getText().isEmpty()){
						double x = Double.parseDouble(txtInput.getText())*(-1); // +/- button
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
				else if (btnF.equals(btnStandards[1][4])){
					if(!txtInput.getText().isEmpty()){
						double x = Math.sqrt(Double.parseDouble(txtInput.getText())); //sqrt button
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
				else if (btnF.equals(btnStandards[3][4])){
					if(!txtInput.getText().isEmpty()){
						double x = 1/Double.parseDouble(txtInput.getText()); // 1/x button
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
			}
		};
		btnStandards[1][3].addActionListener(someBtn);
		btnStandards[1][4].addActionListener(someBtn);
		btnStandards[3][4].addActionListener(someBtn);
		
	}

	// Input number var
	boolean blAppend = false;
	// Calculating var
	double cResult = 0;
	String sCal = "", number = "";
	boolean addCal = false;
	// Equal var
	double eq = 0;
	boolean firstEq = true;
	double eqTmp = 0;
	// btnM var
	double MS = 0;
	
	private void pressNumber(String snum) {
		number = snum;
		if (blAppend) {
			number = txtInput.getText() + number;
			txtInput.setText(number);
		} else {
			txtInput.setText(number);
			blAppend = true;
		}
		
		if(!addCal && firstEq){
			addingFml(snum);
			setFmlTxtArea();
		}
		
	}

	private void calcualting() {
		String sOper = getsCal();
		if (sOper.equals("+")) {
			cResult += Double.parseDouble(txtInput.getText());
			txtInput.setText(cResult + "");
		} else if (sOper.equals("-")) {
			cResult -= Double.parseDouble(txtInput.getText());
			txtInput.setText(cResult + "");
		} else if (sOper.equals("*")) {
			cResult *= Double.parseDouble(txtInput.getText());
			txtInput.setText(cResult + "");
		} else if (sOper.equals("/")) {
			cResult /= Double.parseDouble(txtInput.getText());
			txtInput.setText(cResult + "");
		}
		
		setCurrentResult(cResult);
	}

	private void calEqual(){
		String sOper = getsCal();
		String s = txtInput.getText();
		
		if(firstEq){
			
			eqTmp = Double.parseDouble(s);
			if (sOper.equals("+")) {
				cResult += Double.parseDouble(txtInput.getText());
				txtInput.setText(cResult + "");
			} else if (sOper.equals("-")) {
				cResult -= Double.parseDouble(txtInput.getText());
				txtInput.setText(cResult + "");
			} else if (sOper.equals("*")) {
				cResult *= Double.parseDouble(txtInput.getText());
				txtInput.setText(cResult + "");
			} else if (sOper.equals("/")) {
				cResult /= Double.parseDouble(txtInput.getText());
				txtInput.setText(cResult + "");
			}
			setCurrentResult(cResult);
			
			//Adding formula
			arrFml[arrFmlCount] = getFml() + " " + s + " = " + cResult;
			System.out.println(arrFml[arrFmlCount]);
			
			//
			firstEq = false;
			
		}
		else{
			if(!blAppend){
				if (sOper.equals("+")) {
					cResult += eqTmp;
					txtInput.setText(cResult + "");
				} else if (sOper.equals("-")) {
					cResult -= eqTmp;
					txtInput.setText(cResult + "");
				} else if (sOper.equals("*")) {
					cResult *= eqTmp;
					txtInput.setText(cResult + "");
				} else if (sOper.equals("/")) {
					cResult /= eqTmp;
					txtInput.setText(cResult + "");
				}
				setCurrentResult(cResult);
				
				//Adding formula
				arrFml[arrFmlCount] = getFml() + " " + sOper + " " + eqTmp + " = " + cResult;
				System.out.println(arrFml[arrFmlCount]);
				
			}
			else{
				cResult = eqTmp;
				String sTmpInput = txtInput.getText();
				if (sOper.equals("+")) {
					cResult += Double.parseDouble(txtInput.getText());
					txtInput.setText(cResult + "");
				} else if (sOper.equals("-")) {
					cResult -= Double.parseDouble(txtInput.getText());
					txtInput.setText(cResult + "");
				} else if (sOper.equals("*")) {
					cResult *= Double.parseDouble(txtInput.getText());
					txtInput.setText(cResult + "");
				} else if (sOper.equals("/")) {
					cResult /= Double.parseDouble(txtInput.getText());
					txtInput.setText(cResult + "");
				}
				setCurrentResult(cResult);
				
				//Adding formula
				arrFml[arrFmlCount] = eqTmp + " " + sOper + " " +sTmpInput + " = " + cResult;
				System.out.println(arrFml[arrFmlCount]);
				
			}
		}
		arrFmlCount+=1;
		setFml(cResult + "");
		txtFormula.setText("");
		
		addCal = false;
		blAppend = false;
	}
	
	private void setsCal(String s) {
		this.sCal = s;
	}

	private String getsCal() {
		return sCal;
	}

	private void setCurrentResult(double x) {
		this.cResult = x;
	}

	private double getCurrentResult() {
		return cResult;
	}

	private void chkMC(double M){
		if(M!=0){
			btnStandards[0][0].setEnabled(true);
			btnStandards[0][1].setEnabled(true);
		}
		else {
			btnStandards[0][0].setEnabled(false);
			btnStandards[0][1].setEnabled(false);
		}
	}
	
	String sFml = "";
	String arrFml[] = new String [10000];
	int arrFmlCount = 0;
	
	private void setFml(String sFml){
		this.sFml = sFml;
	}
	
	private String getFml(){
		return sFml;
	}
	
	private void addingFml(String s1){
		this.sFml += s1;
	}
	
	private void setFmlTxtArea(){
		String s1 = getFml();
		txtFormula.setText(s1);
	}
	// menuBar
	JMenuBar mnbMain = new JMenuBar();
	// menu
	JMenu mnView = new JMenu("View");
	JMenu mnEdit = new JMenu("Edit");
	JMenu mnHelp = new JMenu("Help");
	// menuItem
	// mniView
	JMenuItem mniExit = new JMenuItem("Exit");
	JMenuItem mniCStd = new JMenuItem("Standard");
	JMenuItem mniCSci = new JMenuItem("Scientific");
	JMenuItem mniCPro = new JMenuItem("Programmer");
	JMenuItem mniCSta = new JMenuItem("Statistics");
	// mniEdit
	JMenuItem mniCopy = new JMenuItem("Copy");
	JMenuItem mniPaste = new JMenuItem("Paste");
	// mniHelp
	JMenuItem mniAboutJava = new JMenuItem("About Java");
	private void initMenu() {
		// add menu to menubar
		mnbMain.add(mnView);
		mnbMain.add(mnEdit);
		mnbMain.add(mnHelp);
		// add menuItem to menu
		mnView.add(mniCSta);
		mnView.add(mniCSci);
		mnView.add(mniCPro);
		mnView.addSeparator();
		mnView.add(mniExit);
		mnEdit.add(mniCopy);
		mnEdit.add(mniPaste);
		mnHelp.add(mniAboutJava);
		
		// mnbMain.add(miExit);
		setJMenuBar(mnbMain);
		
		//set Icon - hot key
		mniCSta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				KeyEvent.ALT_MASK));
		mniCSci.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.ALT_MASK));
		mniCPro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				KeyEvent.ALT_MASK));
		mniExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				KeyEvent.CTRL_MASK));
		mniCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				KeyEvent.ALT_MASK));
		mniPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.ALT_MASK));
		mniAboutJava.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				KeyEvent.ALT_MASK));
		setIconJMenu(mniCopy, "./imgs/iconcopy.png", 30, 30);
		setIconJMenu(mniPaste, "./imgs/iconpaste.png", 30, 30);
		setIconJMenu(mniAboutJava, "./imgs/javalogo.png", 80, 50);
		//Action Listener
		ActionListener actMni = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem mniT = (JMenuItem)e.getSource();
				if(mniT.equals(mniCSta)){
					displayMode(1);
				}
				else if(mniT.equals(mniCSci)){
					displayMode(2);
				}
				else if(mniT.equals(mniCPro)){
					displayMode(3);
				}
				 if(mniT.equals(mniExit)){
					System.exit(0);
				}
				
			}
		};
		
		mniCSta.addActionListener(actMni);
		mniCSci.addActionListener(actMni);
		mniCPro.addActionListener(actMni);
		mniExit.addActionListener(actMni);
		
	}
	
	private void displayMode(int mode) {
		if (mode == 1) {
			setIconJMenu(mniCSta, "./imgs/dotblue.jpg", 20, 20);
			mniCSci.setIcon(null);
			mniCPro.setIcon(null);
			
			panScientific.setVisible(false);
			panStandards.setVisible(true);
			panStandards.setBounds(10, y0, x + w, y + h);
			this.setSize(5 * w + 4 * d + 30, 6 * h + 4 * d + y0+80);
		}
		else if (mode == 2) {
			mniCSta.setIcon(null);
			setIconJMenu(mniCSci, "./imgs/dotblue.jpg", 20, 20);
			mniCPro.setIcon(null);
			
			panStandards.setVisible(false);
			panScientific.setVisible(true);
			JOptionPane.showMessageDialog(null, "Working on...");
			//panStandards.setBounds(10, y0, x + w, y + h);
			//this.setSize(5 * w + 4 * d + 30, 6 * h + 4 * d + 180);
		}
		else if (mode == 3) {
			mniCSta.setIcon(null);
			mniCSci.setIcon(null);
			setIconJMenu(mniCPro, "./imgs/dotblue.jpg", 20, 20);
			//panStandards.setVisible(false);
			//panScientific.setVisible(true);
			JOptionPane.showMessageDialog(null, "Working on...");
			//panStandards.setBounds(10, y0, x + w, y + h);
			//this.setSize(5 * w + 4 * d + 30, 6 * h + 4 * d + 180);
		}
	}
	
	/**
	 * @param mni JMenuItem 
	 * @param filename url of image
	 * @param dx width of image
	 * @param dy height of image 
	 */
	private void setIconJMenu(JMenuItem mni, String filename, int dx, int dy){
		
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, image.SCALE_SMOOTH));
			mni.setIcon(icon);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(mni.getText() +" can't find image resource");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		cHW01_Calculator_T151487 f = new cHW01_Calculator_T151487();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}