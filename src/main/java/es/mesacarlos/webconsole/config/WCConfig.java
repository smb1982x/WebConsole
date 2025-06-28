package es.mesacarlos.webconsole.config;

import java.net.InetSocketAddress;
import java.util.*;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import es.mesacarlos.webconsole.WebConsole;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = WebConsole.MODID)
public class WCConfig implements ConfigData {
	private static WCConfig instance;

	public static WCConfig getInstance() {
		if(instance == null) {
			instance = AutoConfig.getConfigHolder(WCConfig.class).getConfig();
		}
		return instance;
	}

	@ConfigEntry.Category("SSL")
	public boolean useSSL = false;
	@ConfigEntry.Category("SSL")
	public String StoreType = "JKS";
	@ConfigEntry.Category("SSL")
	public String KeyStore = "plugins/WebConsole/keystore.jks";
	@ConfigEntry.Category("SSL")
	public String StorePassword = "storepassword";
	@ConfigEntry.Category("SSL")
	public String KeyPassword = "keypassword";

	@ConfigEntry.Category("Connection")
	public String host = "0.0.0.0";
	@ConfigEntry.Category("Connection")
	public boolean useIntegratedWebServer = true;
	@ConfigEntry.Category("Connection")
	@Comment("Port for the client web server (if enabled)")
	public int clientPort = 8081;
	@ConfigEntry.Category("Connection")
	@Comment("Port for the socket server")
	public int socketPort = 8080;

	@ConfigEntry.Category("Language")
	public String language = "en";

	@ConfigEntry.Category("User Data")
	@ConfigEntry.Gui.CollapsibleObject
	public List<UserData> users = new ArrayList<>(List.of(new UserDataImpl("admin", "admin", UserType.ADMIN, false, false, null)));

	@ConfigEntry.Category("Web Interface")
	@Comment("Customize the web interface appearance")
	public String pageTitle = "Minecraft Server Admin Console";
	@ConfigEntry.Category("Web Interface")
	@Comment("Text shown in the top-left navbar (leave empty to use logo image)")
	public String brandingText = "Minecraft Server Admin Console";
	@ConfigEntry.Category("Web Interface")
	@Comment("Logo image path relative to webconsole directory (replaces branding text if set)")
	public String logoImagePath = "";
	@ConfigEntry.Category("Web Interface")
	@Comment("Console background color (CSS color value)")
	public String consoleBackgroundColor = "#000000";
	@ConfigEntry.Category("Web Interface")
	@Comment("Console text color (CSS color value)")
	public String consoleTextColor = "#ffffff";
	@ConfigEntry.Category("Web Interface")
	@Comment("Console font family (CSS font-family value)")
	public String consoleFontFamily = "Courier New";
	@ConfigEntry.Category("Web Interface")
	@Comment("Console font size (CSS font-size value)")
	public String consoleFontSize = "12px";
	@ConfigEntry.Category("Web Interface")
	@Comment("Page background color (CSS color value)")
	public String pageBackgroundColor = "#111111";
	@ConfigEntry.Category("Web Interface")
	@Comment("Navbar background color (CSS color value)")
	public String navbarBackgroundColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Card background color (CSS color value)")
	public String cardBackgroundColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Card text color (CSS color value)")
	public String cardTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Navbar text color (CSS color value)")
	public String navbarTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Button text color (CSS color value)")
	public String buttonTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Button background color (CSS color value)")
	public String buttonBackgroundColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Send button text")
	public String sendButtonText = "GO";
	@ConfigEntry.Category("Web Interface")
	@Comment("Input field text color (CSS color value)")
	public String inputTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Input field background color (CSS color value)")
	public String inputBackgroundColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Modal text color (CSS color value)")
	public String modalTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Modal background color (CSS color value)")
	public String modalBackgroundColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Link text color (CSS color value)")
	public String linkTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Footer text (replaces 'WebConsole v2.3 - GitHub' link)")
	public String footerText = "Minecraft Server Admin Console";
	@ConfigEntry.Category("Web Interface")
	@Comment("Footer text color (CSS color value)")
	public String footerTextColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Server Console title text")
	public String consoleTitleText = "Minecraft Server Admin Console";
	@ConfigEntry.Category("Web Interface")
	@Comment("Server Console title text color (CSS color value)")
	public String consoleTitleColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Progress bar background color (CSS color value)")
	public String progressBarBackgroundColor = "#000000";
	@ConfigEntry.Category("Web Interface")
	@Comment("Progress bar foreground color (CSS color value)")
	public String progressBarForegroundColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Scrollbar track background color (CSS color value)")
	public String scrollbarTrackColor = "#242424";
	@ConfigEntry.Category("Web Interface")
	@Comment("Scrollbar thumb foreground color (CSS color value)")
	public String scrollbarThumbColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Input field border/outline color (CSS color value)")
	public String inputBorderColor = "#FFFFFF";
	@ConfigEntry.Category("Web Interface")
	@Comment("Send button border/outline color (CSS color value)")
	public String buttonBorderColor = "#FFFFFF";

