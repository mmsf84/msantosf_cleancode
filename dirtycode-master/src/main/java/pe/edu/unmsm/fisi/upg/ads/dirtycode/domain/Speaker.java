package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

import java.util.Arrays;
import java.util.List;

import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private final int MINCERTIFICADOS = 3;
	private final int ANHOSEXPERIENCIA = 10;
	private final String ARROBAMAILSYMBOL = "@";
	private final int BROWSERIEVERSION = 9;
	private String firstName;
	private String lastName;
	private String email;
	private int exp;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;
	
	public enum Empleadores{
		PLURAL("Pluralsight"), MICROSOFT("Microsoft"), GOOGLE("Google"), 
		FOG("Fog Creek Software"), SIGNAL("37Signals"), TELERIK("Telerik");
		private String value;
		private Empleadores(String value) {
			this.value = value;
		}
		public static boolean contiene(String value){
	        return (Empleadores.valueOf(value)!=null?true:false);
	    }
	}
	
	public enum DominiosAceptados{
		AOL("aol.com"), HOTMAIL("hotmail.com"), PRODIGY("prodigy.com"), COMPUSERVE("compuserve.com");
		private String value;
		private DominiosAceptados(String value) {
			this.value = value;
		}
		public static boolean contiene(String value){
	        return (DominiosAceptados.valueOf(value)!=null?true:false);
	    }
	}
	
	public enum LenguajesProg{
		COBOL("Cobol"), PUNCH("Punch Cards"), COMMODORE("Commodore"), VB("VBScript");
		private String value;
		private LenguajesProg(String value) {
			this.value = value;
		}
		public static boolean contiene(String value){
	        return (LenguajesProg.valueOf(value)!=null?true:false);
	    }
	}
	
	private int[][] rangosPuntaje = {{0,1,500},{2,3,250},{4,5,100},{6,9,50}};	
	
	public Integer register(IRepository repository) throws Exception {
		Integer speakerId = null;		
		boolean aprobado = false;
		
		validaReqMinimo();
		validaStdCumplido();					
		
		for (Session session : this.sessions) {
			for (LenguajesProg tech : LenguajesProg.values()) {
				if (session.getTitle().contains(tech.value) || session.getDescription().contains(tech.value)) {
					session.setApproved(false);
					break;
				} else {
					session.setApproved(true);
					aprobado = true;
				}
			}
		}
	
		if (!aprobado) {
			throw new NoSessionsApprovedException("No sessions approved.");
		}
										
		for(int i=0; i<rangosPuntaje.length; i++) {
			if(this.exp >= rangosPuntaje[i][0] && this.exp <= rangosPuntaje[i][1]) {
				this.registrationFee = rangosPuntaje[i][2];
			}
		}
						
		speakerId = repository.saveSpeaker(this);
		
		return speakerId;
	}
	
	public void validaReqMinimo() {
		if (this.firstName.isEmpty()) {
			throw new IllegalArgumentException("First Name is required");
		}
		if (this.lastName.isEmpty()) {
			throw new IllegalArgumentException("Last name is required.");
		}
		if (this.email.isEmpty()) {
			throw new IllegalArgumentException("Email is required.");
		}
		if (this.sessions.size() == 0) {
			throw new IllegalArgumentException("Can't register speaker with no sessions to present.");
		}
	}
	
	public void validaStdCumplido() throws Exception {
		boolean cumpleEstandares = esExperto();
				
		if (!cumpleEstandares) {
			if (aceptaCorreo()){
				cumpleEstandares = true;
			}
		}
					
		if (!cumpleEstandares) {
			throw new SpeakerDoesntMeetRequirementsException("Speaker doesn't meet our abitrary and capricious standards.");
		}
	}
	
	public boolean esExperto() {		
		boolean experiencia = this.exp > this.ANHOSEXPERIENCIA;
		boolean cetificados = this.certifications.size() > this.MINCERTIFICADOS; 
		boolean empresaTop = Empleadores.contiene(this.employer);
		
		return (experiencia || this.hasBlog || cetificados || empresaTop);
	}
	
	public boolean aceptaCorreo() {		
		String[] splitted = this.email.split(this.ARROBAMAILSYMBOL);
		String emailDomain = splitted[splitted.length - 1];
		boolean correoFiltro = DominiosAceptados.contiene(emailDomain);
		boolean browserIE = browser.getName() == WebBrowser.BrowserName.InternetExplorer;
		boolean versionIE = browser.getMajorVersion() < this.BROWSERIEVERSION;
	
		return (!correoFiltro && (!(browserIE && versionIE)));
	}
	
	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
}