package structures;

public class ConcreteCategoryProcess {
	public CategoryProcess getCategoryProcess() {
		return cp;
	}

	public void setCategoryProcess(CategoryProcess cp) {
		this.cp = cp;
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

	private CategoryProcess cp;
	private String name;
	private String type;
	
	public ConcreteCategoryProcess(CategoryProcess cp, String name, String type)
	{
		this.cp=cp;
		this.name=name;
		this.type=type;
	}
	
}
