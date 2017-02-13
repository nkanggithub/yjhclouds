package com.nkang.kxmoment.baseobject;

public class Role {
	private boolean isExternalUpStream=false;
	private boolean isExternalCustomer=false;
	private boolean isExternalPartner=false;
	private boolean isExternalCompetitor=false;

	private boolean isInternalSeniorMgt=false;
	private boolean isInternalImtMgt=false;
	private boolean isInternalBizEmp=false;
	private boolean isInternalNonBizEmp=false;
	private boolean isInternalQuoter=false;

	private boolean isITOperations=false;

	public boolean isExternalUpStream() {
		return isExternalUpStream;
	}

	public void setExternalUpStream(boolean isExternalUpStream) {
		this.isExternalUpStream = isExternalUpStream;
	}

	public boolean isExternalCustomer() {
		return isExternalCustomer;
	}

	public void setExternalCustomer(boolean isExternalCustomer) {
		this.isExternalCustomer = isExternalCustomer;
	}

	public boolean isExternalPartner() {
		return isExternalPartner;
	}

	public void setExternalPartner(boolean isExternalPartner) {
		this.isExternalPartner = isExternalPartner;
	}

	public boolean isExternalCompetitor() {
		return isExternalCompetitor;
	}

	public void setExternalCompetitor(boolean isExternalCompetitor) {
		this.isExternalCompetitor = isExternalCompetitor;
	}

	public boolean isInternalSeniorMgt() {
		return isInternalSeniorMgt;
	}

	public void setInternalSeniorMgt(boolean isInternalSeniorMgt) {
		this.isInternalSeniorMgt = isInternalSeniorMgt;
	}

	public boolean isInternalImtMgt() {
		return isInternalImtMgt;
	}

	public void setInternalImtMgt(boolean isInternalImtMgt) {
		this.isInternalImtMgt = isInternalImtMgt;
	}

	public boolean isInternalBizEmp() {
		return isInternalBizEmp;
	}

	public void setInternalBizEmp(boolean isInternalBizEmp) {
		this.isInternalBizEmp = isInternalBizEmp;
	}

	public boolean isInternalNonBizEmp() {
		return isInternalNonBizEmp;
	}

	public void setInternalNonBizEmp(boolean isInternalNonBizEmp) {
		this.isInternalNonBizEmp = isInternalNonBizEmp;
	}

	public boolean isInternalQuoter() {
		return isInternalQuoter;
	}

	public void setInternalQuoter(boolean isInternalQuoter) {
		this.isInternalQuoter = isInternalQuoter;
	}

	public boolean isITOperations() {
		return isITOperations;
	}

	public void setITOperations(boolean isItOperations) {
		this.isITOperations = isItOperations;
	}
}
