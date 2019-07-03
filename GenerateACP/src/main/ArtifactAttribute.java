package main;

public class ArtifactAttribute {
	
	public String Name;
	public String DataType;
	private Boolean Defined=false;
	private Boolean Undefined=false;
	private Boolean Options=false;

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

	public Boolean getDefined(){
		return Defined;
	}
	public Boolean getUndefined(){
		return Undefined;
	}

	public Boolean getOptions(){
		return Options;
	}

	public void setDefined(boolean defined){
		this.Defined= defined;
	}
	
	public void setUndefined(boolean undefined){
		this.Undefined= undefined;
	}

	public void setOptions(boolean options){
		this.Options= options;
	}

	public String generateDefine(String nameClass){
		String result= "define("+nameClass+"."+this.Name+")";
		return result;
	}
	public String generateUndefine(String nameClass){
		String result= "undefine("+nameClass+"."+this.Name+")";
		return result;
	}


}
