package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.rmi.activation.ActivationDesc;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import java.util.List;

//import javafx.event.ActionEvent;
import javafx.scene.layout.Border;

public class AttributeInputPrePostCond {
    private JFrame frame;
    private JPanel topPanel;
    private Artifact selectedArtifact;
    private JTable showStateTB;
    private JComboBox mainClassCB;
    private List<Artifact> listArtifact;
    private JPanel attributeJP;
    private JTable attributeJT;
    private DefaultTableModel attCondDTM;
    private JTextArea showCondJTA;
    private DefaultTableModel showResultAttDTM;
    private JTable showResultAttJT;
    private ArtifactState selectedState;

    private JTextArea jtShowPreCond;
    private JTextArea jtShowPostCond;
    private JTextArea jtShowEachCond;

    private JComboBox businessTaskCB;

    public AttributeInputPrePostCond() {

    }

    public void createWindow(List<Artifact> artifactList, Artifact selectedArtifact1, JTable showStatePara,
            JTextArea jtShowPreCondPara, JTextArea jtShowPostCondPara, JTextArea jtShowEachCondPara,
            JComboBox businessTaskCBPara) {
        this.selectedArtifact = selectedArtifact1;
        this.listArtifact = artifactList;
        this.showStateTB = showStatePara;
        this.jtShowPreCond = jtShowPreCondPara;
        this.jtShowPostCond = jtShowPostCondPara;
        this.jtShowEachCond = jtShowEachCondPara;
        this.businessTaskCB = businessTaskCBPara;

        frame = new JFrame();
        frame.setTitle("Relation between state and attribute");
        frame.setSize(800, 600);
        topPanel = new JPanel();
        topPanel.setLayout(null);

        //label component
        int positionOfRow = showStateTB.getSelectedRow();
        List<ArtifactState> listState = new ArrayList(selectedArtifact.getListState());

        this.selectedState = listState.get(positionOfRow);
        String textLabel = selectedState.getName();

        JLabel label = new JLabel("instate(" + selectedArtifact.getName() + "," + textLabel + ")");

        //selectedState.setName("name");

        label.setBounds(300, 10, 200, 30);
        JTextAreaCond();
        JPanelShowResultAtt();
        JPanelAttributeComponent();
        initialDataDefineAndUndefined();
        JComboboxComponent();
        

        topPanel.add(label);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(230, 520, 150, 30);
        topPanel.add(btnSave);

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg) {

                selectedState.clearListAttCompare();
                selectedState.clearList();
                for (int i = 0; i < showResultAttJT.getRowCount(); i++) {
                    ArtifactAttributeCompare aAC = new ArtifactAttributeCompare();
                    String lhs = showResultAttJT.getValueAt(i, 0).toString();
                    aAC.setLeftHandSide(lhs);
                    aAC.setOperator(showResultAttJT.getValueAt(i, 1).toString());
                    aAC.setRightHandSide(showResultAttJT.getValueAt(i, 2).toString());

                    selectedState.pushListAttCompare(aAC);

                }

                for (Artifact i : listArtifact) {
                    for (ArtifactAttribute n : i.getListAttribute()) {
                        if (n.getDefined()) {

                            String toAdd = n.generateDefine(i.getName());
                            selectedState.pushItemDefinedList(toAdd);
                        }
                        if (n.getUndefined()) {
                            String toAdd = n.generateUndefine(i.getName());
                            selectedState.pushItemUndefinedList(toAdd);
                        }
                    }
                }

                //System.out.println()
                jtShowEachCond.setText(selectedState.getCondition());
                selectedState.setCondition(showCondJTA.getText());
                try {
                    int current = businessTaskCB.getSelectedIndex();
                    businessTaskCB.setSelectedIndex(businessTaskCB.getComponentCount() - 1);
                    businessTaskCB.setSelectedIndex(current);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Your file BPMN doesn't have any task.");
                }

                // String preCond = "";
                // for (String i : selectedState.get) {
                //     //System.out.println(i+3);
                //     preCond += "\n" + i;

                // }
                // String postCond = "";
                // for (String i : itemTaskAndCond.getListPostCond()) {
                //     postCond += "\n" + i;
                // }
                // jtPreCondShow.setText(preCond);
                // jtPostCondShow.setText(postCond);

                //System.out.println(selectedState.getListAttCompare().size());
                frame.setVisible(false);
                //selectedState.pushListAttCompare();
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(400, 520, 150, 30);
        topPanel.add(btnCancel);

        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg) {
                jtShowEachCond.setText(selectedState.getCondition());
                frame.setVisible(false);
            }

        });
        frame.getContentPane().add(topPanel);
        frame.setVisible(true);

    }

    public void JComboboxComponent() {
        mainClassCB = new JComboBox<Artifact>();
        mainClassCB.setBounds(30, 50, 200, 30);
        //mainClassCB.removeAllItems();

        mainClassCB.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    //System.out.println("hello");
                    Artifact selectedArtifact = (Artifact) mainClassCB.getSelectedItem();

                    List<ArtifactAttribute> listAtt = new ArrayList(selectedArtifact.getListAttribute());
                    //System.out.println(attCondDTM.getRowCount());
                    for (int i = attCondDTM.getRowCount() - 1; i >= 0; i--) {
                        attCondDTM.removeRow(i);
                    }

                    for (ArtifactAttribute i : listAtt) {
                        attCondDTM.addRow(
                                new Object[] { i.toString(), i.getDefined(), i.getUndefined(), i.getOptions(), "Add" });
                    }
                    attributeJT.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(),
                            listArtifact, selectedArtifact, showResultAttDTM, showCondJTA));

                    //For attribute show

                }
            }

        });

        for (Artifact i : listArtifact) {
            mainClassCB.addItem(i);
        }

        //mainClassCB.getAction();
        //mainClassCB.fire

        topPanel.add(mainClassCB);
    }

    public void JPanelAttributeComponent() {
        attributeJP = new JPanel();
        attributeJP.setBounds(380, 50, 350, 280);
        attributeJP.setLayout(null);

        String column[] = { "Name of attribute", "Defined", "Undefined", "Options", "Compare" };
        attCondDTM = new DefaultTableModel(column, 0) {
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

                    String textResult = showCondJTA.getText();
                    // textAreaPreCond.getText();
                    String arrayCond[] = textResult.split(" /\\\\ ");
                    List<String> listCond = new ArrayList<String>(Arrays.asList(arrayCond));

                    String needToRemove = "";
                    String needToAdd = "";
                    Boolean bol = (Boolean) aValue;

                    for (int i = 1; i < 4; i++) {

                        int indexSelectedArtifact = mainClassCB.getSelectedIndex();
                        if (i != columnIndex && (Boolean) this.getValueAt(rowIndex, i) == true) {

                            Artifact selectedArtifact = listArtifact.get(indexSelectedArtifact);
                            ArtifactAttribute selectedArtifactAttribute = (ArtifactAttribute) selectedArtifact
                                    .getListAttribute().toArray()[rowIndex];

                            switch (i) {

                            case 2:

                                needToRemove = selectedArtifactAttribute.generateUndefine(selectedArtifact.getName());
                                if (listCond.indexOf(needToRemove) != -1) {
                                    listCond.remove(needToRemove);
                                }
                                // selectedState.removeItemUndefinedList(needToRemove);

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
                                    //  selectedState.removeItemDefinedList(needToRemove);
                                    selectedArtifactAttribute.setDefined(false);
                                }
                                //System.out.println(selectedArtifactAttribute.getDefined());

                                break;
                            }

                        }
                        if (i == columnIndex) {
                            //textAreaPreCond.append("str");
                            Artifact selectedArtifact = listArtifact.get(indexSelectedArtifact);
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
                                    if (listCond.indexOf(needToAdd) == -1) {
                                        listCond.add(needToAdd);
                                    } // selectedState.removeItemDefinedList(needToRemove);
                                      //selectedState.pushItemUndefinedList(needToAdd);

                                    if (listCond.indexOf(needToRemove) != -1) {
                                        listCond.remove(needToRemove);
                                    }

                                } else {
                                    needToRemove = selectedArtifactAttribute
                                            .generateUndefine(selectedArtifact.getName());
                                    if (listCond.indexOf(needToRemove) != -1) {
                                        listCond.remove(needToRemove);
                                    }
                                    //selectedState.removeItemUndefinedList(needToRemove);
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
                                // selectedState.removeItemUndefinedList(needToRemove);

                                break;
                            case 1:
                                super.setValueAt(false, rowIndex, 2);
                                selectedArtifactAttribute.setDefined(bol);

                                selectedArtifactAttribute.setUndefined(false);
                                if (bol) {
                                    needToAdd = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
                                    needToRemove = selectedArtifactAttribute
                                            .generateUndefine(selectedArtifact.getName());
                                    if (listCond.indexOf(needToAdd) == -1) {
                                        listCond.add(needToAdd);
                                    }
                                    if (listCond.indexOf(needToRemove) != -1) {
                                        listCond.remove(needToRemove);

                                    }
                                    // selectedState.pushItemDefinedList(needToAdd);
                                    // selectedState.removeItemUndefinedList(needToRemove);
                                } else {
                                    needToRemove = selectedArtifactAttribute.generateDefine(selectedArtifact.getName());
                                    if (listCond.indexOf(needToRemove) != -1) {
                                        listCond.remove(needToRemove);
                                    }
                                    // selectedState.removeItemDefinedList(needToRemove);
                                }
                                break;
                            }

                            super.setValueAt(bol, rowIndex, columnIndex);

                        }

                    }
                    textResult = "";
                    for (String i : listCond) {
                        if (textResult.equals("")) {
                            textResult += i;
                        } else {
                            textResult += (" /\\ " + i);
                        }
                    }
                    showCondJTA.setText(textResult);
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

        attributeJT = new JTable(attCondDTM);
        //attCondDTM.setValueAt(true, 0, 0);
        //System.out.println(selectedState.getListDefined());
        attributeJT.getColumn("Compare").setCellRenderer(new ButtonRenderer());
        JScrollPane scrollPanel = new JScrollPane(attributeJT);
        scrollPanel.setBounds(0, 0, 350, 280);
        attributeJP.add(scrollPanel);
        topPanel.add(attributeJP);

    }

    public void JTextAreaCond() {
        JPanel showCondJP = new JPanel();
        showCondJP.setLayout(null);
        showCondJP.setBorder(new TitledBorder("Condition"));
        showCondJP.setBounds(30, 350, 730, 150);

        showCondJTA = new JTextArea();
        showCondJTA.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(showCondJTA);

        scrollPane.setBounds(10, 30, 710, 100);

        showCondJTA.setEditable(false);

        showCondJP.add(scrollPane);

        String resultJTA = selectedState.getCondition();

        showCondJTA.setText(resultJTA);
        topPanel.add(showCondJP);

    }

    public void JPanelShowResultAtt() {
        JPanel showResultAttJP = new JPanel();
        showResultAttJP.setLayout(null);
        showResultAttJP.setBorder(new TitledBorder("Show result attribute"));
        showResultAttJP.setBounds(30, 80, 330, 250);

        String[] header = { "Attribute", "Operator", "Value" };
        showResultAttDTM = new DefaultTableModel(header, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        showResultAttJT = new JTable(showResultAttDTM);
        JScrollPane scrollPane = new JScrollPane(showResultAttJT);
        scrollPane.setBounds(20, 30, 280, 160);
        showResultAttJP.add(scrollPane);

        JButton btnDeleteResultAtt = new JButton("Delete");
        btnDeleteResultAtt.setBounds(150, 200, 70, 35);
        showResultAttJP.add(btnDeleteResultAtt);

        btnDeleteResultAtt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (showResultAttJT.getSelectedRow() >= 0) {
                    String name = (String) showResultAttJT.getValueAt(showResultAttJT.getSelectedRow(), 0);
                    String operator = (String) showResultAttJT.getValueAt(showResultAttJT.getSelectedRow(), 1);
                    String value = (String) showResultAttJT.getValueAt(showResultAttJT.getSelectedRow(), 2);
                    String needRemove = name + operator + value;
                    //System.out.println(name+1);
                    //System.out.println(needRemove);
                    String textCond = showCondJTA.getText();

                    //System.out.println(textCond);
                    String[] arrayCond = textCond.split(" /\\\\ ");
                    //System.out.println(arrayCond[0]);
                    List<String> listCond = new ArrayList<String>(Arrays.asList(arrayCond));
                    //System.out.println(listCond);
                    if (listCond.indexOf(needRemove) != -1) {
                        listCond.remove(needRemove);
                    }

                    textCond = "";
                    for (String i : listCond) {
                        if (textCond.equals(""))
                            textCond += i;
                        else {
                            textCond += " /\\ " + i;
                        }
                    }
                    showCondJTA.setText(textCond);
                    showResultAttDTM.removeRow(showResultAttJT.getSelectedRow());

                }
            }
        });

        for (ArtifactAttributeCompare i : selectedState.getListAttCompare()) {
           // System.out.println(i.getOperator() + 3);
            showResultAttDTM.addRow(new Object[] { i.getLeftHandSide(), i.getOperator(), i.getRightHandSide() });
        }

        topPanel.add(showResultAttJP);

    }

    public void initialDataDefineAndUndefined() {
       // System.out.println(selectedState.getListDefined());
        //System.out.println(selectedState.getListUndefined());

        for(Artifact ar:listArtifact){
            for(ArtifactAttribute itemAtt: ar.getListAttribute()){


                String defined= itemAtt.generateDefine(ar.getName());
                if(selectedState.getListDefined().indexOf(defined)!=-1){
                    itemAtt.setDefined(true);
                }
                
                String undefined= itemAtt.generateUndefine(ar.getName());
                if(selectedState.getListUndefined().indexOf(undefined)!=-1){
                    itemAtt.setUndefined(true);
                }
                
            }
        }
    }

    public static void main(String[] args) {

    }
}