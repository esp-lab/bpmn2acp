package main;

import com.sun.org.apache.xml.internal.utils.AttList;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp1
 */
class ButtonEditorStateCond extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    // private boolean isActive;
    private InputAttribute inputAttribute;
    private Artifact selectedArtifact;
    private JTextArea textArea;
    private DefaultTableModel condDTM;
    private List<Artifact> artifactList;
    private JTable showStateTB;
    private JTextArea jtShowPreCond;
    private JTextArea jtShowPostCond;
    private JTextArea jtShowEachCond;
    private JComboBox businessTaskCB;

    // private int a;

    public ButtonEditorStateCond(JTextField jTextField, List<Artifact> artifacList1, Artifact selectedArtifactPara,
            JTable showStateTBPara, JTextArea jtShowPreCondPara, JTextArea jtShowPostCondPara,
            JTextArea jtShowEachCondPara, JComboBox businessTaskCBPara) {
        super(jTextField);
        this.artifactList = artifacList1;
        this.selectedArtifact = selectedArtifactPara;
        this.showStateTB = showStateTBPara;
        this.jtShowPreCond = jtShowPreCondPara;
        this.jtShowPostCond = jtShowPostCondPara;
        this.jtShowEachCond = jtShowEachCondPara;
        this.businessTaskCB = businessTaskCBPara;

        //  this.a=a;
        button = new JButton();
        button.setOpaque(true);
        //this.isActive= isActive;
        //System.out.println(artifactList.size());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                for (Artifact i : artifactList) {
                    i.resetAttChoice();
                }

                AttributeInputPrePostCond a = new AttributeInputPrePostCond();
                a.createWindow(artifactList, selectedArtifact, showStateTB, jtShowPreCond, jtShowPostCond,
                        jtShowEachCond,businessTaskCB);

                // jtShowPreCond.getText();
                //jtShowPostCond.getText();

                //System.out.println(selectedState.getListDefined());
                // jtShowEachCond.setText(selectedState.getCondition());
                //inputAttribute = new InputAttribute(new List<Artifact>, parentModel, resultTextArea);
                // System.out.println("hello 2");
                //System.out.println();
            }

        });
    }

    public boolean isCellEditable(EventObject anEvent) {

        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
            //  System.out.println(row);
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }

        label = (value == null) ? "" : value.toString();

        // System.out.println(table.getValueAt(row, column));
        // System.out.println("hello");

        button.getAction();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // System.out.println(a);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }
}
