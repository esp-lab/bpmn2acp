package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//test
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.DataInputAssociation;
import org.camunda.bpm.model.bpmn.instance.DataOutputAssociation;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.event.ListSelectionEvent;
//end test
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import orbital.logic.imp.Formula;
import orbital.logic.sign.ParseException;

class MainScreen extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel businessTaskScreen;
	private JPanel condition_consequence;
	private JPanel artifact;
	private JPanel algorithm;
	private List<Artifact> artifactList = new ArrayList<Artifact>();
	private JPanel topPanel;
	private InputAttribute inputScreen1;
	private InputState inputSceen2;
	private InputDefined inputScreen3;

	// screen 1
	private JButton readPrePostFromFileBtn;
	private JTextField textFilePath;
	private List<int[]> pathList;
	private List<BusinessTask> BTList;
	private JComboBox<BusinessTask> businessTaskCB;
	// fix jlabel-> jButton
	private JButton lblBusinessTask;
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

	//begin screen 5
	private JPanel containerPreAndPostScreen;
	private JTabbedPane tabbedPanePrePostCond;
	private JTabbedPane tabbedPanePreCond;

	private JTabbedPane tabbedPanePostCond;

	private JPanel preCond; // preCondePanel into containerPreAndPost Screen
	private JPanel preState;
	private JPanel preAttribute;

	private JComboBox mainClassPre;
	private JComboBox mainStatePre;
	private JPanel mainStatePanel;
	private JTable preAttJTable;

	private DefaultTableModel statePreCondDTM;
	private DefaultTableModel attPreConDTM;
	private JTextArea textAreaPreCond;

	//private JPanel showResultAttPreGroup;
	private JTable showResultAttPreJT;
	private DefaultTableModel showResultAttPreDTM;
	private JButton btnDeleteResultAttPre;
	private JButton btnAddResultAttPre;

	// for post condition 
	private JPanel postCond;
	private JPanel postState;
	private JPanel postAttribute;

	private JComboBox mainClass;

	private JTable postAttJTable;

	private DefaultTableModel attPostConDTM;

	//begin ui new screen

	private JPanel stateScreenJPanel;
	private DefaultTableModel stateCondDTM;
	private JTable showStateTB;
	private JTextArea jtPreCondShow;
	private JTextArea jtPostCondShow;
	private List<Artifact> artifactsListForPost = new ArrayList<Artifact>();
	private JTextArea showCondEachStateJTA;
	private List<TaskAndCond> listTaskAndCond = new ArrayList<TaskAndCond>();

	//end screen 5
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

		// Set Static MainScreen
		Algorithm.mainScreen = MainScreen.this;

		// Create the tab pages
		createBusinessTaskScreen();
		createConScreen();
		//createPostAndPreCondition();
		createArtifactScreen();
		createAlgorithmScreen();
		createPostAndPreCondition();
		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Artifact", artifact);

		tabbedPane.addTab("Condition & Consequence", condition_consequence);
		tabbedPane.addTab("Pre and post condition", containerPreAndPostScreen);
		//tabbedPane.addTab("Business Task", businessTaskScreen);
		tabbedPane.addTab("Algorithm", algorithm);
		//tabbedPane.addTab("Complete pre and post condition", algorithm);
		// Create for pre and post condition project

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
						// if (label.length == 5){
						System.out.println("result:");
						System.out.println(Algorithm.elimination(condExprList, consExprList, label, BTList.toArray(a)));
						// }

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		algorithm.add(btnElimination);
		JButton btnReOrdering = new JButton("Run Re-ordering");
		btnReOrdering.setBounds(400, 50, 200, 50);
		btnReOrdering.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Formula> br = new ArrayList<>();
				while (JOptionPane.showConfirmDialog(MainScreen.this,
						"Do you want to add new BusinessRule?") == JOptionPane.YES_OPTION) {
					String brStr = JOptionPane.showInputDialog(MainScreen.this, "Input a Business Rule:");
					Formula brFml;
					try {
						brFml = (Formula) Algorithm.logic.createExpression(brStr);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(MainScreen.this,
								"Wrong Format, Please input another Business Rule .", "Warning",
								JOptionPane.WARNING_MESSAGE);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(MainScreen.this,
								"Wrong Format, Please input another Business Rule .", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}

				}
				for (int[] label : pathList) {
					BusinessTask[] a = new BusinessTask[BTList.size()];
					try {
						//						if (label.length == 6) {
						Algorithm.reordering(condExprList, consExprList, label, BTList.toArray(a), br);
						//						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		algorithm.add(btnReOrdering);
	}

	public void createBusinessTaskScreen() {

		businessTaskScreen = new JPanel();
		pathList = new ArrayList<int[]>();
		BTList = new ArrayList<BusinessTask>();
		businessTaskScreen.setLayout(null);
		final JPanel postGroup = new JPanel();
		final JPanel preGroup = new JPanel();
		JButton btnReadFile = new JButton("Read BPMN");
		btnReadFile.setBounds(20, 30, 100, 30);
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser bpmnFile = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".bpmn", "bpmn");
				bpmnFile.setFileFilter(fileFilter);
				//System.out.println(fileFilter);
				int returnVal = bpmnFile.showOpenDialog(businessTaskScreen);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = bpmnFile.getSelectedFile();
					textFilePath.setText(file.getName());
					BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
					Collection<Task> tempListTask = modelInstance.getModelElementsByType(Task.class);

					// Remove All element in businessTaskCB and Table
					businessTaskCB.removeAllItems();
					preGroup.show();
					postGroup.show();
					try {
						for (Task i : tempListTask) {

							BusinessTask n = new BusinessTask();
							n.name = i.getName();

							BTList.add(n);
							businessTaskCB.addItem(n);
							// write file name to pre and post condition
							n.generateInputFile(artifactList);
							//After reading task, n.name is name of task and write file
							//	System.out.println(n.name);
						}
						pathList = BusinessProcessRepository.getListPath(file);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				businessTaskScreen.validate();
			}
		});
		businessTaskScreen.add(btnReadFile);

		textFilePath = new JTextField();
		textFilePath.setBounds(150, 30, 320, 30);
		businessTaskScreen.add(textFilePath);
		textFilePath.setColumns(10);

		lblBusinessTask = new JButton("Business Task:");
		lblBusinessTask.setBounds(500, 30, 100, 30);
		businessTaskScreen.add(lblBusinessTask);

		businessTaskCB = new JComboBox<BusinessTask>();
		businessTaskCB.setBounds(600, 30, 350, 30);
		businessTaskScreen.add(businessTaskCB);

		preGroup.setLayout(null);
		preGroup.setBorder(new TitledBorder("Pre Condition"));
		preGroup.setBounds(10, 65, 470, 460);
		businessTaskScreen.add(preGroup);

		postGroup.setLayout(null);
		postGroup.setBorder(new TitledBorder("Post Condition"));
		postGroup.setBounds(490, 65, 470, 460);
		businessTaskScreen.add(postGroup);

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
		businessTaskScreen.add(lblService);

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
		businessTaskScreen.add(textService);

		readPrePostFromFileBtn = new JButton("Read All Task From File");
		readPrePostFromFileBtn.setBounds(650, 600, 300, 30);
		readPrePostFromFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BTList != null & BTList.size() != 0) {
					for (BusinessTask bt : BTList) {
						try {
							bt.artifacts = new ArrayList<>();
							bt.pre_cond = new ArrayList<>();
							bt.post_cond = new ArrayList<>();
							bt.post_comma = new ArrayList<>();
							ReadTaskFile.ParseArtifact(bt, artifactList);
							ReadTaskFile.ParsePrecondition(bt);
							ReadTaskFile.ParsePostcondition(bt);
							ReadTaskFile.ParsePostCommacondition(bt);
							bt.genFormula();
							bt.genInstance(condExprList);
							JOptionPane.showMessageDialog(MainScreen.this,
									"Reading " + bt.name + ".txt file completed.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					preGroup.hide();
					postGroup.hide();
				}
			}
		});
		businessTaskScreen.add(readPrePostFromFileBtn);

		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(20, 600, 100, 30);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BusinessTask sBT = (BusinessTask) businessTaskCB.getSelectedItem();
				try {
					// preCond
					sBT.setPreCondition(((textPreCondition.getText().equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(textPreCondition.getText())));
					String[] preCondList = textPreCondition.getText().split(" & ");
					for (int i = 0; i < preCondList.length; i++) {
						sBT.addLiteral2Pre(preCondList[i]);
					}

					// postCond
					sBT.setPostCondition(((textPostCondition.getText().equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(textPostCondition.getText())));
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
					sBT.setPostComma(((postCommaStr.equals("")) ? null
							: (Formula) Algorithm.logic.createExpression(postCommaStr)));

					// Service & artifacts
					List<String> artifactStringList = new ArrayList<String>();
					searchArtifact(preAttrModel, "At", artifactStringList);
					searchArtifact(postAttrModel, "At", artifactStringList);
					searchArtifact(preDefinedModel, "At", artifactStringList);
					searchArtifact(postDefinedModel, "At", artifactStringList);
					searchArtifact(preStateModel, "Is", artifactStringList);
					searchArtifact(postStateModel, "Is", artifactStringList);
					sBT.artifacts = new ArrayList<Artifact>();
					for (Artifact ele : artifactList) {
						if (artifactStringList.contains((String) ele.getName())) {
							sBT.artifacts.add(ele);
						}
					}

					System.out.println("artifact: " + sBT.artifacts);
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
		businessTaskScreen.add(btnSubmit);
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

		JButton readFileBtn = new JButton("Read From File");
		readFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					condExprList = new ArrayList<>();
					consExprList = new ArrayList<>();
					ReadTaskFile.ParseDRcondition(condExprList);
					ReadTaskFile.ParseDRconsequence(consExprList);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		readFileBtn.setBounds(10, 10, 150, 30);
		condition_consequence.add(readFileBtn);
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
						if (BTList != null && BTList.size() != 0) {
							for (BusinessTask bt : BTList) {
								bt.generateInputFile(artifactList);
							}
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
		artifactsTable.setEnabled(true);
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

	//create post and pre condition 
	public void createPostAndPreCondition() {
		containerPreAndPostScreen = new JPanel();
		containerPreAndPostScreen.setLayout(null);
		JButton readBPMN = new JButton("Read BPMN");

		readBPMN.setBounds(20, 30, 100, 30);

		containerPreAndPostScreen.add(readBPMN);
		textFilePath = new JTextField();
		textFilePath.setBounds(150, 30, 320, 30);
		containerPreAndPostScreen.add(textFilePath);
		textFilePath.setColumns(10);

		lblBusinessTask = new JButton("Business Task:");
		lblBusinessTask.setBounds(20, 350, 150, 30);
		containerPreAndPostScreen.add(lblBusinessTask);

		businessTaskCB = new JComboBox<BusinessTask>();
		businessTaskCB.setBounds(200, 350, 350, 30);
		containerPreAndPostScreen.add(businessTaskCB);

		stateScreenJPanel = new JPanel();
		stateScreenJPanel.setBounds(30, 80, 950, 250);
		stateScreenJPanel.setBorder(new TitledBorder("State Screen"));
		stateScreenJPanel.setLayout(null);

		createStateScreen();
		createShowPrePostCond();
		readBPMN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// StateInterfacePrePost a= new StateInterfacePrePost();
				// a.test(stateScreenJPanel, artifactList);
				// System.out.println(artifactList.get(0).getName());
				for (TaskAndCond i : listTaskAndCond) {
					i.clearList();
				}
				listTaskAndCond.clear();
				final JFileChooser bpmnFile = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".bpmn", "bpmn");
				bpmnFile.setFileFilter(fileFilter);
				int returnVal = bpmnFile.showOpenDialog(containerPreAndPostScreen);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = bpmnFile.getSelectedFile();
					textFilePath.setText(file.getName());
					BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
					Collection<Task> tempListTasks = modelInstance.getModelElementsByType(Task.class);
					//System.out.println(artifactList);

					businessTaskCB.removeAllItems();
					mainClass.removeAllItems();

					try {

						//read xmlFile
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
						Document doc = dBuilder.parse(file);
						doc.getDocumentElement().normalize();
						NodeList nList = doc.getElementsByTagName("bpmn:dataObjectReference");
						//handlePreCond("listPreCond");

						//mainClass.removeAllItems();
						//System.out.println("hello");
						for (Task i : tempListTasks) {
							//System.out.println(i.getName());
							TaskAndCond itemTaskAndCond = new TaskAndCond();
							itemTaskAndCond.setTask(i);

							BusinessTask n = new BusinessTask();
							n.name = i.getName();
							BTList.add(n);
							businessTaskCB.addItem(n);
							if (i.getDataInputAssociations().parallelStream().toArray().length == 1) {
								DomElement data = ((DataInputAssociation) i.getDataInputAssociations().toArray()[0])
										.getDomElement();
								String id = data.getChildElements().get(0).getTextContent();

								for (int temp = 0; temp < nList.getLength(); temp++) {
									Node nNode = nList.item(temp);
									if (nNode.getNodeType() == Node.ELEMENT_NODE) {
										Element eElement = (Element) nNode;
										//System.out.println(eElement.getAttribute("id"));
										if (eElement.getAttribute("id").equals(id)) {
											String listCond = (String) eElement.getAttribute("name");
											itemTaskAndCond.setListCondInputFromBpmn(listCond);
											//System.out.println(itemTaskAndCond.getListCondInputFromBpmn());
											//System.out.println("input "+eElement.getAttribute("name"));
										}
									}
								}
							}

							if (i.getDataOutputAssociations().parallelStream().toArray().length == 1) {
								DomElement data = ((DataOutputAssociation) i.getDataOutputAssociations().toArray()[0])
										.getDomElement();
								String id = data.getChildElements().get(0).getTextContent();
								//System.out.println("go there");
								for (int temp = 0; temp < nList.getLength(); temp++) {
									Node nNode = nList.item(temp);
									if (nNode.getNodeType() == Node.ELEMENT_NODE) {
										Element eElement = (Element) nNode;
										//System.out.println(eElement.getAttribute("id"));
										if (eElement.getAttribute("id").equals(id)) {
											String listCond = (String) eElement.getAttribute("name");
											itemTaskAndCond.setListCondOutputFromBpmn(listCond);
											//System.out.println("output "+eElement.getAttribute("name"));
										}
									}
								}
							}
							handleDataCond(itemTaskAndCond);
							listTaskAndCond.add(itemTaskAndCond);

							n.generateInputFile(artifactList);

						}
						//System.out.println("hello");
						for (Artifact i : artifactList) {
							mainClass.addItem(i);

						}
						businessTaskCB.setSelectedIndex(listTaskAndCond.size() - 1);
						businessTaskCB.setSelectedIndex(0);
						//System.out.println(((BusinessTask) businessTaskCB.getSelectedObjects()[0]).name);

						pathList = BusinessProcessRepository.getListPath(file);

					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Your file BPMN doesn't have any task.");
					}

				}
				containerPreAndPostScreen.validate();
			}
		});

		businessTaskCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				//System.out.println("hell");
				//if (event.getStateChange() == ItemEvent.SELECTED) {
				// System.out.println("out if");
				// System.out.println(listTaskAndCond.size());
				if (listTaskAndCond.size() > 0) {
					//System.out.println("in if");
					BusinessTask seleBusinessTask = (BusinessTask) businessTaskCB.getSelectedItem();
					int indexOfListTaskAndCond = 0;
					for (TaskAndCond i : listTaskAndCond) {
						if (seleBusinessTask.name.toLowerCase().equals(i.getTask().getName().toLowerCase())) {
							break;
						}
						indexOfListTaskAndCond++;
					}

					TaskAndCond itemTaskAndCond = listTaskAndCond.get(indexOfListTaskAndCond);
					handleDataCond(itemTaskAndCond);
					String preCond = "";
					int count = 0;
					int index = 0;
					// System.out.println(itemTaskAndCond.getListCondInputFromBpmn());
					// for(String i: itemTaskAndCond.getListPreCond()){
					// 	System.out.println(i);
					// }
					//String[] a= itemTaskAndCond.getListCondInputFromBpmn().split(" \\\\/");

					//	itemTaskAndCond.setListInputPosition();
					// System.out.println(itemTaskAndCond.getListInputPossition().size());

					for (String i : itemTaskAndCond.getListPreCond()) {
						if (itemTaskAndCond.getListInputPossition().size() == 0) {
							preCond += "\n" + i;
						}
						//System.out.println(i+3);

						else {
							if (count == itemTaskAndCond.getListInputPossition().get(index)) {
								preCond += "\n" + "\\/";
								if (index < itemTaskAndCond.getListInputPossition().size() - 1) {
									index++;
								}
							}
							preCond += "\n" + i;
							count++;
						}
						//preCond += "\n" + i;

						//System.out.println(i);
						//System.out.println(itemTaskAndCond.getLi

					}
					String postCond = "";
					count = 0;
					index = 0;
					//System.out.println(itemTaskAndCond.getListOutputPosition().size());
					for (String i : itemTaskAndCond.getListPostCond()) {
						if (itemTaskAndCond.getListOutputPosition().size() == 0) {
							postCond += "\n" + i;
						}
						//System.out.println(i+3);

						else {
							if (count == itemTaskAndCond.getListOutputPosition().get(index)) {
								postCond += "\n" + "\\/";
								if (index < itemTaskAndCond.getListOutputPosition().size() - 1) {
									index++;
								}
							}
							postCond += "\n" + i;
							count++;
						}
					}
					jtPreCondShow.setText(preCond);
					jtPostCondShow.setText(postCond);
				}

				//}
			}
		});

		containerPreAndPostScreen.add(stateScreenJPanel);

	}

	public void createStateScreen() {
		mainClass = new JComboBox<Artifact>();
		mainClass.setBounds(30, 20, 150, 30);

		stateScreenJPanel.add(mainClass);

		String column[] = { "Name of State", "Add Attribute" };
		stateCondDTM = new DefaultTableModel(column, 0) {
			private boolean ImInLoop = false;

			@Override
			public Class<?> getColumnClass(int column) {

				switch (column) {
				case 0:
					return String.class;

				default:
					return Boolean.class;

				}
			}
		};
		showStateTB = new JTable(stateCondDTM);

		mainClass.addItemListener(new ItemListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(mainClass.)
				//System.out.println(arg0.ACTION_EVENT_MASK);

			}

			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Object item = event.getItem();
					// do something with object
					Artifact selectedArtifact = (Artifact) mainClass.getSelectedItem();
					//System.out.println(selectedArtifact.getName());
					List<ArtifactState> listState = new ArrayList(selectedArtifact.getListState());

					for (int i = stateCondDTM.getRowCount() - 1; i >= 0; i--) {
						stateCondDTM.removeRow(i);
					}

					for (ArtifactState i : listState) {

						stateCondDTM.addRow(new Object[] { i.getName(), "Add" });

					}

					showStateTB.getColumnModel().getColumn(1)
							.setCellEditor(new ButtonEditorStateCond(new JTextField(), artifactList,
									(Artifact) mainClass.getSelectedItem(), showStateTB, jtPreCondShow, jtPostCondShow,
									showCondEachStateJTA, businessTaskCB));

				}
			}
		});

		showStateTB.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				Artifact selectedArtifact = (Artifact) mainClass.getSelectedItem();
				List<ArtifactState> lstState = new ArrayList<ArtifactState>(selectedArtifact.getListState());
				//System.out.println(showStateTB.getSelectedRow());
				if (lstState.size() != 0 && showStateTB.getSelectedRow() > -1
						&& showStateTB.getSelectedRow() < lstState.size()) {
					//System.out.println("showStateTB changed");
					ArtifactState selectedState = lstState.get(showStateTB.getSelectedRow());
					showCondEachStateJTA.setText(selectedState.getCondition());
				}

			}
		});

		showStateTB.getColumn("Add Attribute").setCellRenderer(new ButtonRenderer());

		JScrollPane scrollPane = new JScrollPane(showStateTB);
		scrollPane.setBounds(250, 10, 300, 200);

		//show Condition of each State;

		showCondEachStateJTA = new JTextArea();
		showCondEachStateJTA.setEditable(false);
		showCondEachStateJTA.setLineWrap(true);
		JPanel showCondEachStateJP = new JPanel();
		showCondEachStateJP.setBounds(600, 20, 300, 200);
		showCondEachStateJP.setLayout(null);
		JScrollPane scrollCond = new JScrollPane(showCondEachStateJTA);
		scrollCond.setBounds(10, 10, 280, 180);
		showCondEachStateJP.add(scrollCond);

		stateScreenJPanel.add(scrollPane);
		stateScreenJPanel.add(showCondEachStateJP);
	}

	public void createShowPrePostCond() {
		JPanel prePostShow = new JPanel();
		prePostShow.setBounds(30, 400, 950, 250);
		prePostShow.setBorder(new TitledBorder("Pre and post condition"));
		prePostShow.setLayout(null);
		containerPreAndPostScreen.add(prePostShow);

		JPanel preShowJP = new JPanel();
		preShowJP.setBounds(20, 30, 425, 150);
		preShowJP.setBorder(new TitledBorder("Pre condition "));
		preShowJP.setLayout(null);
		prePostShow.add(preShowJP);

		JPanel postShowJP = new JPanel();
		postShowJP.setBounds(500, 30, 425, 150);
		postShowJP.setBorder(new TitledBorder("Post condition"));
		postShowJP.setLayout(null);
		prePostShow.add(postShowJP);

		jtPreCondShow = new JTextArea();
		jtPreCondShow.setLineWrap(true);
		JScrollPane scrollPreShow = new JScrollPane(jtPreCondShow);
		scrollPreShow.setBounds(10, 20, 400, 120);
		preShowJP.add(scrollPreShow);

		jtPostCondShow = new JTextArea();
		jtPostCondShow.setLineWrap(true);
		JScrollPane scrollPostShow = new JScrollPane(jtPostCondShow);
		scrollPostShow.setBounds(10, 20, 400, 120);
		postShowJP.add(scrollPostShow);

		JButton writeToFile = new JButton("Write to file");
		writeToFile.setBounds(400, 200, 200, 30);
		prePostShow.add(writeToFile);
		writeToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				int size = businessTaskCB.getItemCount();
				System.out.println(businessTaskCB.getItemCount());
				for (int index = 0; index < size; index++) {
					FileWriter fw = null;
					BufferedWriter bw = null;
					try {

						BusinessTask seleBusinessTask = (BusinessTask) businessTaskCB.getItemAt(index);

						String name = seleBusinessTask.name;
						//System.out.println(name);
						String fileName = name + ".txt";
						//String testData = "hahah";

						File file = new File(fileName);

						int indexOfListTaskAndCond = 0;
						for (TaskAndCond i : listTaskAndCond) {
							if (seleBusinessTask.name.toLowerCase().equals(i.getTask().getName().toLowerCase())) {
								break;
							}
							indexOfListTaskAndCond++;
						}
						TaskAndCond itemTaskAndCond = listTaskAndCond.get(indexOfListTaskAndCond);
						System.out.println(itemTaskAndCond.getListPostCond().get(0));
						//System.out.println("access sucessfully");
						//List<String> listState = new ArrayList<String>();
						List<String> listPreCond = new ArrayList<String>();
						List<String> listPostCond = new ArrayList<String>();
						List<String> listArtifact = new ArrayList<String>();
						int count = 0;
						int indexPosition = 0;
						for (String i : itemTaskAndCond.getListPreCond()) {
							if (itemTaskAndCond.getListInputPossition().size() != 0) {
								if (count == itemTaskAndCond.getListInputPossition().get(indexPosition)) {
									listPreCond.add("\\/");
									if (indexPosition < itemTaskAndCond.getListInputPossition().size() - 1) {
										indexPosition++;
									}
								}
							}
							String[] tempList = i.split(" <-> ");

							if (tempList.length > 0) {
								listPreCond.add(tempList[0]);
								for (String j : findoutClass(tempList[0])) {
									if (listArtifact.indexOf(j) == -1) {
										listArtifact.add(j);
									}
								}
							}

							//System.out.println("pass 1426");
							if (tempList.length > 1) {
								for (String j : findoutClass(tempList[1])) {
									if (listArtifact.indexOf(j) == -1) {
										listArtifact.add(j);
									}
								}
								String[] tempListCond = tempList[1].split(" /\\\\ ");

								for (String item : tempListCond) {
									listPreCond.add(item);
								}
							}

							count++;
						}
						count = 0;
						indexPosition = 0;
						for (String i : itemTaskAndCond.getListPostCond()) {
							if (itemTaskAndCond.getListOutputPosition().size() != 0) {
								if (count == itemTaskAndCond.getListOutputPosition().get(indexPosition)) {
									listPostCond.add("\\/");
									if (indexPosition < itemTaskAndCond.getListOutputPosition().size() - 1) {
										indexPosition++;
									}
								}
							}
							String[] tempList = i.split(" <-> ");

							if (tempList.length > 0) {
								listPostCond.add(tempList[0]);
								//System.out.println(tempList[0]);
								for (String j : findoutClass(tempList[0])) {
									if (listArtifact.indexOf(j) == -1) {
										listArtifact.add(j);
									}
								}
							}

							if (tempList.length > 1) {
								//System.out.println(tempList[1]);
								for (String j : findoutClass(tempList[1])) {
									//System.out.println("hello");
									if (listArtifact.indexOf(j) == -1) {
										listArtifact.add(j);
									}
								}
								//System.out.println("pass 1452");
								String[] tempListCond = tempList[1].split(" /\\\\ ");
								for (String item : tempListCond) {
									listPostCond.add(item);
								}
							}

							count++;
							//System.out.println("pass 1457");
						}

						//System.out.println("pass line 1460");

						//String artifactBlock = "<artifact>" + "\n";
						String artifactBlock = "artifact: ";
						String listArtifactToWrite = "";
						for (String itemArtifact : listArtifact) {
							//System.out.println(listArtifact);
							if (listArtifactToWrite.equals("")) {
								listArtifactToWrite += itemArtifact;
							} else {
								listArtifactToWrite += (", " + itemArtifact);

							}
						}
						//System.out.println("1482");
						//	artifactBlock += listArtifactToWrite + "\n" + ("</artifact>");
						artifactBlock += listArtifactToWrite + "\n";
						//String preCondBlock = "<precondition>" + "\n";
						Boolean useAnd = false;
						String preCondBlock = "Precondition: ";
						for (String itemPreCond : listPreCond) {
							if (preCondBlock.equals("Precondition: ") || useAnd==true) {
								preCondBlock += itemPreCond;
								useAnd=false;
							} else if (!itemPreCond.equals("\\/")){
								preCondBlock += (" /\\ " + itemPreCond);
							}
							else if(itemPreCond.equals("\\/")){
								useAnd=true;
								preCondBlock+=(" "+itemPreCond+" ");
							}

						}
						preCondBlock += "\n";

						String postCondBlock = "Postcondition: ";
						useAnd = false;
						for (String itemPostCond : listPostCond) {

							if (postCondBlock.equals("Postcondition: ") || useAnd) {
								postCondBlock += itemPostCond;
								useAnd = false;
							} else {
								postCondBlock += (" /\\ " + itemPostCond);
							}
							if (itemPostCond.equals("\\/")) {
								useAnd = true;
							}

						}
						//postCondBlock += ("</postcondition>");
						//System.out.println(postCondBlock);
						String dataToWrite = artifactBlock + "\n" + preCondBlock + "\n" + postCondBlock;

						//System.out.println(dataToWrite);

						if (file.exists()) {
							fw = new FileWriter(file, false);
							bw = new BufferedWriter(fw);

							bw.write(dataToWrite);
						}

						//System.out.println(fileName);

						//System.out.println(name);
					} catch (Exception e) {
						e.printStackTrace();

					} finally {
						try {

							if (bw != null)
								bw.close();

							if (fw != null)
								fw.close();

						} catch (IOException ex) {

							ex.printStackTrace();

						}
					}
				}

				JOptionPane.showMessageDialog(null, "Success");
			}
		});

		JButton readFromFile = new JButton("Read from file");
		readFromFile.setBounds(650, 200, 200, 30);
		prePostShow.add(readFromFile);

	}

	public List<String> findoutClass(String needToHandle) {
		List<String> listNameClass = new ArrayList<String>();
		if (needToHandle.equals(""))
			return listNameClass;
		String pattern = "[[a-z][A-Z]]*" + "[,.]";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(needToHandle);

		while (m.find()) {
			int start = m.start();
			int end = m.end() - 1;
			listNameClass.add(needToHandle.substring(start, end));
		}
		return listNameClass;
	}

	public void handleDataCond(TaskAndCond itemTaskAndCond) {
		//String test = "instate(contract,done) & instate(Flight,done)";
		String listPreCond = itemTaskAndCond.getListCondInputFromBpmn();
		String listPostCond = itemTaskAndCond.getListCondOutputFromBpmn();
		String pattern = "instate[(]" + "[[a-z][A-Z]]*" + "," + "[[a-z][A-Z]]*" + "[)]";

		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(listPreCond);
		List<String> listInstate = new ArrayList<String>();
		//System.out.println(itemTaskAndCond.getTask().getName());
		//	System.out.println(listPreCond);
		while (m.find()) {
			listInstate.add(m.group());
		}

		List<String> listClass = new ArrayList<String>();
		List<String> listState = new ArrayList<String>();

		itemTaskAndCond.clearList();

		for (String i : listInstate) {
			int positionStartClass = 8;
			int positionEndClass = i.indexOf(",");
			int postionStartState = i.indexOf(",") + 1;
			int positionEndState = i.length() - 1;

			String classItem = i.substring(positionStartClass, positionEndClass);
			listClass.add(classItem);
			String stateItem = i.substring(postionStartState, positionEndState);
			listState.add(stateItem);

		}
		//System.out.println(listClass);
		// System.out.println(listState.get(1));

		int indexClassItem = 0;
		for (String classItem : listClass) {
			for (Artifact ar : artifactList) {
				if (classItem.toLowerCase().equals(ar.getName().toLowerCase())) {
					List<ArtifactState> lstState = (List<ArtifactState>) ar.getListState();
					for (ArtifactState stateItem : lstState) {

						if (stateItem.getName().toLowerCase().equals(listState.get(indexClassItem).toLowerCase())) {
							String instate = "instate(" + classItem + "," + listState.get(indexClassItem) + ")";
							String cond = stateItem.getCondition();

							String result = instate + " <-> " + cond;
							//System.out.println("inside: "+ result);
							itemTaskAndCond.pushItemListPreCond(result);

						}
					}
					break;
				}

			}
			indexClassItem++;
		}

		m = r.matcher(listPostCond);
		listInstate.clear();
		//System.out.println(itemTaskAndCond.getTask().getName());
		//	System.out.println(listPreCond);
		while (m.find()) {
			listInstate.add(m.group());
		}

		listClass.clear();
		listState.clear();

		//itemTaskAndCond.clearList();

		for (String i : listInstate) {
			int positionStartClass = 8;
			int positionEndClass = i.indexOf(",");
			int postionStartState = i.indexOf(",") + 1;
			int positionEndState = i.length() - 1;

			String classItem = i.substring(positionStartClass, positionEndClass);
			listClass.add(classItem);
			String stateItem = i.substring(postionStartState, positionEndState);
			listState.add(stateItem);

		}
		//System.out.println(listClass);
		//System.out.println(listState);
		indexClassItem = 0;
		for (String classItem : listClass) {
			for (Artifact ar : artifactList) {
				if (classItem.toLowerCase().equals(ar.getName().toLowerCase())) {
					List<ArtifactState> lstState = (List<ArtifactState>) ar.getListState();
					for (ArtifactState stateItem : lstState) {
						if (stateItem.getName().toLowerCase().equals(listState.get(indexClassItem).toLowerCase())) {
							String instate = "instate(" + classItem + "," + listState.get(indexClassItem) + ")";
							String cond = stateItem.getCondition();

							String result = instate + " <-> " + cond;

							itemTaskAndCond.pushItemListPostCond(result);

						}
					}
				}
			}
			indexClassItem++;
		}

	}

	public void createPreCondPanel() {

		mainClassPre = new JComboBox<Artifact>();
		mainClassPre.setBounds(30, 20, 300, 30);

		createPreStateCondPanel();

		createPreAttributeCondPanel();

		tabbedPanePreCond = new JTabbedPane();
		tabbedPanePreCond.setBounds(500, 20, 400, 300);

		//System.out.println(artifactList.toArray());
		preCond = new JPanel();
		preCond.setLayout(null);

		tabbedPanePreCond.addTab("State", preState);
		tabbedPanePreCond.addTab("Attribute", preAttribute);
		preCond.add(mainClassPre);
		preCond.add(tabbedPanePreCond);
		textAreaPreCond = new JTextArea();
		//textAreaPreCond.setBounds(20,600,600,200);
		textAreaPreCond.setLineWrap(true);

		JPanel preAreaTextJP = new JPanel();
		preAreaTextJP.setLayout(null);
		preAreaTextJP.setBorder(new TitledBorder("Precondition"));
		preAreaTextJP.setBounds(30, 480, 400, 150);
		JScrollPane scrollPaneResult = new JScrollPane(textAreaPreCond);
		scrollPaneResult.setBounds(10, 30, 380, 100);
		textAreaPreCond.setEditable(false);
		preAreaTextJP.add(scrollPaneResult);
		containerPreAndPostScreen.add(preAreaTextJP);

		tabbedPanePrePostCond.add("Pre Condition", preCond);

	}

	public void createPreStateCondPanel() {

		preState = new JPanel();
		preState.setLayout(null);

		mainStatePre = new JComboBox<ArtifactState>();
		mainStatePre.setBounds(10, 10, 150, 30);

		Object[] columns = { "Name of State", "Instate" };
		//		Object[] dataTemp= artifactList.get(1).getListState().toArray();
		//Object[][] dataInput= {{1, 2}, {3, 4}, {5, 6}};

		statePreCondDTM = new DefaultTableModel(columns, 0) {
			private boolean ImInLoop = false;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0:
					return String.class;
				default:
					return Boolean.class;
				}
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				if (columnIndex == 1) {
					String textResult = textAreaPreCond.getText();
					if (!ImInLoop) {
						ImInLoop = true;
						Boolean bol = (Boolean) aValue;
						String arrayCond[] = textResult.split(" /\\\\ ");
						List<String> listCond = new ArrayList<String>(Arrays.asList(arrayCond));

						String needToremove = "";
						String needToAdd = "";
						int indexSelectedArtifact = mainClassPre.getSelectedIndex();
						Artifact selectedArtifact = artifactList.get(indexSelectedArtifact);

						for (int i = 0; i < this.getRowCount(); i++) {
							//Artifact selectedArtifact = (Artifact) mainClassPre.getSelectedItem();

							//selectedArtifactState.setUsed(false);
							//System.out.println(rowIndex);

							if (i != rowIndex && (Boolean) this.getValueAt(i, columnIndex) == true) {
								//System.out.println(bol);

								ArtifactState selectedArtifactState = (ArtifactState) selectedArtifact.getListState()
										.toArray()[i];
								needToremove = selectedArtifactState.generateCond(selectedArtifact.getName());

								if (listCond.indexOf(needToremove) != -1)
									listCond.remove(listCond.indexOf(needToremove));

								selectedArtifactState.setUsed(false);
								super.setValueAt(false, i, columnIndex);
							}
						}

						ArtifactState selectedArtifactState = (ArtifactState) selectedArtifact.getListState()
								.toArray()[rowIndex];

						selectedArtifactState.setUsed(bol);
						if (bol) {
							needToAdd = selectedArtifactState.generateCond(selectedArtifact.getName());
							listCond.add(needToAdd);

						} else {
							needToremove = selectedArtifactState.generateCond(selectedArtifact.getName());
							if (listCond.indexOf(needToremove) != -1)
								listCond.remove(listCond.indexOf(needToremove));
						}
						textResult = "";
						for (String i : listCond) {
							if (textResult.equals("")) {
								textResult += i;
							} else {
								textResult += (" /\\ " + i);
							}
						}
						//	System.out.println(bol);
						super.setValueAt(bol, rowIndex, columnIndex);

						textAreaPreCond.setText(textResult);

						ImInLoop = false;
					}

				}
			}

			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		preStateTable = new JTable();

		preStateTable.setModel(statePreCondDTM);

		//preStateTable.getColumn("Initial").setCellRenderer(new RadioButtonRenderer());

		JScrollPane scrollpaneState = new JScrollPane(preStateTable);
		scrollpaneState.setBounds(0, 0, 400, 300);
		//		System.out.println(dataTemp);

		preState.add(scrollpaneState);

	}

	public void createPreAttributeCondPanel() {
		preAttribute = new JPanel();
		preAttribute.setLayout(null);

		String column[] = { "Name of attribute", "Defined", "Undefined", "Options", "Compare" };
		attPreConDTM = new DefaultTableModel(column, 0) {
			private boolean ImInLoop = false;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 1:
					return Boolean.class;
				case 0:
					return String.class;
				case 2:
					return Boolean.class;
				case 3:
					return Boolean.class;
				case 4:
					return Object.class;
				default:
					return Boolean.class;
				}
			}

			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				if (!ImInLoop && columnIndex != 4) {
					ImInLoop = true;

					String textResult = textAreaPreCond.getText();
					String arrayCond[] = textResult.split(" /\\\\ ");
					List<String> listCond = new ArrayList<String>(Arrays.asList(arrayCond));

					String needToRemove = "";
					String needToAdd = "";
					Boolean bol = (Boolean) aValue;

					//	super.setValueAt(aValue, rowIndex, columnIndex);
					//	System.out.println(this.getColumnCount());
					for (int i = 1; i < 4; i++) {
						//Artifact selectedArtifact = (Artifact) mainClassPre.getSelectedItem();

						//selectedArtifactState.setUsed(false);
						//System.out.println(rowIndex);
						int indexSelectedArtifact = mainClassPre.getSelectedIndex();
						if (i != columnIndex && (Boolean) this.getValueAt(rowIndex, i) == true) {
							//System.out.println(bol);

							Artifact selectedArtifact = artifactList.get(indexSelectedArtifact);
							ArtifactAttribute selectedArtifactAttribute = (ArtifactAttribute) selectedArtifact
									.getListAttribute().toArray()[rowIndex];

							//selectedArtifactAttribute.setDefined(false);
							//selectedArtifactAttribute.setOptions(false);
							//selectedArtifactAttribute.setUndefined(false);
							switch (i) {

							case 2:
								//super.setValueAt(false, rowIndex, 1);
								//super.setValueAt(false, rowIndex, 3);
								needToRemove = selectedArtifactAttribute.generateUndefine(selectedArtifact.getName());
								if (listCond.indexOf(needToRemove) != -1) {
									listCond.remove(needToRemove);
								}
								selectedArtifactAttribute.setUndefined(false);
								break;
							case 3:

								selectedArtifactAttribute.setOptions(false);

								break;
							case 1:
								if (columnIndex != 3) {
									needToRemove = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
									if (listCond.indexOf(needToRemove) != -1) {
										listCond.remove(needToRemove);
									}
									selectedArtifactAttribute.setDefined(false);
								}
								//System.out.println(selectedArtifactAttribute.getDefined());

								break;
							}

						}
						if (i == columnIndex) {
							//textAreaPreCond.append("str");
							Artifact selectedArtifact = artifactList.get(indexSelectedArtifact);
							ArtifactAttribute selectedArtifactAttribute = (ArtifactAttribute) selectedArtifact
									.getListAttribute().toArray()[rowIndex];
							//System.out.println(selectedArtifactState.getName());

							switch (columnIndex) {

							case 2:

								super.setValueAt(false, rowIndex, 1);
								super.setValueAt(false, rowIndex, 3);
								selectedArtifactAttribute.setDefined(false);
								selectedArtifactAttribute.setOptions(false);
								selectedArtifactAttribute.setUndefined(bol);
								if (bol) {
									needToAdd = selectedArtifactAttribute.generateUndefine(selectedArtifact.getName());
									needToRemove = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
									//System.out.println(needToAdd);
									listCond.add(listCond.size(), needToAdd);
									if (listCond.indexOf(needToRemove) != -1) {
										listCond.remove(needToRemove);
									}

								} else {
									needToRemove = selectedArtifactAttribute
											.generateUndefine(selectedArtifact.getName());
									if (listCond.indexOf(needToRemove) != -1) {
										listCond.remove(needToRemove);
									}
								}
								break;
							case 3:

								super.setValueAt(false, rowIndex, 2);
								//selectedArtifactAttribute.setDefined(false);
								selectedArtifactAttribute.setOptions(bol);
								selectedArtifactAttribute.setUndefined(false);

								needToRemove = selectedArtifactAttribute.generateUndefine(selectedArtifact.getName());
								//System.out.println(needToRemove);
								if (listCond.indexOf(needToRemove) != -1) {
									listCond.remove(needToRemove);

								}

								break;
							case 1:
								super.setValueAt(false, rowIndex, 2);
								selectedArtifactAttribute.setDefined(bol);
								//System.out.println(selectedArtifactAttribute.getDefined());
								//selectedArtifactAttribute.setOptions(false);
								selectedArtifactAttribute.setUndefined(false);
								if (bol) {
									needToAdd = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
									needToRemove = selectedArtifactAttribute
											.generateUndefine(selectedArtifact.getName());
									listCond.add(listCond.size(), needToAdd);
									if (listCond.indexOf(needToRemove) != -1) {
										listCond.remove(needToRemove);

									}
								} else {
									needToRemove = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
									if (listCond.indexOf(needToRemove) != -1) {
										listCond.remove(needToRemove);
									}
								}
								break;
							}
							//	System.out.println(bol);
							super.setValueAt(bol, rowIndex, columnIndex);
							//System.out.println(selectedArtifactState.getUsed());
						}
						//System.out.println(selectedArtifactState.getUsed());
					}
					textResult = "";
					for (String i : listCond) {
						if (textResult.equals("")) {
							textResult += i;
						} else {
							textResult += (" /\\ " + i);
						}
					}
					textAreaPreCond.setText(textResult);
					ImInLoop = false;
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 0:

					return false;

				default:
					return true;
				}
			}
		};

		preAttJTable = new JTable(attPreConDTM);

		//preAttJTable.getColumnModel().getColumn(4).getCellRenderer(new ButtonRender());
		preAttJTable.getColumn("Compare").setCellRenderer(new ButtonRenderer());
		//Artifact ar= artifactList.get(0);

		JScrollPane scrollPane1 = new JScrollPane(preAttJTable);
		scrollPane1.setBounds(0, 0, 400, 300);
		preAttribute.add(scrollPane1);
	}

	// public void createShowResultAtt() {
	// 	JPanel showResultAttPreGroup = new JPanel();
	// 	showResultAttPreGroup.setLayout(null);
	// 	showResultAttPreGroup.setBorder(new TitledBorder("Show result attribute"));
	// 	showResultAttPreGroup.setBounds(30, 70, 450, 250);

	// 	String[] header = { "Attribute", "Operator", "Value" };
	// 	showResultAttPreDTM = new DefaultTableModel(header, 0) {
	// 		public boolean isCellEditable(int row, int column) {
	// 			return false;
	// 		}
	// 	};
	// 	showResultAttPreJT = new JTable(showResultAttPreDTM);
	// 	JScrollPane scrollPane = new JScrollPane(showResultAttPreJT);
	// 	scrollPane.setBounds(20, 30, 400, 160);
	// 	showResultAttPreGroup.add(scrollPane);

	// 	btnDeleteResultAttPre = new JButton("Delete");
	// 	btnDeleteResultAttPre.setBounds(350, 200, 70, 35);
	// 	showResultAttPreGroup.add(btnDeleteResultAttPre);

	// 	btnDeleteResultAttPre.addActionListener(new ActionListener() {
	// 		public void actionPerformed(ActionEvent arg0) {
	// 			if (showResultAttPreJT.getSelectedRow() >= 0) {
	// 				String name = (String) showResultAttPreJT.getValueAt(showResultAttPreJT.getSelectedRow(), 0);
	// 				String operator = (String) showResultAttPreJT.getValueAt(showResultAttPreJT.getSelectedRow(), 1);
	// 				String value = (String) showResultAttPreJT.getValueAt(showResultAttPreJT.getSelectedRow(), 2);
	// 				String needRemove = name + operator + value;

	// 				String textCond = textAreaPreCond.getText();
	// 				String[] arrayCond = textCond.split(" & ");
	// 				List<String> listCond = new ArrayList<String>(Arrays.asList(arrayCond));

	// 				if (listCond.indexOf(needRemove) != -1) {
	// 					listCond.remove(needRemove);
	// 				}

	// 				textCond = "";
	// 				for (String i : listCond) {
	// 					if (textCond.equals(""))
	// 						textCond += i;
	// 					else {
	// 						textCond += " & " + i;
	// 					}
	// 				}
	// 				textAreaPreCond.setText(textCond);
	// 				showResultAttPreDTM.removeRow(showResultAttPreJT.getSelectedRow());

	// 			}
	// 		}
	// 	});

	// 	btnAddResultAttPre = new JButton("Delete All");
	// 	btnAddResultAttPre.setBounds(250, 200, 70, 35);
	// 	showResultAttPreGroup.add(btnAddResultAttPre);

	// 	preCond.add(showResultAttPreGroup);

	// }

	// public void createPostAttributeCondPanel() {
	// 	postAttribute = new JPanel();
	// 	postAttribute.setLayout(null);
	// 	String column[] = { "Name of attribute", "Defined", "Undefined", "Options", "Compare" };
	// 	attPostConDTM = new DefaultTableModel(column, 0) {
	// 		@Override
	// 		public Class<?> getColumnClass(int column) {
	// 			switch (column) {
	// 			case 1:
	// 				return Boolean.class;
	// 			case 0:
	// 				return String.class;
	// 			case 2:
	// 				return Boolean.class;
	// 			case 3:
	// 				return Boolean.class;
	// 			case 4:
	// 				return Object.class;
	// 			default:
	// 				return Boolean.class;
	// 			}
	// 		}
	// 	};
	// 	postAttJTable = new JTable(attPostConDTM);
	// 	postAttJTable.getColumn("Compare").setCellRenderer(new ButtonRenderer());

	// 	JScrollPane scrollPane = new JScrollPane(postAttJTable);
	// 	scrollPane.setBounds(0, 0, 400, 300);
	// 	postAttribute.add(scrollPane);
	// }

	// Main method to  f get things started
	public static void main(String args[]) {
		// Create an instance of the test application
		MainScreen mainFrame = new MainScreen();
		mainFrame.setVisible(true);
	}
}
//hello