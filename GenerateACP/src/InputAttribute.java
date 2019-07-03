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

public class InputAttribute {
	private List<String> compareTypeList = new ArrayList<String>();
	private List<String> operatorList = new ArrayList<String>();
	private List<Artifact> artifactList;
	private DefaultTableModel parent;
	private JTextArea resultTextArea;
	private List<Expression> conExprList;
	private Expression resultExpr;

	public void setArtifactList(List<Artifact> artifactList) {
		this.artifactList = artifactList;
	}

	private JComboBox<String> compareTypeCB;
	private JComboBox<String> operatorCB;
	private JComboBox<Artifact> artifactCB1;
	private JComboBox<Artifact> artifactCB2;
	private JComboBox<ArtifactAttribute> attributeCB1;
	private JComboBox<ArtifactAttribute> attributeCB2;
	private JTextField valueTextField;
	private String result[] = null;
	private JFrame frmAddNewAttribute;

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
					InputAttribute window = new InputAttribute(artifactList, parent, resultTextArea);
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
	public InputAttribute(List<Artifact> artifactList, DefaultTableModel parentModel, JTextArea resultTextArea) {
		this.artifactList = artifactList;
		this.parent = parentModel;
		this.resultTextArea = resultTextArea;
		initialize();
	}

	public InputAttribute(List<Artifact> artifactList, DefaultTableModel parentModel, JTextArea resultTextArea,
			List<Expression> condExprList) {
		this.artifactList = artifactList;
		this.parent = parentModel;
		this.resultTextArea = resultTextArea;
		this.conExprList = condExprList;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		compareTypeList.add("Compare to another Attribute");
		compareTypeList.add("Compare to a value");
		operatorList.add("BiggerThan");
		operatorList.add("EqualOrBiggerThan");
		operatorList.add("SmallerThan");
		operatorList.add("EqualOrSmallerThan");
		operatorList.add("Equal");
		frmAddNewAttribute = new JFrame();
		frmAddNewAttribute.setTitle("Add New Attribute");
		frmAddNewAttribute.setBounds(100, 100, 475, 341);
		frmAddNewAttribute.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmAddNewAttribute.getContentPane().setLayout(springLayout);
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
									+ result[0] + result[1] + result[2]);
					System.out.println(resultExpr);
					if (conExprList != null) {
						conExprList.add(resultExpr);
					}
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
		frmAddNewAttribute.getContentPane().add(lblArtifact);

