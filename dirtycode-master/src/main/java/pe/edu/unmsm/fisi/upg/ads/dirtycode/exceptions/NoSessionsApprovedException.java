package pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions;

import java.io.Serializable;

public class NoSessionsApprovedException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public NoSessionsApprovedException(String message) {
		super(message);

	}
}