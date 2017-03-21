package com.nkang.kxmoment.baseobject;

public class PlatforRelated {
	int Done_APJ;
	int Done_USA;
	int Done_MEXICO;
	int Done_EMEA;
	
	int InProgress_APJ;
	int InProgress_USA;
	int InProgress_MEXICO;
	int InProgress_EMEA;

	int InPlanning_APJ;
	int InPlanning_USA;
	int InPlanning_MEXICO;
	int InPlanning_EMEA;
	
	int unAssinged;
	int total;
	public int getDone_APJ() {
		return Done_APJ;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setDone_APJ(int done_APJ) {
		Done_APJ = done_APJ;
	}

	public int getDone_USA() {
		return Done_USA;
	}

	public void setDone_USA(int done_USA) {
		Done_USA = done_USA;
	}

	public int getDone_MEXICO() {
		return Done_MEXICO;
	}

	public void setDone_MEXICO(int done_MEXICO) {
		Done_MEXICO = done_MEXICO;
	}

	public int getDone_EMEA() {
		return Done_EMEA;
	}

	public void setDone_EMEA(int done_EMEA) {
		Done_EMEA = done_EMEA;
	}

	public int getInProgress_APJ() {
		return InProgress_APJ;
	}

	public void setInProgress_APJ(int inProgress_APJ) {
		InProgress_APJ = inProgress_APJ;
	}

	public int getInProgress_USA() {
		return InProgress_USA;
	}

	public void setInProgress_USA(int inProgress_USA) {
		InProgress_USA = inProgress_USA;
	}

	public int getInProgress_MEXICO() {
		return InProgress_MEXICO;
	}

	public void setInProgress_MEXICO(int inProgress_MEXICO) {
		InProgress_MEXICO = inProgress_MEXICO;
	}

	public int getInProgress_EMEA() {
		return InProgress_EMEA;
	}

	public void setInProgress_EMEA(int inProgress_EMEA) {
		InProgress_EMEA = inProgress_EMEA;
	}

	public int getInPlanning_APJ() {
		return InPlanning_APJ;
	}

	public void setInPlanning_APJ(int inPlanning_APJ) {
		InPlanning_APJ = inPlanning_APJ;
	}

	public int getInPlanning_USA() {
		return InPlanning_USA;
	}

	public void setInPlanning_USA(int inPlanning_USA) {
		InPlanning_USA = inPlanning_USA;
	}

	public int getInPlanning_MEXICO() {
		return InPlanning_MEXICO;
	}

	public void setInPlanning_MEXICO(int inPlanning_MEXICO) {
		InPlanning_MEXICO = inPlanning_MEXICO;
	}

	public int getInPlanning_EMEA() {
		return InPlanning_EMEA;
	}

	public void setInPlanning_EMEA(int inPlanning_EMEA) {
		InPlanning_EMEA = inPlanning_EMEA;
	}

	public int getUnAssinged() {
		return unAssinged;
	}

	public void setUnAssinged(int unAssinged) {
		this.unAssinged = unAssinged;
	}
	
	public String info(){
		return Done_APJ+" "+InProgress_APJ+" "+InPlanning_APJ;
		
	}
	
}
