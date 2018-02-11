package model;

public class Criteria {
	private boolean[] provided;
	
	private Integer month;
	private String category;
	private Float lessThan;
	private Float moreThan;
	
	public Criteria(){
		this.provided = new boolean[4];
		for(int i=0;i<provided.length;i++){
			this.provided[i] = false;
		}
		this.month = null;
		this.category = null;
		this.lessThan = null;
		this.moreThan = null;
	}

	public boolean getProvided(int i){
		return provided[i];
	}
	
	public boolean[] getProvided(){
		return provided;
	}
	
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		if(month == null){
			provided[0] = false;
		}
		else{
			provided[0] = true;
		}
		this.month = month;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if(category == null){
			provided[1] = false;
		}
		else{
			provided[1] = true;
		}
		this.category = category;
	}

	public Float getLessThan() {
		return lessThan;
	}

	public void setLessThan(Float lessThan) {
		if(lessThan == null){
			provided[2] = false;
		}
		else{
			provided[2] = true;
		}
		this.lessThan = lessThan;
	}

	public Float getMoreThan() {
		return moreThan;
	}

	public void setMoreThan(Float moreThan) {
		if(moreThan == null){
			provided[3] = false;
		}
		else{
			provided[3] = true;
		}
		this.moreThan = moreThan;
	}
	
}
