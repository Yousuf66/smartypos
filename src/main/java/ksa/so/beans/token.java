package ksa.so.beans;

import java.util.Date;
import java.util.List;

public class token {
	
	
	private Long UserId;
	private String TokenString;
	private Date Login;
	private boolean AuthStat;
	private String FullUsername;
	private String RefreshTokenString;
	private List<idTitle> branchList;


	public List<idTitle> getBranchList() {
		return branchList;
	}
	public void setBranchList(List<idTitle> branchList) {
		this.branchList = branchList;
	}
	public boolean isAuthStat() {
		return AuthStat;
	}
	public void setAuthStat(boolean authStat) {
		AuthStat = authStat;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public String getTokenString() {
		return TokenString;
	}
	public void setTokenString(String tokenString) {
		TokenString = tokenString;
	}
	@Override
	public String toString() {
		return "token [UserId=" + UserId + ", TokenString=" + TokenString + ", Login=" + Login + ", AuthStat="
				+ AuthStat + ", FullUsername=" + FullUsername + ", RefreshTokenString=" + RefreshTokenString + "]";
	}
	public Date getLogin() {
		return Login;
	}
	public void setLogin(Date login) {
		Login = login;
	}

public String getRefreshTokenString() {
	return RefreshTokenString;
}
public void setRefreshTokenString(String refreshTokenString) {
	RefreshTokenString = refreshTokenString;
}
public String getFullUsername() {
	return FullUsername;
}
public void setFullUsername(String fullUsername) {
	FullUsername = fullUsername;
}

}
