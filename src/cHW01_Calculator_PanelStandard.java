/*import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class cHW01_Calculator_PanelStandard extends JPanel{

	String[][] sStandard = { 
			{ "MC", "MR", "MS", "M+", "M-" },
			{ "<", "CE", "C", "+/-", "sqrt" }, 
			{ "7", "8", "9", "/", "%" },
			{ "4", "5", "6", "*", "1/x" }, 
			{ "1", "2", "3", "-", "=" },
			{ "0", ".", "+", "", "" }, };

	JButton[][] btnStandards = new JButton[6][5];
	int w = 40, h = 40, d = 10;
	int x = 0, y = 0;
	
	public cHW01_Calculator_PanelStandard(){
		setLayout(null);
		
		Insets isMargin = new Insets(1, 1, 1, 1);
		y = 0;
		for (int i = 0; i < 6; i++) {
			x = 0;
			for (int j = 0; j < 5; j++) {
				btnStandards[i][j] = new JButton(sStandard[i][j]);
				this.add(btnStandards[i][j]);
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
				if (addCal && !number.isEmpty()) {
					calcualting();
					setsCal(btnCal.getText());
					
					//Adding formula
					addingFml(btnCal.getText());
				} else {
					if (!txtInput.getText().isEmpty()) {
						cResult = Double.parseDouble(txtInput.getText());
					}
					setsCal(btnCal.getText());
					addCal = true;
					
					//Adding formula
					if(!number.isEmpty()){
						addingFml(btnCal.getText());
					}
					else{
						String sTmp = getFml();
						setFml(sTmp.substring(0, sTmp.length()-1));
						addingFml(btnCal.getText());
					}
				} 
				
				firstEq = false;
				blAppend = false;
				number = "";
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
				// TODO Auto-generated method stub
				sEq = getsCal();
				if(firstEq){
					if(!blAppend){
						if (sEq.equals("+")) {
							cResult += eqTmp;
							txtInput.setText(cResult + "");
						} else if (sEq.equals("-")) {
							cResult -= eqTmp;
							txtInput.setText(cResult + "");
						} else if (sEq.equals("*")) {
							cResult *= eqTmp;
							txtInput.setText(cResult + "");
						} else if (sEq.equals("/")) {
							cResult /= eqTmp;
							txtInput.setText(cResult + "");
						}
						setCurrentResult(cResult);
						
						//Adding formula
						arrFml[arrFmlCount] = getFml() + sEq + eqTmp + " = " + cResult;
						System.out.println(arrFml[arrFmlCount]);
						arrFml[arrFmlCount] = cResult+"";
						setFml(arrFml[arrFmlCount]);
					}
					else{
						cResult = eqTmp;
						String sTmpInput = txtInput.getText();
						if (sEq.equals("+")) {
							cResult += Double.parseDouble(txtInput.getText());
							txtInput.setText(cResult + "");
						} else if (sEq.equals("-")) {
							cResult -= Double.parseDouble(txtInput.getText());
							txtInput.setText(cResult + "");
						} else if (sEq.equals("*")) {
							cResult *= Double.parseDouble(txtInput.getText());
							txtInput.setText(cResult + "");
						} else if (sEq.equals("/")) {
							cResult /= Double.parseDouble(txtInput.getText());
							txtInput.setText(cResult + "");
						}
						setCurrentResult(cResult);
						
						//Adding formula
						arrFml[arrFmlCount] = eqTmp +  sEq + sTmpInput + " = " + cResult;
						System.out.println(arrFml[arrFmlCount]);
						arrFml[arrFmlCount] = cResult+"";
						setFml(arrFml[arrFmlCount]);
					}
				}
				else{
					eqTmp = Double.parseDouble(txtInput.getText());
					if (sEq.equals("+")) {
						cResult += Double.parseDouble(txtInput.getText());
						txtInput.setText(cResult + "");
					} else if (sEq.equals("-")) {
						cResult -= Double.parseDouble(txtInput.getText());
						txtInput.setText(cResult + "");
					} else if (sEq.equals("*")) {
						cResult *= Double.parseDouble(txtInput.getText());
						txtInput.setText(cResult + "");
					} else if (sEq.equals("/")) {
						cResult /= Double.parseDouble(txtInput.getText());
						txtInput.setText(cResult + "");
					}
					setCurrentResult(cResult);
					
					//Adding formula
					arrFml[arrFmlCount] = getFml() + " = " + cResult;
					System.out.println(arrFml[arrFmlCount]);
					arrFml[arrFmlCount] = cResult+"";
					setFml(arrFml[arrFmlCount]);
					
					//
					firstEq = true;
				}
				//System.out.println(blAppend);
				
				arrFmlCount+=1;
				
				setFmlTxtArea();
				addCal = false;
				blAppend = false;
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
		
		ActionListener fBtn = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton btnF = (JButton)e.getSource();
				if(btnF.equals(btnStandards[1][3])){
					if(!txtInput.getText().isEmpty()){
						double x = Double.parseDouble(txtInput.getText())*(-1);
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
				else if (btnF.equals(btnStandards[1][4])){
					if(!txtInput.getText().isEmpty()){
						double x = Math.sqrt(Double.parseDouble(txtInput.getText()));
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
				else if (btnF.equals(btnStandards[3][4])){
					if(!txtInput.getText().isEmpty()){
						double x = 1/Double.parseDouble(txtInput.getText());
						setCurrentResult(x);
						txtInput.setText(getCurrentResult()+"");
					}
				}
			}
		};
		btnStandards[1][3].addActionListener(fBtn);
		btnStandards[1][4].addActionListener(fBtn);
		btnStandards[3][4].addActionListener(fBtn);
		
	}
	
	// Input number var
		boolean blAppend = false;
		// Calculating var
		double cResult = 0;
		String sCal = "", number = "";
		boolean addCal = false;
		// Equal var
		double eq = 0;
		String sEq = "";
		boolean firstEq = false;
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
			addingFml(snum);
			setFmlTxtArea();
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
}
*/