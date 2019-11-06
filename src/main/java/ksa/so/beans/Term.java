package ksa.so.beans;

public class Term {

    public Term(Long termId, String termText) {
		super();
		this.termId = termId;
		this.termText = termText;
	}

	Long termId;
    String termText;


	/**
     * @return the termId
     */
    public Long getTermId() {
        return termId;
    }

    /**
     * @param termId the termId to set
     */
    public void setTermId(Long termId) {
        this.termId = termId;
    }

    /**
     * @return the termText
     */
    public String getTermText() {
        return termText;
    }

    /**
     * @param termText the termText to set
     */
    public void setTermText(String termText) {
        this.termText = termText;
    }


}