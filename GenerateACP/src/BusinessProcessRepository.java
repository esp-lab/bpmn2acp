import java.io.File;
import java.util.*;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.Query;
import org.camunda.bpm.model.bpmn.impl.instance.ExclusiveGatewayImpl;
import org.camunda.bpm.model.bpmn.impl.instance.GatewayImpl;
import org.camunda.bpm.model.bpmn.impl.instance.UserTaskImpl;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.type.ModelElementType;

public class BusinessProcessRepository {
	
	private String fileName;
	private String sourceFolder;
	private List<BusinessProcess> listBusinessProcesses = new ArrayList<BusinessProcess>();
	
	public BusinessProcessRepository(){}
	private BusinessProcessRepository(String _fileName, String _sourceFolder)
	{
		this.fileName = _fileName;
		this.sourceFolder = _sourceFolder;
	}
	private static BusinessProcessRepository Create(String _fileName, String _sourceFolder)
	{
		return new BusinessProcessRepository(_fileName, _sourceFolder);
	}
	
	public void GetAllBusinessProcessesFromFileBPMN(File file)
	{
		BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);

		Collection<StartEvent> tempListTask = modelInstance.getModelElementsByType(StartEvent.class);
		StartEvent[] listTask = new StartEvent[tempListTask.size()];
		tempListTask.toArray(listTask);
		SequenceFlow sequenceFlow = (SequenceFlow) modelInstance.getModelElementById("SequenceFlow_1s93cem");
		System.out.println(sequenceFlow.getSource().getId());

	}
	private static List<FlowNode> findNextTask(FlowNode flowNode)
	{
		List<FlowNode> result = new ArrayList<FlowNode>();
		List<FlowNode> lstNode = flowNode.getSucceedingNodes().list();
		for(FlowNode node : lstNode)
		{
			if(!(node instanceof Task))
			{
				List<FlowNode> listNextTaskNode = findNextTask(node);
				for(FlowNode nextTaskNode : listNextTaskNode)
				{
					result.add(nextTaskNode);
				}
			}else{
				result.add(node);
			}
		}
		return result;
	}
	private static ArrayList<BusinessProcess> dfs(FlowNode node, BpmnModelInstance model) throws Exception
	{
		ArrayList<BusinessProcess> result = new ArrayList<BusinessProcess>();
		List<FlowNode> listNextTask = findNextTask(node);
		
		if(listNextTask.size() == 0)
		{
			BusinessProcess busProcess = new BusinessProcess();
			BusinessTask busTask = new BusinessTask();
			busTask.name = node.getName();
			busProcess.addTask(busTask);
			result.add(busProcess);
		}
		else
		{
			for(FlowNode task : listNextTask)
			{
				ArrayList<BusinessProcess> processes = dfs(task, model);
				for(BusinessProcess process : processes)
				{
					BusinessTask busTask = new BusinessTask();
					busTask.name = node.getName();
					process.addTask(0, busTask);
					result.add(process);
				}
			}	
		}
		return result;
	}
	
	public static List<int[]> getListPath(File file) throws Exception
	{
		BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
		List<Task> listTask = (List<Task>)modelInstance.getModelElementsByType(Task.class);
		
		List<StartEvent> tempListTask = (List<StartEvent>)modelInstance.getModelElementsByType(StartEvent.class);
		
		ArrayList<BusinessProcess> listBusPro = dfs(tempListTask.get(0), modelInstance);
		for(BusinessProcess b : listBusPro)
		{
			b.getAllTasks().remove(0);
		}
		List<int[]> listPath = new ArrayList<int[]>();
		for(BusinessProcess b : listBusPro)
		{
			int[] listLabel = new int[b.TaskCount()];
			int index = 0;
			for(BusinessTask t: b.getAllTasks())
			{
				//System.out.println(t.name);
				for(int i = 0; i < listTask.size(); i++)
				{
					if(listTask.get(i).getName() == t.name)
					{
						listLabel[index] = i;
						++index;
					}
				}
			}
			listPath.add(listLabel);
		}
		return listPath;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File("D:/EclipseEE/project Java/TestCamunda/src/original_process.bpmn");
		BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);
		List<Task> listTask = (List<Task>)modelInstance.getModelElementsByType(Task.class);
		
		List<StartEvent> tempListTask = (List<StartEvent>)modelInstance.getModelElementsByType(StartEvent.class);
		//StartEvent[] startEvent = new StartEvent[tempListTask.size()];
		//tempListTask.toArray(startEvent);
		ArrayList<BusinessProcess> listBusPro = dfs(tempListTask.get(0), modelInstance);
		for(BusinessProcess b : listBusPro)
		{
			b.getAllTasks().remove(0);
		}
		List<int[]> listPath = new ArrayList<int[]>();
		for(BusinessProcess b : listBusPro)
		{
			int[] listLabel = new int[b.TaskCount()];
			int index = 0;
			for(BusinessTask t: b.getAllTasks())
			{
				//System.out.println(t.name);
				for(int i = 0; i < listTask.size(); i++)
				{
					if(listTask.get(i).getName() == t.name)
					{
						listLabel[index] = i;
						++index;
					}
				}
			}
			listPath.add(listLabel);
		}
		for(int i = 0; i < listTask.size(); i++)
		{
			System.out.println(listTask.get(i).getName());
		}
		for(int[] arr : listPath)
		{
			for(int i : arr)
				System.out.println(i);
		}
		//System.out.println("1224");
		
	}
}