	public boolean isSslEnabled() {
		return useSSL;
	}

	public String getStoreType() {
		return StoreType;
	}

	public String getKeyStore() {
		return KeyStore;
	}

	public String getStorePassword() {
		return StorePassword;
	}

	public String getKeyPassword() {
		return KeyPassword;
	}

	/**
	 * Language code from config.yml
	 * @return language code
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Get all registered users
	 * @return All Admin and Viewer users inside config.yml
	 */
	public List<UserData> getAllUsers(){
		return users;
	}

	// Web Interface customization getters
	public String getPageTitle() {
		return pageTitle;
	}

	public String getBrandingText() {
		return brandingText;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public String getConsoleBackgroundColor() {
		return consoleBackgroundColor;
	}

	public String getConsoleTextColor() {
		return consoleTextColor;
	}

	public String getConsoleFontFamily() {
		return consoleFontFamily;
	}

	public String getConsoleFontSize() {
		return consoleFontSize;
	}

	public String getPageBackgroundColor() {
		return pageBackgroundColor;
	}

	public String getNavbarBackgroundColor() {
		return navbarBackgroundColor;
	}

	public String getCardBackgroundColor() {
		return cardBackgroundColor;
	}

	public String getCardTextColor() {
		return cardTextColor;
	}

	public String getNavbarTextColor() {
		return navbarTextColor;
	}

	public String getButtonTextColor() {
		return buttonTextColor;
	}

	public String getButtonBackgroundColor() {
		return buttonBackgroundColor;
	}

	public String getSendButtonText() {
		return sendButtonText;
	}

	public String getInputTextColor() {
		return inputTextColor;
	}

	public String getInputBackgroundColor() {
		return inputBackgroundColor;
	}

	public String getModalTextColor() {
		return modalTextColor;
	}

	public String getModalBackgroundColor() {
		return modalBackgroundColor;
	}

	public String getLinkTextColor() {
		return linkTextColor;
	}

	public String getFooterText() {
		return footerText;
	}

	public String getFooterTextColor() {
		return footerTextColor;
	}

	public String getConsoleTitleText() {
		return consoleTitleText;
	}

	public String getConsoleTitleColor() {
		return consoleTitleColor;
	}

	public String getProgressBarBackgroundColor() {
		return progressBarBackgroundColor;
	}

	public String getProgressBarForegroundColor() {
		return progressBarForegroundColor;
	}

	public String getScrollbarTrackColor() {
		return scrollbarTrackColor;
	}

	public String getScrollbarThumbColor() {
		return scrollbarThumbColor;
	}

	public String getInputBorderColor() {
		return inputBorderColor;
	}

	public String getButtonBorderColor() {
		return buttonBorderColor;
	}

}