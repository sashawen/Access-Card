package ymss.csc.models;

import java.util.Date;

public interface AccountTransaction {
	public String getMemo();
	public Double getAccountChange();
	public Double getBalance();
	public Date getDate();
}
