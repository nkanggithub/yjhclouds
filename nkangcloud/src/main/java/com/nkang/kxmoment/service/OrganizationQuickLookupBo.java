package com.nkang.kxmoment.service;

public class OrganizationQuickLookupBo {

	private String orgId;
	private String orgName;
    private String accountName;
    private String nonLatinName;

    private String isUseContains;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

    /**
     * @return the isUseContains
     */
    public String getIsUseContains() {
        return isUseContains;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the nonLatinName
     */
    public String getNonLatinName() {
        return nonLatinName;
    }

    /**
     * @param nonLatinName
     *            the nonLatinName to set
     */
    public void setNonLatinName(String nonLatinName) {
        this.nonLatinName = nonLatinName;
    }

    /**
     * @param isUseContains
     *            the isUseContains to set
     */
    public void setIsUseContains(String isUseContains) {
        this.isUseContains = isUseContains;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrganizationQuickLookupBo other = (OrganizationQuickLookupBo) obj;
		if (orgId == null) {
			if (other.orgId != null) {
				return false;
			}
		} else if (!orgId.equals(other.orgId)) {
			return false;
		}
		if (orgName == null) {
			if (other.orgName != null) {
				return false;
			}
		} else if (!orgName.equals(other.orgName)) {
			return false;
		}
		return true;
	}

}