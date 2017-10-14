package pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions;

import java.io.Serializable;

public class SpeakerDoesntMeetRequirementsException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public SpeakerDoesntMeetRequirementsException(String message) {
		super(message);

	}

	public SpeakerDoesntMeetRequirementsException(String format, Object[] args) {
		super(String.format(format, args));
	}
}