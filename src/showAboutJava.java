import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.DropMode;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Cursor;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.CardLayout;

public class showAboutJava extends JDialog {

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public showAboutJava() {
		setBounds(100, 100, 513, 524);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblIconJava = new JLabel("");
			lblIconJava.setIcon(new ImageIcon(".\\imgs\\javalogo.png"));
			lblIconJava.setBounds(10, 11, 476, 212);
			contentPanel.add(lblIconJava);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 234, 477, 207);
			contentPanel.add(scrollPane);
			{
				JEditorPane txtAboutJava = new JEditorPane();
				txtAboutJava.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				txtAboutJava.setAlignmentY(Component.TOP_ALIGNMENT);
				txtAboutJava.setAlignmentX(Component.RIGHT_ALIGNMENT);
				txtAboutJava.setFocusTraversalKeysEnabled(false);
				txtAboutJava.setFocusCycleRoot(false);
				txtAboutJava.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtAboutJava.setEditable(false);
				String strContent = "";
				try {
					Scanner reader = new Scanner(new File("./text/AboutJava.txt"));
					while(reader.hasNextLine()){
						strContent += reader.nextLine() + "\n";
					}
					reader.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "ERROR! AboutJava.txt not found");
				}
				txtAboutJava.setText(strContent);
				scrollPane.setViewportView(txtAboutJava);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(10, 452, 477, 23);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				
				JLabel lblAuthor = new JLabel("Author: Vinh - T151487");
				lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
				lblAuthor.setFont(new Font("Arial", Font.BOLD, 12));
				lblAuthor.setBounds(0, 3, 154, 23);
				lblAuthor.setVerticalAlignment(SwingConstants.TOP);
				buttonPane.add(lblAuthor);
			}
			JButton okButton = new JButton("OK");
			okButton.setBounds(418, 0, 59, 23);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
	}
}
