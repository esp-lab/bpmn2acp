package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp1
 */
class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    // private boolean isActive;
    private InputAttribute inputAttribute;
    private Artifact artifact;
    private JTextArea textArea;
    private DefaultTableModel condDTM;
    private List<Artifact> artifactList;
    // private int a;

    public ButtonEditor(JCheckBox checkBox, List<Artifact> artifactsList, Artifact artifact, DefaultTableModel condDTM,
            JTextArea textArea) {
        super(checkBox);

        this.artifact = artifact;
        this.textArea = textArea;
        this.condDTM = condDTM;
        this.artifactList = artifactsList;
        //  this.a=a;
        button = new JButton();
        button.setOpaque(true);
        //this.isActive= isActive;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                //inputAttribute = new InputAttribute(new List<Artifact>, parentModel, resultTextArea);
                //System.out.println("hello 2");
                // System.out.println();
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
        List<Artifact> tempList = Arrays.asList(this.artifact);
       
        if ((Boolean) table.getValueAt(row, 3) == true) {
            InputAttribute1 inputAtt = new InputAttribute1(this.artifactList, tempList, this.condDTM, this.textArea);
            inputAtt.run();
        } else {
            JOptionPane.showMessageDialog(null, "Please choose options to countinue.",
							"Warning", JOptionPane.WARNING_MESSAGE);
        }
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
