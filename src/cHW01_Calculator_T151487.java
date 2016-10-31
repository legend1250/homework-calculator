
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	int y0 = 150; // panel start from this y0
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
		
		this.add(panStandards);
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
	
	int w = 40, h = 40, d = 10;
	int xSta = 0, ySta = 0;
	
	//Scientific
	String [][] sSci = {
			{ "" , "lnv" , "ln" , "(", ")" },
			{ "lnt", "sinh", "sin", "x^2" , "n!"},
			{ "dms", "cosh", "cos", "x^y", "x^1/y"},
			{ "PI", "tanh", "tan", "x^3", "x^1/3"},
			{ "F-E", "Exp", "Mod", "log", "10^x"}
			
	};
	JPanel panScientific = new JPanel();
	JButton[][] btnScientific = new JButton[5][5];
	int xSci = 0, ySci = 0;
	JPanel panAngleUnits = new JPanel();
	ButtonGroup btgAngle = new ButtonGroup();
	JRadioButton optDegrees = new JRadioButton("Degrees");
	JRadioButton optRadians = new JRadioButton("Radians");
	JRadioButton optGrads = new JRadioButton("Grads");
	
	//Progammer
	JPanel panPro = new JPanel();
	JButton[][] btnPro = new JButton[6][3];
	int xPro = 0, yPro = 0, yPBin = 60;
	JPanel pancvNum = new JPanel();
	ButtonGroup btgconvertNum = new ButtonGroup();
	JRadioButton optHex = new JRadioButton("Hex");
	JRadioButton optDec = new JRadioButton("Dec");
	JRadioButton optOct = new JRadioButton("Oct");
	JRadioButton optBin = new JRadioButton("Bin");
	JPanel panWord = new JPanel();
	ButtonGroup btgWord = new ButtonGroup();
	JRadioButton optQword = new JRadioButton("Qword");
	JRadioButton optDword = new JRadioButton("Dword");
	JRadioButton optWord = new JRadioButton("Word");
	JRadioButton optByte = new JRadioButton("Byte");
	String [][] sPro = {
			{"","Mod","A"},
			{"(",")","B"},
			{"RoL","RoR","C"},
			{"Or","Xor","D"},
			{"Lsh","Rsh","E"},
			{"Not","And","F"},
			
	};
	
	
	
	
	private void initComponent() {
		this.setLayout(null);
		// panStandard here
		panStandards.setLayout(null);
		Insets isMargin = new Insets(1, 1, 1, 1);
		ySta = 0;
		for (int i = 0; i < 6; i++) {
			xSta = 0;
			for (int j = 0; j < 5; j++) {
				btnStandards[i][j] = new JButton(sStandard[i][j]);
				panStandards.add(btnStandards[i][j]);
				btnStandards[i][j].setBounds(xSta, ySta, w, h);
				btnStandards[i][j].setMargin(isMargin);
				xSta = xSta + w + d;
			}
			ySta = ySta + h + d;
		}
		btnStandards[4][4].setSize(w, h + d + h);
		btnStandards[5][0].setSize(w + d + w, h);
		btnStandards[5][1].setLocation((w + d) * 2, ySta - h - d);
		btnStandards[5][2].setLocation((w + d) * 3, ySta - h - d);
		btnStandards[5][3].setVisible(false);
		btnStandards[5][4].setVisible(false);


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
		
		
		//Scientific Panel here
		panScientific.setLayout(null);
		ySci = 0;
		for (int i = 0; i < 5; i++) {
			xSci = 0;
			for (int j = 0; j < 5; j++) {
				btnScientific[i][j] = new JButton(sSci[i][j]);
				panScientific.add(btnScientific[i][j]);
				btnScientific[i][j].setBounds(xSci, ySci, w, h);
				btnScientific[i][j].setMargin(isMargin);
				xSci = xSci + w + d;
			}
			ySci = ySci + h + d;
		}
		this.add(panScientific);
		//Panel angle
		btgAngle.add(optDegrees);
		optDegrees.setSelected(true);
		btgAngle.add(optRadians);
		btgAngle.add(optGrads);
		panAngleUnits.add(optDegrees, BorderLayout.WEST);
		panAngleUnits.add(optRadians, BorderLayout.CENTER);
		panAngleUnits.add(optGrads, BorderLayout.EAST);
		this.add(panAngleUnits);
		
		
		//Programmer Panel here
		//Panel convert Dec Hex Bin Oct
		btgconvertNum.add(optDec);
		optDec.setSelected(true);
		btgconvertNum.add(optHex);
		btgconvertNum.add(optOct);
		btgconvertNum.add(optBin);
		pancvNum.add(optBin, BorderLayout.WEST);
		pancvNum.add(optOct, BorderLayout.WEST);
		pancvNum.add(optDec, BorderLayout.WEST);
		pancvNum.add(optHex, BorderLayout.WEST);
		this.add(pancvNum);
		//Panel word...
		btgWord.add(optQword);
		optQword.setSelected(true);
		btgWord.add(optDword);
		btgWord.add(optWord);
		btgWord.add(optByte);
		panWord.add(optQword, FlowLayout.LEFT);
		panWord.add(optDword, FlowLayout.LEFT);
		panWord.add(optWord, FlowLayout.LEFT);
		panWord.add(optByte, FlowLayout.LEFT);
		
		this.add(panWord);
		
		panPro.setLayout(null);
		for (int i = 0; i < 6; i++) {
			xPro = 0;
			for (int j = 0; j < 3; j++) {
				btnPro[i][j] = new JButton(sPro[i][j]);
				panPro.add(btnPro[i][j]);
				btnPro[i][j].setBounds(xPro, yPro, w, h);
				btnPro[i][j].setMargin(isMargin);
				xPro = xPro + w + d;
			}
			yPro = yPro + h + d;
		}
		this.add(panPro);
		//add radio button convert num
		ActionListener optConvertNum = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chkConvertNum();
			}
		};
		optBin.addActionListener(optConvertNum);
		optOct.addActionListener(optConvertNum);
		optDec.addActionListener(optConvertNum);
		optHex.addActionListener(optConvertNum);
		
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
	
	private void chkConvertNum(){
		if(optBin.isSelected()){
			setBtnDis(1);
		}
		else if(optOct.isSelected()){
			setBtnDis(2);
		}
		else if(optDec.isSelected()){
			setBtnDis(3);
		}
		else if(optHex.isSelected()){
			setBtnDis(4);
		}

	}
	
	private void setBtnDis(int stage){
		//set Enable button first
		for(int i = 0 ; i < 6 ; i++){
			for(int j = 0 ; j < 3 ; j++){
				btnPro[i][j].setEnabled(true);
			}
		}
		for(int i = 2 ; i < 5 ; i++){
			for(int j = 0 ; j < 3 ; j++){
				btnStandards[i][j].setEnabled(true);
			}
		}
		
		//Bin stage
		if(stage == 1){
			for(int i = 0 ; i < 6 ; i++){
				btnPro[i][2].setEnabled(false);
			}
			for(int i = 2 ; i < 5 ; i++){
				for(int j = 0 ; j < 3 ; j++){
					btnStandards[i][j].setEnabled(false);
				}
			}
			btnStandards[4][0].setEnabled(true);
			btnStandards[5][0].setEnabled(true);
		}
		//Oct stage
		else if(stage == 2){
			for(int i = 0 ; i < 6 ; i++){
				btnPro[i][2].setEnabled(false);
			}
			btnStandards[2][1].setEnabled(false);
			btnStandards[2][2].setEnabled(false);
		}
		
		//Dec stage
		else if(stage == 3){
			for(int i = 0 ; i < 6 ; i++){
				btnPro[i][2].setEnabled(false);
			}
		}
		
		else if(stage == 4){
			//do not disable any button
		}
		
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
		//Standard mode
		if (mode == 1) {
			setIconJMenu(mniCSta, "./imgs/dotblue.jpg", 20, 20);
			mniCSci.setIcon(null);
			mniCPro.setIcon(null);
			
			//turn on panel
			panStandards.setVisible(true);
			panStandards.setBounds(10, y0, xSta + w, ySta + h);
			
			panScientific.setVisible(false);
			panAngleUnits.setVisible(false);
			panWord.setVisible(false);
			pancvNum.setVisible(false);
			panPro.setVisible(false);
			
			//resize the frame
			this.setSize(5 * w + 4 * d + 30, 6 * h + 4 * d + y0+80);
		}
		//Scientific mode
		else if (mode == 2) {
			mniCSta.setIcon(null);
			setIconJMenu(mniCSci, "./imgs/dotblue.jpg", 20, 20);
			mniCPro.setIcon(null);
			
			//turn on panel
			panStandards.setVisible(true);
			panStandards.setBounds(10 + xSci, y0, xSta + w, ySta + h);
			panAngleUnits.setVisible(true);
			panAngleUnits.setBounds(10, y0, xSci, h+d);
			panScientific.setVisible(true);
			panScientific.setBounds(10, y0 + h + d , xSci, ySci + h);
			
			//disable some button
			btnStandards[2][4].setEnabled(false);
			panWord.setVisible(false);
			pancvNum.setVisible(false);
			panPro.setVisible(false);
			//resize the frame
			this.setSize(5 * w + 4 * d + 30 + xSci, 6 * h + 4 * d + y0+80);
		}
		//Progammer mode
		else if (mode == 3) {
			mniCSta.setIcon(null);
			mniCSci.setIcon(null);
			setIconJMenu(mniCPro, "./imgs/dotblue.jpg", 20, 20);
			
			//addPanel
			pancvNum.setVisible(true);
			pancvNum.setBounds(10, y0 + yPBin, 80, 150);
			panWord.setVisible(true);
			panWord.setBounds(10, y0 + yPBin+150, 80, 150);
			
			panPro.setVisible(true);
			panPro.setBounds(10 + 100,  y0+yPBin, xPro, yPro+h);
			panStandards.setVisible(true);
			panStandards.setBounds(110 + xPro , y0 + yPBin, xSta + w, ySta + h);
			
			panAngleUnits.setVisible(false);
			panScientific.setVisible(false);
			
			//disable some button
			for(int i = 1 ; i < 4 ; i++){
				btnStandards[i][4].setEnabled(false);
			}
			btnStandards[5][1].setEnabled(false);
			
			//resize the frame
			this.setSize(5 * w + 4 * d + 30 + xSci , 6 * h + 4 * d + y0+80 + yPBin);
		}
		
		chkConvertNum();
		txtFormula.setBounds(10, y1, this.getWidth() - 30, widthFml);
		txtFormula.setEditable(false);
		txtInput.setBounds(10, y1 + widthFml+15, this.getWidth() - 30, widthInput);
		txtInput.setEditable(false);
	}
	
	/**
	 * set Icon for JMenuItem
	 * @param which JMenuItem want to add
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