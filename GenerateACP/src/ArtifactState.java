import com.sun.xml.internal.bind.v2.runtime.Name;

public class ArtifactState {
	private String Name;

	public String toString() {

		return Name;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArtifactState(String name) {
		this.Name = name;
	}
}