		artifactCB1 = new JComboBox(artifactList.toArray());
		artifactCB1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Artifact selectedArtifact = (Artifact) artifactCB1.getSelectedItem();
					attributeCB1.removeAllItems();
					for (ArtifactAttribute i : selectedArtifact.getListAttribute()) {
						attributeCB1.addItem(i);
					}
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, artifactCB1, -3, SpringLayout.NORTH, lblArtifact);
		frmAddNewAttribute.getContentPane().add(artifactCB1);

		JLabel lblAttribute = new JLabel("Attribute 1:");
		springLayout.putConstraint(SpringLayout.WEST, lblArtifact, 0, SpringLayout.WEST, lblAttribute);
		springLayout.putConstraint(SpringLayout.WEST, lblAttribute, 10, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(lblAttribute);

		attributeCB1 = new JComboBox(artifactList.get(0).getListAttribute().toArray());
		springLayout.putConstraint(SpringLayout.WEST, artifactCB1, 0, SpringLayout.WEST, attributeCB1);
		springLayout.putConstraint(SpringLayout.EAST, artifactCB1, 0, SpringLayout.EAST, attributeCB1);
		springLayout.putConstraint(SpringLayout.WEST, attributeCB1, 10, SpringLayout.EAST, lblAttribute);
		springLayout.putConstraint(SpringLayout.SOUTH, attributeCB1, 0, SpringLayout.SOUTH, lblAttribute);
		frmAddNewAttribute.getContentPane().add(attributeCB1);

		artifactCB2 = new JComboBox(artifactList.toArray());
		artifactCB2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Artifact selectedArtifact = (Artifact) artifactCB2.getSelectedItem();
					attributeCB2.removeAllItems();
					for (ArtifactAttribute i : selectedArtifact.getListAttribute()) {
						attributeCB2.addItem(i);
					}
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, artifactCB2, 70, SpringLayout.NORTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblArtifact, 3, SpringLayout.NORTH, artifactCB2);
		springLayout.putConstraint(SpringLayout.EAST, artifactCB2, -25, SpringLayout.EAST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(artifactCB2);

		JLabel lblAttribute_1 = new JLabel("Attribute 2:");
		springLayout.putConstraint(SpringLayout.EAST, attributeCB1, -74, SpringLayout.WEST, lblAttribute_1);
		springLayout.putConstraint(SpringLayout.NORTH, lblAttribute, 0, SpringLayout.NORTH, lblAttribute_1);
		springLayout.putConstraint(SpringLayout.WEST, lblAttribute_1, 256, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(lblAttribute_1);

		compareTypeCB = new JComboBox(compareTypeList.toArray());
		compareTypeCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ("Compare to a value".equals(compareTypeCB.getSelectedItem())) {
					valueTextField.enable();
					valueTextField.setVisible(true);
					attributeCB2.disable();
					artifactCB2.disable();
					attributeCB2.setVisible(false);
					artifactCB2.setVisible(false);
				} else {
					valueTextField.disable();
					valueTextField.setVisible(false);
					attributeCB2.enable();
					artifactCB2.enable();
					attributeCB2.setVisible(true);
					artifactCB2.setVisible(true);
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, compareTypeCB, 21, SpringLayout.NORTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, compareTypeCB, -25, SpringLayout.EAST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(compareTypeCB);

		JLabel lblArtifact_2 = new JLabel("Artifact 2:");
		springLayout.putConstraint(SpringLayout.NORTH, lblArtifact_2, 76, SpringLayout.NORTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblArtifact_2, 256, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, artifactCB2, 23, SpringLayout.EAST, lblArtifact_2);
		springLayout.putConstraint(SpringLayout.NORTH, lblAttribute_1, 39, SpringLayout.SOUTH, lblArtifact_2);
		frmAddNewAttribute.getContentPane().add(lblArtifact_2);

		attributeCB2 = new JComboBox(artifactList.get(0).getListAttribute().toArray());
		springLayout.putConstraint(SpringLayout.NORTH, attributeCB2, 33, SpringLayout.SOUTH, artifactCB2);
		springLayout.putConstraint(SpringLayout.WEST, attributeCB2, 0, SpringLayout.WEST, artifactCB2);
		springLayout.putConstraint(SpringLayout.EAST, attributeCB2, 0, SpringLayout.EAST, artifactCB2);
		frmAddNewAttribute.getContentPane().add(attributeCB2);

		JLabel lblValue = new JLabel("Value:");
		springLayout.putConstraint(SpringLayout.NORTH, lblValue, 38, SpringLayout.SOUTH, lblAttribute_1);
		springLayout.putConstraint(SpringLayout.WEST, lblValue, 256, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(lblValue);

		valueTextField = new JTextField();
		valueTextField.disable();
		valueTextField.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, valueTextField, 32, SpringLayout.SOUTH, attributeCB2);
		springLayout.putConstraint(SpringLayout.WEST, valueTextField, 0, SpringLayout.WEST, artifactCB2);
		springLayout.putConstraint(SpringLayout.EAST, valueTextField, 0, SpringLayout.EAST, artifactCB2);
		frmAddNewAttribute.getContentPane().add(valueTextField);

		JLabel lblCompareType = new JLabel("Compare Type");
		springLayout.putConstraint(SpringLayout.WEST, compareTypeCB, 14, SpringLayout.EAST, lblCompareType);
		springLayout.putConstraint(SpringLayout.NORTH, lblCompareType, 24, SpringLayout.NORTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblCompareType, 0, SpringLayout.WEST, lblArtifact);
		frmAddNewAttribute.getContentPane().add(lblCompareType);

		JLabel lblOperator = new JLabel("Operator:");
		springLayout.putConstraint(SpringLayout.NORTH, lblOperator, 0, SpringLayout.NORTH, lblValue);
		springLayout.putConstraint(SpringLayout.WEST, lblOperator, 11, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(lblOperator);

		operatorCB = new JComboBox(operatorList.toArray());
		springLayout.putConstraint(SpringLayout.NORTH, operatorCB, 0, SpringLayout.NORTH, valueTextField);
		springLayout.putConstraint(SpringLayout.WEST, operatorCB, 17, SpringLayout.EAST, lblOperator);
		springLayout.putConstraint(SpringLayout.EAST, operatorCB, -74, SpringLayout.WEST, lblValue);
		frmAddNewAttribute.getContentPane().add(operatorCB);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmp[] = { artifactCB1.getSelectedItem() + "At" + attributeCB1.getSelectedItem(),
						operatorCB.getSelectedItem().toString(),
						("Compare to a value".equals(compareTypeCB.getSelectedItem())) ? valueTextField.getText()
								: (artifactCB2.getSelectedItem() + "At" + attributeCB2.getSelectedItem()) };
				result = tmp;
				if ("Compare to a value".equals(compareTypeCB.getSelectedItem())) {
					resultExpr = new Expression(artifactCB1.getSelectedItem().toString(),
							attributeCB1.getSelectedItem().toString(), operatorCB.getSelectedItem().toString(), null,
							null, valueTextField.getText());
				} else {
					resultExpr = new Expression(artifactCB1.getSelectedItem().toString(),
							attributeCB1.getSelectedItem().toString(), operatorCB.getSelectedItem().toString(),
							artifactCB2.getSelectedItem().toString(), attributeCB2.getSelectedItem().toString(), null);
				}
				frmAddNewAttribute.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnAdd, 10, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -26, SpringLayout.SOUTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAdd, 90, SpringLayout.WEST,
				frmAddNewAttribute.getContentPane());
		frmAddNewAttribute.getContentPane().add(btnAdd);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmAddNewAttribute.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 42, SpringLayout.EAST, btnAdd);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -26, SpringLayout.SOUTH,
				frmAddNewAttribute.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, 122, SpringLayout.EAST, btnAdd);
		frmAddNewAttribute.getContentPane().add(btnCancel);
	}
}
