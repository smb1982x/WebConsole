package es.mesacarlos.webconsole.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.mesacarlos.webconsole.WCConstants;
import es.mesacarlos.webconsole.WebConsole;
import es.mesacarlos.webconsole.config.WCConfig;
import es.mesacarlos.webconsole.util.Internationalization;
import fi.iki.elonen.NanoHTTPD;
import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static es.mesacarlos.webconsole.WebConsole.WEB_PATH;

class WebServer extends NanoHTTPD {

    private static final Map<String, String> TYPES = new HashMap<>() {{
        put("css", "text/css");
        put("html", "text/html");
        put("js", "application/javascript");
        put("json", "application/json");
    }};

    WebServer(String host, int port) {
        super(host, port);
    }

    @Override
    public void start(int timeout, boolean daemon) throws IOException {
        super.start(timeout, daemon);
        File settingsFile = new File(WEB_PATH, "settings.json");
        // Update client server if necessary
        int revision = 0;
        if (settingsFile.exists()) {
            FileReader reader  = new FileReader(settingsFile);
            JsonElement revisionElement = JsonParser.parseReader(reader).getAsJsonObject().get("revision");
            if (revisionElement != null) revision = revisionElement.getAsInt();
            reader.close();
        }
        if (revision < WCConstants.CLIENT_REVISION) {
            updateServer();
        }

        // Settings are now served dynamically - no static file needed
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.GET) {
            // Handle language API endpoint
            if (session.getUri().startsWith("/api/language/")) {
                String language = session.getUri().substring("/api/language/".length());
                return newFixedLengthResponse(Response.Status.OK, "application/json", getLanguageJson(language));
            }
            
            // Handle settings endpoint - serve live config instead of static file
            if (session.getUri().equals("/settings.json")) {
                return newFixedLengthResponse(Response.Status.OK, "application/json", getLiveSettingsJson());
            }
            
            String path = WEB_PATH + session.getUri();
            if (session.getUri().equals("/")) {
                path += "index.html";
            }
            String suffix = path.substring(path.lastIndexOf(".") + 1);
            String type = TYPES.get(suffix);
            return newFixedLengthResponse(Response.Status.OK, type, readFile(path));
        } else {
            return null;
        }
    }

    private static void updateServer() {
        // Delete old server if applicable
        File wcFolder = new File(WEB_PATH);
        if (wcFolder.exists()) {
            var files = wcFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            wcFolder.mkdirs();
        }
        // Extract server
        WebConsole.LOGGER.info(Internationalization.getPhrase("unzip"));
        try {
            // Copy zip
            InputStream is = WebConsole.class.getResourceAsStream("/client.zip");
            File wcZipFile = new File(wcFolder, "client.zip");
            Files.copy(is, wcZipFile.toPath());
            // Unzip
            ZipFile zipFile = new ZipFile(wcZipFile);
            zipFile.extractAll(wcFolder.getAbsolutePath());
            Files.deleteIfExists(wcZipFile.toPath());
            WebConsole.LOGGER.info(Internationalization.getPhrase("unzip-success"));
        } catch (IOException ex) {
            WebConsole.LOGGER.error(Internationalization.getPhrase("unzip-error") + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String readFile(String path) {
        // Read index html
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    private static String getLanguageJson(String language) {
        // Try to read custom language file first
        File languageFile = new File(WEB_PATH, "lang/" + language + ".json");
        if (languageFile.exists()) {
            return readFile(languageFile.getAbsolutePath());
        }
        
        // Return default English language strings
        JsonObject defaultLang = new JsonObject();
        defaultLang.addProperty("language", "Language");
        defaultLang.addProperty("settings", "Settings");
        defaultLang.addProperty("players_online", "Players Online");
        defaultLang.addProperty("cpu_title", "CPU");
        defaultLang.addProperty("ram_title", "RAM");
        defaultLang.addProperty("user_title", "Logged as");
        defaultLang.addProperty("delete_server_button", "Delete server");
        defaultLang.addProperty("password_required", "Password required");
        defaultLang.addProperty("password_label", "Password:");
        defaultLang.addProperty("remember_password", "Remember password");
        defaultLang.addProperty("close", "Close");
        defaultLang.addProperty("login", "Login");
        defaultLang.addProperty("disconnected", "Disconnected");
        defaultLang.addProperty("disconnection_description", "Connection was lost with the server. This can be caused by:");
        defaultLang.addProperty("disconnection_sub1", "Server was closed intentionally.");
        defaultLang.addProperty("disconnection_sub2", "Port is not opened on your host. In this case, troubleshoot using this tool and recheck your firewall or router.");
        defaultLang.addProperty("welcome_screen", "Welcome screen");
        defaultLang.addProperty("webconsole_settings", "WebConsole Settings");
        defaultLang.addProperty("show_date_setting", "Show date and time on each console line");
        defaultLang.addProperty("read_log_file_setting", "Retrieve full log file from server after login");
        defaultLang.addProperty("done", "Done");
        defaultLang.addProperty("send", "Send");
        
        return defaultLang.toString();
    }
    
    /**
     * Generate settings JSON dynamically from current configuration
     */
    private static String getLiveSettingsJson() {
        JsonObject settingsJson = new JsonObject();
        settingsJson.addProperty("revision", WCConstants.CLIENT_REVISION);
        settingsJson.addProperty("ssl", WCConfig.getInstance().isSslEnabled());
        settingsJson.addProperty("port", WCConfig.getInstance().socketPort);
        settingsJson.addProperty("host", "local");
        
        // Add web interface customization settings - live from config
        settingsJson.addProperty("pageTitle", WCConfig.getInstance().getPageTitle());
        settingsJson.addProperty("brandingText", WCConfig.getInstance().getBrandingText());
        settingsJson.addProperty("logoImagePath", WCConfig.getInstance().getLogoImagePath());
        settingsJson.addProperty("consoleBackgroundColor", WCConfig.getInstance().getConsoleBackgroundColor());
        settingsJson.addProperty("consoleTextColor", WCConfig.getInstance().getConsoleTextColor());
        settingsJson.addProperty("consoleFontFamily", WCConfig.getInstance().getConsoleFontFamily());
        settingsJson.addProperty("consoleFontSize", WCConfig.getInstance().getConsoleFontSize());
        settingsJson.addProperty("pageBackgroundColor", WCConfig.getInstance().getPageBackgroundColor());
        settingsJson.addProperty("navbarBackgroundColor", WCConfig.getInstance().getNavbarBackgroundColor());
        settingsJson.addProperty("cardBackgroundColor", WCConfig.getInstance().getCardBackgroundColor());
        settingsJson.addProperty("cardTextColor", WCConfig.getInstance().getCardTextColor());
        settingsJson.addProperty("navbarTextColor", WCConfig.getInstance().getNavbarTextColor());
        settingsJson.addProperty("buttonTextColor", WCConfig.getInstance().getButtonTextColor());
        settingsJson.addProperty("buttonBackgroundColor", WCConfig.getInstance().getButtonBackgroundColor());
        settingsJson.addProperty("sendButtonText", WCConfig.getInstance().getSendButtonText());
        settingsJson.addProperty("inputTextColor", WCConfig.getInstance().getInputTextColor());
        settingsJson.addProperty("inputBackgroundColor", WCConfig.getInstance().getInputBackgroundColor());
        settingsJson.addProperty("modalTextColor", WCConfig.getInstance().getModalTextColor());
        settingsJson.addProperty("modalBackgroundColor", WCConfig.getInstance().getModalBackgroundColor());
        settingsJson.addProperty("linkTextColor", WCConfig.getInstance().getLinkTextColor());
        settingsJson.addProperty("footerText", WCConfig.getInstance().getFooterText());
        settingsJson.addProperty("footerTextColor", WCConfig.getInstance().getFooterTextColor());
        settingsJson.addProperty("consoleTitleText", WCConfig.getInstance().getConsoleTitleText());
        settingsJson.addProperty("consoleTitleColor", WCConfig.getInstance().getConsoleTitleColor());
        settingsJson.addProperty("progressBarBackgroundColor", WCConfig.getInstance().getProgressBarBackgroundColor());
        settingsJson.addProperty("progressBarForegroundColor", WCConfig.getInstance().getProgressBarForegroundColor());
        settingsJson.addProperty("scrollbarTrackColor", WCConfig.getInstance().getScrollbarTrackColor());
        settingsJson.addProperty("scrollbarThumbColor", WCConfig.getInstance().getScrollbarThumbColor());
        settingsJson.addProperty("inputBorderColor", WCConfig.getInstance().getInputBorderColor());
        settingsJson.addProperty("buttonBorderColor", WCConfig.getInstance().getButtonBorderColor());
        
        return settingsJson.toString();
    }
}
