import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import javafx.scene.Parent;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ItemEvent;

public class InputDefined {
	private List<Artifact> artifactList;
	private DefaultTableModel parent;

	public void setArtifactList(List<Artifact> artifactList) {
		this.artifactList = artifactList;
	}

	private JComboBox<Artifact> artifactCB;
	private JComboBox<ArtifactAttribute> attributeCB;
	private String result[] = null;
	private JTextArea resultTextArea;
	private JFrame frmAddNewAttribute;
	private JRadioButton isDefinedrdbtn;

	/**
	 * Launch the application.
	 */

	public void run() {
		frmAddNewAttribute.setVisible(true);
	}

	public JFrame getFrmAddNewAttribute() {
		return frmAddNewAttribute;
	}

	public boolean isShowing() {
		return frmAddNewAttribute.getContentPane().isShowing();
	}

	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputDefined window = new InputDefined(artifactList, parent, resultTextArea);
					window.frmAddNewAttribute.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputDefined(List<Artifact> artifactList, DefaultTableModel parentModel, JTextArea resultTextArea) {
		this.artifactList = artifactList;
		this.parent = parentModel;
		this.resultTextArea = resultTextArea;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddNewAttribute = new JFrame();
		frmAddNewAttribute.setTitle("Add New State");
		frmAddNewAttribute.setSize(400, 300);
		frmAddNewAttribute.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddNewAttribute.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (result != null) {
					parent.addRow(result);
					resultTextArea
							.setText(((resultTextArea.getText().equals("")) ? "" : (resultTextArea.getText() + " & "))
									+ result[0]);
				}
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}
		});

		JLabel lblArtifact = new JLabel("Artifact 1:");
		lblArtifact.setBounds(20, 20, 60, 30);
		frmAddNewAttribute.getContentPane().add(lblArtifact);

		artifactCB = new JComboBox(artifactList.toArray());
		artifactCB.setBounds(100, 20, 200, 30);
		artifactCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Artifact selectedArtifact = (Artifact) artifactCB.getSelectedItem();
					attributeCB.removeAllItems();
					for (ArtifactAttribute i : selectedArtifact.getListAttribute()) {
						attributeCB.addItem(i);
					}
				}
			}
		});
		frmAddNewAttribute.getContentPane().add(artifactCB);
		JLabel lblAttribute = new JLabel("Attribute :");
		lblAttribute.setBounds(20, 80, 60, 30);
		frmAddNewAttribute.getContentPane().add(lblAttribute);

		attributeCB = new JComboBox(artifactList.get(0).getListAttribute().toArray());
		attributeCB.setBounds(100, 80, 200, 30);
		frmAddNewAttribute.getContentPane().add(attributeCB);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(20, 180, 80, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmp[] = { ((isDefinedrdbtn.isSelected()) ? "" : "~") + artifactCB.getSelectedItem() + "At"
						+ attributeCB.getSelectedItem() };
				result = tmp;
				frmAddNewAttribute.dispose();
			}
		});
		frmAddNewAttribute.getContentPane().add(btnAdd);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(100, 180, 80, 30);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmAddNewAttribute.dispose();
			}
		});
		frmAddNewAttribute.getContentPane().add(btnCancel);

		isDefinedrdbtn = new JRadioButton("Is In this State");
		isDefinedrdbtn.setBounds(20, 140, 200, 30);
		frmAddNewAttribute.getContentPane().add(isDefinedrdbtn);

		// end
		frmAddNewAttribute.getContentPane().add(new JLabel());
	}
}
