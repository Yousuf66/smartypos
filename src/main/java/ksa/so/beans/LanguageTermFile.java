package ksa.so.beans;
import java.util.List;

public class LanguageTermFile {
	int id;
	List<Term> term;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Term> getTerm() {
		return term;
	}
	public void setTerm(List<Term> term) {
		this.term = term;
	}
	
	
	
}