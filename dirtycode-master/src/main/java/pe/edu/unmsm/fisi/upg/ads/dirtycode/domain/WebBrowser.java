package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

public class WebBrowser {
	private BrowserName name;
	private int majorVersion;

	public WebBrowser(String name, int majorVersion) {
		this.name = TranslateStringToBrowserName(name);
		this.majorVersion = majorVersion;
	}

	private BrowserName TranslateStringToBrowserName(String name) {
		if (name.contains("IE")) {
			return BrowserName.InternetExplorer;
		}
		// TODO: Add more logic for properly sniffing for other browsers.
		return BrowserName.Unknown;
	}

	public enum BrowserName {
		Unknown, InternetExplorer, Firefox, Chrome, Opera, Safari, Dolphin, Konqueror, Linx
	}

	public BrowserName getName() {
		return name;
	}

	public void setName(BrowserName name) {
		this.name = name;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

}