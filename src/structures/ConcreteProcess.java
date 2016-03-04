package structures;

public class ConcreteProcess {
	public Process getProcess() {
		return p;
	}

	public void setProcess(Process p) {
		this.p = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Process p;
	private String name;
	private String type;
	
	public ConcreteProcess(Process p, String name, String type)
	{
		this.p=p;
		this.name=name;
		this.type=type;
	}
}
