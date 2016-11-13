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
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(".\\imgs\\javalogo.png"));
			lblNewLabel.setBounds(10, 11, 476, 212);
			contentPanel.add(lblNewLabel);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 234, 477, 207);
			contentPanel.add(scrollPane);
			{
				JEditorPane dtrpnJavaIsA = new JEditorPane();
				dtrpnJavaIsA.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				dtrpnJavaIsA.setAlignmentY(Component.BOTTOM_ALIGNMENT);
				dtrpnJavaIsA.setAlignmentX(Component.RIGHT_ALIGNMENT);
				dtrpnJavaIsA.setFocusTraversalKeysEnabled(false);
				dtrpnJavaIsA.setFocusCycleRoot(false);
				dtrpnJavaIsA.setFont(new Font("Tahoma", Font.PLAIN, 12));
				dtrpnJavaIsA.setEditable(false);
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
				dtrpnJavaIsA.setText(strContent);
				scrollPane.setViewportView(dtrpnJavaIsA);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
