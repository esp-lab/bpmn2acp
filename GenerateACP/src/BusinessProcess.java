import java.io.File;
import java.util.Collection;
import java.util.*;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.type.ModelElementType;

public class BusinessProcess {
	private List<BusinessTask> listBusinessTask = new ArrayList<BusinessTask>();

	public void addTask(BusinessTask _task) {
		this.listBusinessTask.add(_task);
	}

	public void addTask(int index, BusinessTask _task) {
		this.listBusinessTask.add(index, _task);
	}

	public List<BusinessTask> getAllTasks() {
		return this.listBusinessTask;
	}

	public int TaskCount() {
		return listBusinessTask.size();
	}

	public void CreateFromStartEvent() {

	}
}
