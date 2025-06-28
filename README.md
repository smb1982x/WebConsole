# WebConsole - Remote Minecraft Server Administration

[![License](https://img.shields.io/badge/license-CC0--1.0-blue.svg)](LICENSE)
[![Minecraft](https://img.shields.io/badge/minecraft-1.21.6-green.svg)](https://minecraft.net)
[![Fabric](https://img.shields.io/badge/fabric-0.128.1-orange.svg)](https://fabricmc.net)
[![Java](https://img.shields.io/badge/java-21-red.svg)](https://adoptopenjdk.net)

WebConsole is a powerful Fabric mod for Minecraft 1.21.6+ that provides **secure web-based remote access** to your Minecraft server console. Monitor your server, execute commands, and manage your Minecraft server from anywhere with a web browser.

![WebConsole Interface](https://i.imgur.com/sN1sYju.png)

## 🚀 Key Features

### 🌐 **Remote Web Console**
- **Real-time console output** with full color support
- **Command execution** with admin permissions
- **Cross-platform compatibility** (Windows, Linux, macOS)
- **No external dependencies** - direct connection to your server

### 👥 **Multi-User System**
- **Admin Users**: Full console access + command execution
- **Viewer Users**: Read-only console monitoring
- **Command Whitelisting/Blacklisting**: Granular permission control
- **Session Management**: Secure login with IP tracking

### 📊 **Server Monitoring**
- **Real-time player count** and player list
- **CPU usage monitoring** with live updates
- **RAM usage tracking** (used/total memory)
- **Server status** and performance metrics

### 🌍 **Multi-Language Support**
- **14 Languages**: English, Spanish, Chinese, Czech, German, Dutch, French, Italian, Japanese, Korean, Portuguese, Russian, Turkish
- **Dynamic language switching** in web interface
- **Localized console messages** and UI elements

### 🔒 **Security & Privacy**
- **Direct connection**: No intermediary servers
- **Local data storage**: All settings stored in your browser
- **SSL/TLS support**: Optional encryption for secure connections
- **IP-based access control**: Track and manage connections

### ⚡ **Advanced Features**
- **Command history**: Navigate with up/down arrow keys
- **Multiple server support**: Connect to multiple servers simultaneously
- **WebSocket real-time communication**: Low latency updates
- **Integrated web server**: Optional built-in web client hosting
- **Automatic reconnection**: Robust connection management

---

## 📋 Table of Contents

- [Requirements](#-requirements)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Commands](#-commands)
- [Security](#-security)
- [Troubleshooting](#-troubleshooting)
- [API Documentation](#-api-documentation)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🔧 Requirements

### Server Requirements
- **Minecraft**: 1.21.6+
- **Fabric Loader**: 0.16.0+
- **Fabric API**: 0.128.1+1.21.6+
- **Java**: 21+ (Required for MC 1.21.6)

### Client Requirements
- **Modern web browser** with WebSocket support
- **Internet connection** to the server
- **Ports opened**: Default 8080 (WebSocket) and 8081 (Web server)

---

## 📦 Installation

### 1. Download the Mod
```bash
# Download from releases page
wget https://github.com/godemperoroftheworld/WebConsole/releases/latest/download/webconsole-2.4.0.jar
```

### 2. Install on Server
1. Stop your Minecraft server
2. Place `webconsole-2.4.0.jar` in your `mods/` folder
3. Start your server
4. WebConsole will generate a configuration file on first run

### 3. Configure Ports
Ensure these ports are **port-forwarded** in your router/firewall:
- **8080**: WebSocket server (required)
- **8081**: Web client server (optional, if using integrated web server)

---

## ⚙️ Configuration

WebConsole creates a configuration file at `config/webconsole.json5`:

### Basic Configuration
```json5
{
  // Connection Settings
  "host": "0.0.0.0",                    // Bind address (0.0.0.0 = all interfaces)
  "socketPort": 8080,                   // WebSocket server port
  "clientPort": 8081,                   // Web server port
  "useIntegratedWebServer": true,       // Enable built-in web server
  
  // Language Settings
  "language": "en",                     // Server console language
  
  // User Management
  "users": [
    {
      "username": "admin",
      "password": "admin",               // ⚠️ CHANGE DEFAULT PASSWORD!
      "userType": "ADMIN",
      "whitelistEnabled": false,
      "whitelistActsAsBlacklist": false,
      "whitelistedCommands": []
    }
  ],
  
  // SSL Configuration (Optional)
  "useSSL": false,
  "StoreType": "JKS",
  "KeyStore": "plugins/WebConsole/keystore.jks",
  "StorePassword": "storepassword",
  "KeyPassword": "keypassword"
}
```

### User Types
- **ADMIN**: Full access (console viewing + command execution)
- **VIEWER**: Read-only access (console viewing only)

### Command Filtering
```json5
{
  "username": "moderator",
  "password": "mod_password",
  "userType": "ADMIN",
  "whitelistEnabled": true,
  "whitelistActsAsBlacklist": false,    // true = blacklist, false = whitelist
  "whitelistedCommands": [
    "say",
    "tell",
    "kick",
    "ban"
  ]
}
```

### SSL Configuration
For secure HTTPS connections, configure SSL certificates:

```json5
{
  "useSSL": true,
  "StoreType": "JKS",
  "KeyStore": "/path/to/keystore.jks",
  "StorePassword": "your_store_password",
  "KeyPassword": "your_key_password"
}
```

---

## 🖥️ Usage

### Accessing the Web Interface

#### Option 1: Integrated Web Server
If `useIntegratedWebServer` is enabled:
```
http://your-server-ip:8081
```

#### Option 2: External Web Client
1. Download the web client from releases
2. Host it on any web server
3. Configure connection to `ws://your-server-ip:8080`

### Web Interface Features

#### 🔐 **Login Screen**
- Enter your username and password (configured in `webconsole.json5`)
- Select language preference
- Connection status indicator

#### 📺 **Console View**
- **Real-time console output** with color support
- **Scrollable history** with automatic scrolling
- **Command input** at the bottom (Admin users only)
- **Server information** panel

#### 📊 **Monitoring Dashboard**
- **Connected Players**: Real-time player count and list
- **Server Performance**: CPU and RAM usage
- **Connection Status**: WebSocket connection health
- **Command History**: Previous commands with up/down navigation

#### ⚙️ **Settings Panel**
- **Language Selection**: Switch between 14 supported languages
- **Connection Settings**: Server IP/port configuration
- **Display Options**: Console colors and formatting
- **User Preferences**: Save locally in browser

---

## 🔧 Commands

### In-Game Commands

#### `/WebConsole`
**Permission**: None required (server admin command)
**Description**: Display WebConsole status and connected clients

```bash
/WebConsole
```

**Output Example**:
```
WebConsole v2.4.0
Active connections:
- admin (192.168.1.100)
- viewer (192.168.1.101)
```

### Web Console Commands

#### **Server Commands**
Execute any Minecraft server command through the web interface:

```bash
# Player management
say Hello from WebConsole!
kick PlayerName Reason
ban PlayerName Reason
pardon PlayerName

# World management
time set day
weather clear
seed

# Server management
stop
save-all
reload
```

#### **System Commands**
Special WebConsole commands for monitoring:

- **Player List**: Automatically updated in sidebar
- **Performance Metrics**: CPU/RAM displayed in real-time
- **Connection Status**: WebSocket health monitoring

---

## 🔒 Security

### 🛡️ **Security Best Practices**

#### 1. **Change Default Credentials**
```json5
// ❌ NEVER use defaults in production
"username": "admin",
"password": "admin"

// ✅ Use strong credentials
"username": "your_secure_username", 
"password": "your_strong_password_123!"
```

#### 2. **Use SSL/TLS Encryption**
```json5
{
  "useSSL": true,
  "KeyStore": "/secure/path/keystore.jks"
}
```

#### 3. **Restrict Network Access**
```bash
# Firewall rules (example for iptables)
iptables -A INPUT -p tcp --dport 8080 -s 192.168.1.0/24 -j ACCEPT
iptables -A INPUT -p tcp --dport 8080 -j DROP
```

#### 4. **Use Command Whitelisting**
```json5
{
  "whitelistEnabled": true,
  "whitelistActsAsBlacklist": false,
  "whitelistedCommands": ["say", "tell", "list"]  // Only allow safe commands
}
```

### 🔍 **Security Features**

- **IP Tracking**: All connections logged with IP addresses
- **Session Management**: Automatic logout on disconnect
- **Permission Levels**: Granular access control
- **Command Filtering**: Whitelist/blacklist support
- **SSL Support**: Optional encryption for data in transit

---

## 🔧 Troubleshooting

### ❌ **Common Issues**

#### **Cannot Connect to WebSocket**
```bash
# Check if server is running
curl -I http://your-server-ip:8081

# Check if ports are open
telnet your-server-ip 8080
telnet your-server-ip 8081

# Check firewall rules
sudo ufw status
iptables -L
```

#### **"WebSocket connection failed"**
1. **Verify server is running**: Check Minecraft server console
2. **Check port forwarding**: Ensure port 8080 is forwarded
3. **Firewall settings**: Allow traffic on WebConsole ports
4. **SSL mismatch**: If using HTTPS, enable SSL in config

#### **Authentication Failed**
1. **Check credentials**: Verify username/password in config
2. **Configuration syntax**: Ensure valid JSON5 format
3. **Restart server**: Reload configuration changes

#### **Console Colors Not Working**
1. **Windows**: See [Windows Console Characters Guide](https://github.com/mesacarlos/WebConsole/wiki/Show-local-characters-in-Windows-Console)
2. **Linux**: Ensure terminal supports ANSI colors
3. **Web browser**: Try different browser/disable extensions

### 🐛 **Debug Mode**

Enable debug logging in Minecraft server:
```bash
# Add to server startup script
-Dwebconsole.debug=true
```

Check server logs for WebConsole messages:
```bash
tail -f logs/latest.log | grep WebConsole
```

### 📊 **Performance Issues**

#### **High CPU Usage**
- Reduce console output frequency
- Limit number of connected clients
- Check for console spam in server

#### **Memory Leaks**
- Monitor with `/WebConsole` command
- Restart server if connections don't close properly
- Check browser for multiple open connections

---

## 📚 API Documentation

### WebSocket Protocol

WebConsole uses WebSocket communication for real-time updates:

#### Connection Endpoint
```
ws://your-server-ip:8080/
```

#### Message Format
```json
{
  "command": "command_name",
  "data": {
    // Command-specific data
  }
}
```

#### Available Commands

##### **Authentication**
```json
{
  "command": "login",
  "data": {
    "username": "admin",
    "password": "password"
  }
}
```

##### **Execute Server Command**
```json
{
  "command": "exec",
  "data": {
    "command": "say Hello World"
  }
}
```

##### **Get Player List**
```json
{
  "command": "players",
  "data": {}
}
```

##### **Get System Stats**
```json
{
  "command": "cpu",
  "data": {}
}
```

```json
{
  "command": "ram",
  "data": {}
}
```

### Response Format
```json
{
  "type": "response_type",
  "message": "Human readable message",
  "data": {
    // Response data
  }
}
```

---

## 🌐 Multi-Language Support

### Supported Languages

| Language | Code | Contributors |
|----------|------|--------------|
| English | `en` | MesaCarlos |
| Spanish | `es` | MesaCarlos |
| Chinese | `zh` | Neubulae, OPhantomO |
| Czech | `cs` | Tada |
| German | `de` | NoNamePro0 |
| Dutch | `nl` | Twockx |
| French | `fr` | pickatchou999 |
| Italian | `it` | AlexZap |
| Japanese | `ja` | kuroneko6423 |
| Korean | `ko` | XxPKBxX |
| Portuguese | `pt` | AlexandreMuassab, Connect500BR |
| Russian | `ru` | Stashenko |
| Turkish | `tr` | acarnd03 |

### Adding Translations

1. Copy `phrases_en.properties` to `phrases_[code].properties`
2. Translate all phrases while keeping the keys unchanged
3. Submit a pull request with your translation

---

## 🤝 Contributing

### Development Setup

1. **Clone the repository**
```bash
git clone https://github.com/godemperoroftheworld/WebConsole.git
cd WebConsole
```

2. **Setup development environment**
```bash
# Ensure Java 21 is installed
java -version

# Import project in IntelliJ IDEA
./gradlew genSources
```

3. **Build the mod**
```bash
./gradlew build
```

### Contribution Guidelines

- **Code Style**: Follow existing Java conventions
- **Testing**: Test with Minecraft 1.21.6+ server
- **Documentation**: Update README for new features
- **Translations**: Maintain all language files

### Reporting Issues

Please include:
- **Minecraft version**
- **Fabric version** 
- **WebConsole version**
- **Server logs** (with WebConsole debug enabled)
- **Browser console errors**
- **Steps to reproduce**

---

## 📄 License

This project is licensed under the **Creative Commons Zero v1.0 Universal** license.

```
CC0 1.0 Universal (CC0 1.0) Public Domain Dedication

The person who associated a work with this deed has dedicated the work to the 
public domain by waiving all of his or her rights to the work worldwide under 
copyright law, including all related and neighboring rights, to the extent 
allowed by law.

You can copy, modify, distribute and perform the work, even for commercial 
purposes, all without asking permission.
```

---

## 🙏 Acknowledgments

- **Original Author**: [MesaCarlos](https://github.com/mesacarlos) for the original WebConsole plugin
- **Fabric Port**: [CommodoreThrawn](https://github.com/t2pellet) for the Fabric mod adaptation
- **Translation Contributors**: All community members who provided language translations
- **Fabric Community**: For the excellent modding framework and documentation
- **Minecraft Community**: For testing and feedback

---

## 📞 Support

- **GitHub Issues**: [Report bugs and request features](https://github.com/godemperoroftheworld/WebConsole/issues)
- **Discord**: Join the Fabric modding community
- **Wiki**: [WebConsole Documentation](https://github.com/mesacarlos/WebConsole/wiki)

---

**Made with ❤️ for the Minecraft community**

*WebConsole - Bringing modern web administration to Minecraft servers*