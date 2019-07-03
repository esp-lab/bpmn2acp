package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import orbital.logic.sign.ParseException;

public class ReduceTaskDialog extends JDialog {
	private JPanel mainPanel;
	private BusinessTask[] tList;
	private static JLabel message = new JLabel("Please choose a Task and its part to reduce.");
	private JComboBox<BusinessTask> businessTaskCB;
	private JComboBox<String> postConditionCB;

	// constructor showTextDialog()
	public ReduceTaskDialog( BusinessTask[] iTasks,JFrame owner, boolean modality) {
		super(owner, modality);
		this.tList = iTasks;
		setTitle("Reduce Task");
		setDefaultCloseOperation(0);
		setPreferredSize(new Dimension(500, 300));
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);

		// create the panel that will be the container
		mainPanel = new JPanel();

		businessTaskCB = new JComboBox<BusinessTask>();
		for (int i = 0; i < tList.length; i++) {
			businessTaskCB.addItem(tList[i]);
		}
		businessTaskCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					postConditionCB.removeAllItems();
					for (String post : tList[businessTaskCB.getSelectedIndex()].getpost_cond()) {
						postConditionCB.addItem(post);
					}
				}
			}
		});
		postConditionCB = new JComboBox<String>();
		for (String post : tList[0].getpost_cond()) {
			postConditionCB.addItem(post);
		}
		JButton addBtn = new JButton("Remove");
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = "Are you sure to reduce \""+postConditionCB.getSelectedItem()+"\" in Task "+businessTaskCB.getSelectedItem();
				int r = JOptionPane.showConfirmDialog(ReduceTaskDialog.this, message);
				if (r == JOptionPane.YES_OPTION){
					try {
						((BusinessTask)businessTaskCB.getSelectedItem()).reducePostCond(postConditionCB.getSelectedIndex());
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					postConditionCB.removeAllItems();
					for (String post : tList[businessTaskCB.getSelectedIndex()].getpost_cond()) {
						postConditionCB.addItem(post);
					}
                }
			}
		});
		JButton doneBtn = new JButton("Done");
		doneBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				
			}
		});
		mainPanel.add(businessTaskCB);
		mainPanel.add(postConditionCB);
		mainPanel.add(addBtn);
		mainPanel.add(doneBtn);
		
		// add the components to the frame, specify placement, and arrange
		add(mainPanel);

		setLocationRelativeTo(owner);

		pack();

	} // end constructor showTextDialog()

} // end class showTextDialog
