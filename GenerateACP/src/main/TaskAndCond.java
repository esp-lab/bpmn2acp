package main;

import java.io.File;
//test
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

public class TaskAndCond {
    private Task task;
    private String preCond = "";
    private String postCond = "";
    private String listCondInputFromBpmn = "";
    private String listCondOutputFromBpmn = "";
    private List<String> listPreCond = new ArrayList<String>();
    private List<String> listPostCond = new ArrayList<String>();
    private List<Integer> listPositionInput = new ArrayList<Integer>();
    private List<Integer> listPositionOutput = new ArrayList<Integer>();

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    public void setPreCond(String cond) {
        this.preCond = cond;
    }

    public String getPreCond() {
        return this.preCond;
    }

    public void setPostCond(String cond) {
        this.postCond = cond;
    }

    public String getPostCond() {
        return this.postCond;
    }

    public void setListCondInputFromBpmn(String listCond) {
        this.listCondInputFromBpmn = listCond.toString();
        this.setListInputPosition();
    }

    public String getListCondInputFromBpmn() {
        return this.listCondInputFromBpmn;
    }

    public void setListCondOutputFromBpmn(String listCond) {
        this.listCondOutputFromBpmn = listCond.toString();
        this.setListOuputPostion();
    }

    public String getListCondOutputFromBpmn() {
        return this.listCondOutputFromBpmn;
    }

    public void pushItemListPreCond(String item) {

        this.listPreCond.add(item);

    }

    public void pushItemListPostCond(String item) {

        this.listPostCond.add(item);

    }

    public List<String> getListPreCond() {
        return this.listPreCond;
    }

    public void clearList() {
        this.listPreCond.clear();
        this.listPostCond.clear();
    }

    public List<String> getListPostCond() {
        return this.listPostCond;
    }

    public void setListInputPosition() {
        String inputBPMN = this.listCondInputFromBpmn;
        String[] itemsCondition = inputBPMN.split("\\\\/");
        for (String i : itemsCondition) {
            System.out.println(i);
        }
        String pattern = "instate";
        Pattern r = Pattern.compile(pattern);
        for (String item : itemsCondition) {
            Matcher m = r.matcher(item);
            int count = 0;
            while (m.find()) {
                count++;
            }

            int length = this.listPositionInput.size();
            if (length == 0) {
                this.listPositionInput.add(count);
            } else {
                this.listPositionInput.add(this.listPositionInput.get(length - 1) + count);
            }

        }
        this.listPositionInput.remove(this.listPositionInput.size() - 1);

    }

    public List<Integer> getListInputPossition() {
        return this.listPositionInput;
    }

    public void setListOuputPostion() {
        String outputBPMN = this.listCondOutputFromBpmn;
        String[] itemsCondition = outputBPMN.split("\\\\/");
        for (String i : itemsCondition) {
            System.out.println(i);
        }
        String pattern = "instate";
        Pattern r = Pattern.compile(pattern);
        for (String item : itemsCondition) {
            Matcher m = r.matcher(item);
            int count = 0;
            while (m.find()) {
                count++;
            }

            int length = this.listPositionOutput.size();
            if (length == 0) {
                this.listPositionOutput.add(count);
            } else {
                this.listPositionOutput.add(this.listPositionOutput.get(length - 1) + count);
            }

        }
        this.listPositionOutput.remove(this.listPositionOutput.size() - 1);
    }

    public List<Integer> getListOutputPosition() {
        return this.listPositionOutput;
    }

}
