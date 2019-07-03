import gov.nih.nci.ncicb.xmiinout.handler.XmiException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.jdesktop.swingx.prompt.PromptSupport;

import orbital.logic.imp.Formula;
import orbital.logic.sign.ParseException;

class MainScreen extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel mainScreen;
	private JPanel condition_consequence;
	private JPanel artifact;
	private JPanel algorithm;
	private List<Artifact> artifactList = new ArrayList<Artifact>();
	private JPanel topPanel;
	private InputAttribute inputScreen1;
	private InputState inputSceen2;
	private InputDefined inputScreen3;
	// screen 1
	private JTextField textFilePath;
	private List<int[]> pathList;
	private List<BusinessTask> BTList;
	private JComboBox<BusinessTask> businessTaskCB;
	private JLabel lblBusinessTask;
	private JLabel lblPreCondition;
	private JLabel lblPostCondition;
	private JLabel lblService;
	private JTextArea textPreCondition;
	private JTextArea textPostCondition;
	private JTextField textService;
	private JButton btnSubmit;
	private String tableHeader1[] = { "Attribute", "Operator", "Value" };
	private String tableHeader2[] = { "State" };
	private String tableHeader3[] = { "Is Defined" };
	private DefaultTableModel preAttrModel;
	private JTable preAttrTable;
	private DefaultTableModel postAttrModel;
	private JTable postAttrTable;
	private DefaultTableModel preStateModel;
	private JTable preStateTable;
	private DefaultTableModel postStateModel;
	private JTable postStateTable;
	private DefaultTableModel preDefinedModel;
	private JTable preDefinedTable;
	private DefaultTableModel postDefinedModel;
	private JTable postDefinedTable;
	private JButton preStateAddBtn;
	private JButton preAttrAddBtn;
	private JButton postStateAddBtn;
	private JButton postAttrAddBtn;
	private JButton preStateDelBtn;
	private JButton preAttrDelBtn;
	private JButton postStateDelBtn;
	private JButton postAttrDelBtn;
	private JButton preDefinedAddBtn;
	private JButton preDefinedDelBtn;
	private JButton postDefinedAddBtn;
	private JButton postDefinedDelBtn;
	// screen 2
	private DefaultTableModel condAttrModel;
	private JTable condAttrTable;
	private DefaultTableModel consAttrModel;
	private JTable consAttrTable;
	private JButton condAttrAddBtn;
	private JButton condAttrDelBtn;
	private JButton consAttrAddBtn;
	private JButton consAttrDelBtn;
	private JLabel lblCond;
	private JLabel lblCons;
	private JTextArea textCond;
	private JTextArea textCons;
	private List<Expression> condExprList = new ArrayList<Expression>();
	private List<Expression> consExprList = new ArrayList<Expression>();

	// screen 3
	private JButton classDiagramBtn;
	private JTextField classDiagramPath;
	private DefaultTableModel artifactsModel;
	private JTable artifactsTable;
	private JComboBox<Artifact> artifactCBSc3;
	private JButton readStateBtn;

	// screen 4

	void setPanelEnabled(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelEnabled((JPanel) components[i], isEnabled);
			}

			components[i].setEnabled(isEnabled);
		}
	}

	public MainScreen() {

		setTitle("NCKH");
		setSize(1024, 768);
		setBackground(Color.gray);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		// Create the tab pages
		createMainScreen();
		createConScreen();
		createArtifactScreen();
		createAlgorithmScreen();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Main", mainScreen);
		tabbedPane.addTab("Condition & Consequence", condition_consequence);
		tabbedPane.addTab("Artifact", artifact);
		tabbedPane.addTab("Algorithm", algorithm);
		topPanel.add(tabbedPane, BorderLayout.CENTER);
	}

	public void createAlgorithmScreen() {
		algorithm = new JPanel();
		algorithm.setLayout(null);
		JButton btnElimination = new JButton("Run Elimination");
		btnElimination.setBounds(50, 50, 200, 50);
		btnElimination.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int[] label : pathList) {
					BusinessTask[] a = new BusinessTask[BTList.size()];
					try {
						Algorithm.elimination(condExprList, consExprList, label, BTList.toArray(a));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		algorithm.add(btnElimination);
	}

	public void createMainScreen() {

		mainScreen = new JPanel();
		pathList = new ArrayList<int[]>();
		BTList = new ArrayList<BusinessTask>();
		mainScreen.setLayout(null);

		JButton btnReadFile = new JButton("Read BPMN");
		btnReadFile.setBounds(20, 30, 100, 30);
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser bpmnFile = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".bpmn", "bpmn");
				bpmnFile.setFileFilter(fileFilter);
				int returnVal = bpmnFile.showOpenDialog(mainScreen);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = bpmnFile.getSelectedFile();
					textFilePath.setText(file.getName());
					BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
					Collection<Task> tempListTask = modelInstance.getModelElementsByType(Task.class);
					// Remove All element in businessTaskCB and Table
					businessTaskCB.removeAllItems();

					try {
						for (Task i : tempListTask) {

							BusinessTask n = new BusinessTask();
							n.name = i.getName();
							BTList.add(n);
							businessTaskCB.addItem(n);
						}
						pathList = BusinessProcessRepository.getListPath(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mainScreen.validate();
			}
		});
		mainScreen.add(btnReadFile);

		textFilePath = new JTextField();
		textFilePath.setBounds(150, 30, 320, 30);
		mainScreen.add(textFilePath);
		textFilePath.setColumns(10);

		lblBusinessTask = new JLabel("Business Task:");
		lblBusinessTask.setBounds(500, 30, 150, 30);
		mainScreen.add(lblBusinessTask);

		businessTaskCB = new JComboBox<BusinessTask>();
		businessTaskCB.setBounds(600, 30, 350, 30);
		mainScreen.add(businessTaskCB);

		JPanel preGroup = new JPanel();
		preGroup.setLayout(null);
		preGroup.setBorder(new TitledBorder("Pre Condition"));
		preGroup.setBounds(10, 65, 470, 460);
		mainScreen.add(preGroup);

		JPanel postGroup = new JPanel();
		postGroup.setLayout(null);
		postGroup.setBorder(new TitledBorder("Post Condition"));
		postGroup.setBounds(490, 65, 470, 460);
		mainScreen.add(postGroup);

		// Attribute Table
		preAttrModel = new DefaultTableModel(tableHeader1, 0);
		preAttrTable = new JTable(preAttrModel);
		JScrollPane scrollpane1 = new JScrollPane(preAttrTable);
		scrollpane1.setBounds(10, 15, 450, 100);
		preGroup.add(scrollpane1);

		preAttrAddBtn = new JButton("Add");
		preAttrAddBtn.setBounds(10, 125, 100, 30);

		preAttrAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new Attr Precond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen1 = new InputAttribute(artifactList, preAttrModel, textPreCondition);
				inputScreen1.run();
			}
		});
		preGroup.add(preAttrAddBtn);

		preAttrDelBtn = new JButton("Delete");
		preAttrDelBtn.setBounds(120, 125, 100, 30);
		preAttrDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (preAttrTable.getSelectedRow() >= 0) {
					String selected = ((String) preAttrModel.getValueAt(preAttrTable.getSelectedRow(), 0)
							+ (String) preAttrModel.getValueAt(preAttrTable.getSelectedRow(), 1)
							+ (String) preAttrModel.getValueAt(preAttrTable.getSelectedRow(), 2));
					if (textPreCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPreCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPreCondition.setText(textPreCondition.getText().replaceFirst(selected, ""));
					preAttrModel.removeRow(preAttrTable.getSelectedRow());

				}
			}
		});
		preGroup.add(preAttrDelBtn);

		postAttrModel = new DefaultTableModel(tableHeader1, 0);
		postAttrTable = new JTable(postAttrModel);
		JScrollPane scrollpane2 = new JScrollPane(postAttrTable);
		scrollpane2.setBounds(10, 15, 450, 100);
		postGroup.add(scrollpane2);

		postAttrAddBtn = new JButton("Add");
		postAttrAddBtn.setBounds(10, 125, 100, 30);
		postAttrAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new Attr Postcond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen1 = new InputAttribute(artifactList, postAttrModel, textPostCondition);
				inputScreen1.run();
			}
		});
		postGroup.add(postAttrAddBtn);

		postAttrDelBtn = new JButton("Delete");
		postAttrDelBtn.setBounds(120, 125, 100, 30);
		postAttrDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (postAttrTable.getSelectedRow() >= 0) {
					String selected = ((String) postAttrModel.getValueAt(postAttrTable.getSelectedRow(), 0)
							+ (String) postAttrModel.getValueAt(postAttrTable.getSelectedRow(), 1)
							+ (String) postAttrModel.getValueAt(postAttrTable.getSelectedRow(), 2));
					if (textPostCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPostCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPostCondition.setText(textPostCondition.getText().replaceFirst(selected, ""));
					postAttrModel.removeRow(postAttrTable.getSelectedRow());
				}
			}
		});
		postGroup.add(postAttrDelBtn);

		// State Table
		preStateModel = new DefaultTableModel(tableHeader2, 0);
		preStateTable = new JTable(preStateModel);
		JScrollPane scrollpane3 = new JScrollPane(preStateTable);
		scrollpane3.setBounds(10, 185, 225, 100);
		preGroup.add(scrollpane3);

		preStateAddBtn = new JButton("Add");
		preStateAddBtn.setBounds(10, 295, 100, 30);
		preStateAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new State Precond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputSceen2 = new InputState(artifactList, preStateModel, textPreCondition);
				inputSceen2.run();
			}
		});
		preGroup.add(preStateAddBtn);

		preStateDelBtn = new JButton("Delete");
		preStateDelBtn.setBounds(120, 295, 100, 30);
		preStateDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (preStateTable.getSelectedRow() >= 0) {
					String selected = (String) preStateModel.getValueAt(preStateTable.getSelectedRow(), 0);
					if (textPreCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPreCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPreCondition.setText(textPreCondition.getText().replaceFirst(selected, ""));
					preStateModel.removeRow(preStateTable.getSelectedRow());
				}
			}
		});
		preGroup.add(preStateDelBtn);

		postStateModel = new DefaultTableModel(tableHeader2, 0);
		postStateTable = new JTable(postStateModel);
		JScrollPane scrollpane4 = new JScrollPane(postStateTable);
		scrollpane4.setBounds(10, 185, 225, 100);
		postGroup.add(scrollpane4);

		postStateAddBtn = new JButton("Add");
		postStateAddBtn.setBounds(10, 295, 100, 30);
		postStateAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new State Postcond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputSceen2 = new InputState(artifactList, postStateModel, textPostCondition);
				inputSceen2.run();
			}
		});
		postGroup.add(postStateAddBtn);

		postStateDelBtn = new JButton("Delete");
		postStateDelBtn.setBounds(120, 295, 100, 30);
		postStateDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (postStateTable.getSelectedRow() >= 0) {
					String selected = (String) postStateModel.getValueAt(postStateTable.getSelectedRow(), 0);
					if (textPostCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPostCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPostCondition.setText(textPostCondition.getText().replaceFirst(selected, ""));
					postStateModel.removeRow(postStateTable.getSelectedRow());
				}
			}
		});
		postGroup.add(postStateDelBtn);

		// Defined Table
		preDefinedModel = new DefaultTableModel(tableHeader3, 0);
		preDefinedTable = new JTable(preDefinedModel);
		JScrollPane scrollpane5 = new JScrollPane(preDefinedTable);
		scrollpane5.setBounds(235, 185, 225, 100);
		preGroup.add(scrollpane5);

		preDefinedAddBtn = new JButton("Add");
		preDefinedAddBtn.setBounds(235, 295, 100, 30);
		preDefinedAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new Defined Precond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen3 = new InputDefined(artifactList, preDefinedModel, textPreCondition);
				inputScreen3.run();
			}
		});
		preGroup.add(preDefinedAddBtn);

		preDefinedDelBtn = new JButton("Delete");
		preDefinedDelBtn.setBounds(345, 295, 100, 30);
		preDefinedDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (preDefinedTable.getSelectedRow() >= 0) {
					String selected = (String) preDefinedModel.getValueAt(preDefinedTable.getSelectedRow(), 0);
					if (textPreCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPreCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPreCondition.setText(textPreCondition.getText().replaceFirst(selected, ""));
					preDefinedModel.removeRow(preDefinedTable.getSelectedRow());
				}
			}
		});
		preGroup.add(preDefinedDelBtn);

		postDefinedModel = new DefaultTableModel(tableHeader3, 0);
		postDefinedTable = new JTable(postDefinedModel);
		JScrollPane scrollpane6 = new JScrollPane(postDefinedTable);
		scrollpane6.setBounds(235, 185, 225, 100);
		postGroup.add(scrollpane6);

		postDefinedAddBtn = new JButton("Add");
		postDefinedAddBtn.setBounds(235, 295, 100, 30);
		postDefinedAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new State Postcond
				if (artifactList == null || artifactList.isEmpty() || businessTaskCB.getItemCount() == 0) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts and Business Tasks.",
							"Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen3 = new InputDefined(artifactList, postDefinedModel, textPostCondition);
				inputScreen3.run();
			}
		});
		postGroup.add(postDefinedAddBtn);

		postDefinedDelBtn = new JButton("Delete");
		postDefinedDelBtn.setBounds(345, 295, 100, 30);
		postDefinedDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (postDefinedTable.getSelectedRow() >= 0) {
					String selected = (String) postDefinedModel.getValueAt(postDefinedTable.getSelectedRow(), 0);
					if (textPostCondition.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textPostCondition.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textPostCondition.setText(textPostCondition.getText().replaceFirst(selected, ""));
					postDefinedModel.removeRow(postDefinedTable.getSelectedRow());
				}
			}
		});
		postGroup.add(postDefinedDelBtn);

		// Result
		lblPreCondition = new JLabel("Pre Condition :");
		lblPreCondition.setBounds(10, 325, 150, 30);
		preGroup.add(lblPreCondition);

		lblPostCondition = new JLabel("Post Condition :");
		lblPostCondition.setBounds(10, 325, 150, 30);
		postGroup.add(lblPostCondition);

		lblService = new JLabel("Service : ");
		lblService.setBounds(20, 530, 150, 30);
		mainScreen.add(lblService);

		textPreCondition = new JTextArea();
		textPreCondition.setEditable(false);
		textPreCondition.setLineWrap(true);
		textPreCondition.setWrapStyleWord(true);
		JScrollPane spPreCondtion = new JScrollPane(textPreCondition);
		spPreCondtion.setBounds(10, 360, 450, 90);
		preGroup.add(spPreCondtion);
		textPreCondition.setColumns(10);

		textPostCondition = new JTextArea();
		textPostCondition.setEditable(false);
		textPostCondition.setLineWrap(true);
		textPostCondition.setWrapStyleWord(true);
		JScrollPane spPostCondtion = new JScrollPane(textPostCondition);
		spPostCondtion.setBounds(10, 360, 450, 90);
		textPostCondition.setColumns(10);
		postGroup.add(spPostCondtion);

		textService = new JTextField();
		textService.setBounds(120, 530, 500, 50);
		textService.setColumns(10);
		mainScreen.add(textService);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(20, 600, 100, 30);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BusinessTask sBT = (BusinessTask) businessTaskCB.getSelectedItem();
				try {
					// preCond
					sBT.preCondition = ((textPreCondition.getText().equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(textPreCondition.getText()));
					String[] preCondList = textPreCondition.getText().split(" & ");
					for (int i = 0; i < preCondList.length; i++) {
						sBT.addLiteral2Pre(preCondList[i]);
					}

					// postCond
					sBT.postCondition = ((textPostCondition.getText().equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(textPostCondition.getText()));
					String[] postCondList = textPostCondition.getText().split(" & ");
					for (int i = 0; i < postCondList.length; i++) {
						sBT.addLiteral2Post(postCondList[i]);
					}

					// postComma
					String postCommaStr = "";
					for (int i = 0; i < postDefinedModel.getRowCount(); i++) {
						postCommaStr = ((postCommaStr.equals("")) ? "" : " & ") + postDefinedModel.getValueAt(i, 0);
						sBT.addLiteral2PostComma((String) postDefinedModel.getValueAt(i, 0));
					}
					sBT.postComma = ((postCommaStr.equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(postCommaStr));

					// Service & artifacts
					List<String> artifactList = new ArrayList<String>();
					searchArtifact(preAttrModel, "At", artifactList);
					searchArtifact(postAttrModel, "At", artifactList);
					searchArtifact(preDefinedModel, "At", artifactList);
					searchArtifact(postDefinedModel, "At", artifactList);
					searchArtifact(preStateModel, "Is", artifactList);
					searchArtifact(postStateModel, "Is", artifactList);
					sBT.artifacts = "";
					boolean isFirst = true;
					for (String iString : artifactList) {
						sBT.artifacts += ((isFirst) ? "" : ",") + iString;
						isFirst = false;
					}
					System.out.println(sBT.artifacts);
					sBT.service = textService.getText();

					// Instance
					sBT.genInstance(condExprList);
					System.out.println(sBT.name + "-" + sBT.getinstance_().toString());
					// Dom

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mainScreen.add(btnSubmit);
		preAttrTable.setDefaultEditor(Object.class, null);
		preDefinedTable.setDefaultEditor(Object.class, null);
		preStateTable.setDefaultEditor(Object.class, null);
		postAttrTable.setDefaultEditor(Object.class, null);
		postDefinedTable.setDefaultEditor(Object.class, null);
		postStateTable.setDefaultEditor(Object.class, null);
	}

	public void createConScreen() {
		condition_consequence = new JPanel();
		condition_consequence.setLayout(null);
		JPanel condGroup = new JPanel();
		condGroup.setLayout(null);
		condGroup.setBorder(new TitledBorder("Condition String"));
		condGroup.setBounds(10, 65, 470, 460);
		condition_consequence.add(condGroup);

		JPanel consGroup = new JPanel();
		consGroup.setLayout(null);
		consGroup.setBorder(new TitledBorder("Consequence String"));
		consGroup.setBounds(490, 65, 470, 460);
		condition_consequence.add(consGroup);

		// Condition & Consequence Table
		condAttrModel = new DefaultTableModel(tableHeader1, 0);
		condAttrTable = new JTable(condAttrModel);
		JScrollPane scrollpane1 = new JScrollPane(condAttrTable);
		scrollpane1.setBounds(10, 15, 450, 200);
		condGroup.add(scrollpane1);

		condAttrAddBtn = new JButton("Add");
		condAttrAddBtn.setBounds(10, 225, 100, 30);

		condAttrAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new Attr Precond
				if (artifactList == null || artifactList.isEmpty()) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen1 = new InputAttribute(artifactList, condAttrModel, textCond, condExprList);
				inputScreen1.run();
			}
		});
		condGroup.add(condAttrAddBtn);

		condAttrDelBtn = new JButton("Delete");
		condAttrDelBtn.setBounds(120, 225, 100, 30);
		condAttrDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (condAttrTable.getSelectedRow() >= 0) {
					String selected = ((String) condAttrModel.getValueAt(condAttrTable.getSelectedRow(), 0)
							+ (String) condAttrModel.getValueAt(condAttrTable.getSelectedRow(), 1)
							+ (String) condAttrModel.getValueAt(condAttrTable.getSelectedRow(), 2));
					for (int i = 0; i < condExprList.size(); i++) {
						if (condExprList.get(i).toString().equals(selected)) {
							condExprList.remove(i);
							break;
						}
					}
					if (textCond.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textCond.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textCond.setText(textCond.getText().replaceFirst(selected, ""));
					condAttrModel.removeRow(condAttrTable.getSelectedRow());
					System.out.println(condExprList);
				}
			}
		});
		condGroup.add(condAttrDelBtn);

		consAttrModel = new DefaultTableModel(tableHeader1, 0);
		consAttrTable = new JTable(consAttrModel);
		JScrollPane scrollpane2 = new JScrollPane(consAttrTable);
		scrollpane2.setBounds(10, 15, 450, 200);
		consGroup.add(scrollpane2);

		consAttrAddBtn = new JButton("Add");
		consAttrAddBtn.setBounds(10, 225, 100, 30);
		consAttrAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Add new Attr Postcond
				if (artifactList == null || artifactList.isEmpty()) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				inputScreen1 = new InputAttribute(artifactList, consAttrModel, textCons, consExprList);
				inputScreen1.run();
			}
		});
		consGroup.add(consAttrAddBtn);

		consAttrDelBtn = new JButton("Delete");
		consAttrDelBtn.setBounds(120, 225, 100, 30);
		consAttrDelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (consAttrTable.getSelectedRow() >= 0) {
					String selected = ((String) consAttrModel.getValueAt(consAttrTable.getSelectedRow(), 0)
							+ (String) consAttrModel.getValueAt(consAttrTable.getSelectedRow(), 1)
							+ (String) consAttrModel.getValueAt(consAttrTable.getSelectedRow(), 2));
					for (int i = 0; i < consExprList.size(); i++) {
						if (consExprList.get(i).toString().equals(selected)) {
							consExprList.remove(i);
							break;
						}
					}
					if (textCons.getText().contains(" & " + selected)) {
						selected = " & " + selected;
					} else {
						if (textCons.getText().contains(selected + " & ")) {
							selected = selected + " & ";
						}
					}
					textCons.setText(textCons.getText().replaceFirst(selected, ""));
					consAttrModel.removeRow(consAttrTable.getSelectedRow());
				}
			}
		});
		consGroup.add(consAttrDelBtn);
		// Result
		lblCond = new JLabel("Condition :");
		lblCond.setBounds(10, 325, 150, 30);
		condGroup.add(lblCond);

		lblCons = new JLabel("Consequence :");
		lblCons.setBounds(10, 325, 150, 30);
		consGroup.add(lblCons);

		textCond = new JTextArea();
		textCond.setEditable(false);
		textCond.setLineWrap(true);
		textCond.setWrapStyleWord(true);
		JScrollPane spPreCondtion = new JScrollPane(textCond);
		spPreCondtion.setBounds(10, 360, 450, 90);
		condGroup.add(spPreCondtion);
		textCond.setColumns(10);

		textCons = new JTextArea();
		textCons.setEditable(false);
		textCons.setLineWrap(true);
		textCons.setWrapStyleWord(true);
		JScrollPane spPostCondtion = new JScrollPane(textCons);
		spPostCondtion.setBounds(10, 360, 450, 90);
		textCons.setColumns(10);
		consGroup.add(spPostCondtion);
	}

	public void createArtifactScreen() {
		final String header[] = { "Name", "Attributes", "States" };
		artifact = new JPanel();
		artifact.setLayout(null);
		classDiagramBtn = new JButton("Read Class Diagram");
		classDiagramBtn.setBounds(20, 30, 200, 30);
		classDiagramBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser classDiagramFile = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".xmi", "xmi");
				classDiagramFile.setFileFilter(fileFilter);
				int returnVal = classDiagramFile.showOpenDialog(artifact);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = classDiagramFile.getSelectedFile();
					artifactCBSc3.removeAllItems();
					classDiagramPath.setText(file.getAbsolutePath());
					artifactsModel.setRowCount(0);
					try {
						artifactList = (List<Artifact>) ArtifactRepository.getListArtifact(file.getAbsolutePath());
						for (Artifact i : artifactList) {
							String attributeList = "";
							String stateList = "";
							for (ArtifactAttribute j : i.getListAttribute()) {
								attributeList += j.toString() + ",";
							}
							String temp[] = { i.getName(), attributeList, stateList };
							artifactsModel.addRow(temp);
							artifactCBSc3.addItem(i);
						}
					} catch (XmiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		artifact.add(classDiagramBtn);
		artifactCBSc3 = new JComboBox<>();
		artifactCBSc3.setBounds(300, 80, 320, 30);
		readStateBtn = new JButton("Read State Chart");
		readStateBtn.setBounds(20, 80, 200, 30);
		readStateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (artifactList == null || artifactList.isEmpty()) {
					JOptionPane.showMessageDialog(MainScreen.this, "Please Input Artifacts.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				final JFileChooser classDiagramFile = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".xmi", "xmi");
				classDiagramFile.setFileFilter(fileFilter);
				int returnVal = classDiagramFile.showOpenDialog(artifact);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = classDiagramFile.getSelectedFile();
					Artifact selected = (Artifact) artifactCBSc3.getSelectedItem();
					try {
						selected.setListState(StateDiagramReader.getListState(file.getAbsolutePath()));
						artifactsModel.setRowCount(0);
						for (Artifact i : artifactList) {
							String attributeList = "";
							String stateList = "";
							for (ArtifactAttribute j : i.getListAttribute()) {
								attributeList += j.toString() + ",";
							}
							for (ArtifactState j : i.getListState()) {
								stateList += j.toString() + ",";
							}
							String temp[] = { i.getName(), attributeList, stateList };
							artifactsModel.addRow(temp);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		artifact.add(readStateBtn);
		artifact.add(artifactCBSc3);
		classDiagramPath = new JTextField();
		classDiagramPath.setBounds(300, 30, 320, 30);
		artifact.add(classDiagramPath);
		classDiagramPath.setColumns(10);
		artifactsModel = new DefaultTableModel(header, 0);
		artifactsTable = new JTable(artifactsModel);
		artifactsTable.setEnabled(false);
		JScrollPane scrollpane = new JScrollPane(artifactsTable);
		scrollpane.setBounds(150, 150, 700, 500);
		artifact.add(scrollpane);

	}

	// moi them 1809
	public void searchArtifact(DefaultTableModel table, String s, List<String> artifactList) {
		for (int i = 0; i < table.getRowCount(); i++) {
			String tmpArt = ((String) table.getValueAt(i, 0)).split(s)[0];
			if (tmpArt.substring(0, 1).equals("~")) {
				tmpArt = tmpArt.substring(1);
			}
			for (String iArt : artifactList) {
				if (tmpArt.equals(iArt)) {
					return;
				}
			}
			artifactList.add(tmpArt);
			if (table.getColumnCount() > 1) {
				String tmpArt2 = ((String) table.getValueAt(i, 2)).split(s)[0];
				if (tmpArt2.substring(0, 1).equals("~")) {
					tmpArt = tmpArt.substring(1);
				}
				for (String iArt : artifactList) {
					if (tmpArt2.equals(iArt)) {
						return;
					}
				}
				artifactList.add(tmpArt2);
			}
		}
	}

	// Main method to get things started
	public static void main(String args[]) {
		// Create an instance of the test application
		MainScreen mainFrame = new MainScreen();
		mainFrame.setVisible(true);
	}
}
//hello