
public class ArtifactAttribute {
	
	public String Name;
	public String DataType;
	public String toString(){
		return Name;
	}
	public ArtifactAttribute()
	{	
	}
	
	public ArtifactAttribute(String name, String dataType)
	{
		this.Name = name;
		this.DataType = dataType;
	}
}
