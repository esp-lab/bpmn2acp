package main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.DataInputAssociation;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
//end test
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import orbital.logic.imp.Formula;
import orbital.logic.sign.ParseException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class StateInterfacePrePost{
    // private List<String> 
    private JComboBox classCB;
    private List<Artifact> listTemp;
    public StateInterfacePrePost(){}
    public void test(JPanel container, List<Artifact> listArtifact){
        this.listTemp= listArtifact;
        
        classCB = new JComboBox<Artifact>();

        JButton btnTest= new JButton("test");
        btnTest.setBounds(50, 50, 150, 30);
        container.add(btnTest);
        //listArtifact.get(0).setName("name");
        btnTest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                listTemp.get(0).setName("name");
            }
        });

        
    }
    
}