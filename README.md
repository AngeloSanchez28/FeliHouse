# FeliHouse 🏠📱

<div align="center">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/kotlin/kotlin-original.svg" alt="Kotlin" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original.svg" alt="Android" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/gradle/gradle-plain.svg" alt="Gradle" width="60" height="60"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/arduino/arduino-original.svg" alt="Arduino" width="60" height="60"/>
  <img src="https://developer.android.com/static/studio/images/studio-icon.svg" alt="Android Studio" width="60" height="60"/>
</div>

<br>

**FeliHouse** es una aplicación móvil IoT (Internet of Things) desarrollada en Kotlin que permite controlar y monitorear dispositivos inteligentes del hogar de forma remota. La aplicación ofrece una interfaz intuitiva para gestionar la domótica de tu hogar, proporcionando comodidad, seguridad y eficiencia energética.

## 🎯 Características Principales

- **🏠 Control Domótico**: Gestión completa de dispositivos inteligentes del hogar
- **🌐 Conectividad IoT**: Integración con sensores y actuadores inteligentes
- **⚡ Tiempo Real**: Monitoreo y control en tiempo real de dispositivos
- **🔒 Seguridad**: Comunicación segura con dispositivos IoT
- **🎨 UI Moderna**: Diseño intuitivo siguiendo Material Design

## 🚀 Tecnologías

### Desarrollo Móvil
- **Kotlin** - Lenguaje de programación principal
- **Android SDK** - Kit de desarrollo para Android
- **Android Studio** - IDE oficial de desarrollo
- **Gradle** - Sistema de construcción y gestión de dependencias

### IoT y Conectividad
- **MQTT** - Protocolo de comunicación IoT
- **HTTP/HTTPS** - APIs REST para comunicación
- **WebSockets** - Comunicación en tiempo real
- **Bluetooth/WiFi** - Conectividad local con dispositivos

### Arquitectura
- **MVVM** - Patrón Model-View-ViewModel
- **Android Jetpack** - Bibliotecas modernas de Android
- **Room Database** - Base de datos local
- **Retrofit** - Cliente HTTP para APIs

## 📋 Prerrequisitos

- **Android Studio** Arctic Fox o superior
- **JDK 11** o superior
- **Android SDK** (API nivel 21 o superior)
- **Kotlin** 1.8 o superior
- **Gradle** 7.0 o superior
- **Dispositivos IoT** compatibles (Arduino, ESP32, Raspberry Pi, etc.)

## 🛠️ Instalación

### Configuración del Entorno

```bash
# Clonar el repositorio
git clone https://github.com/AngeloSanchez28/FeliHouse.git

# Navegar al directorio del proyecto
cd FeliHouse

# Abrir en Android Studio
# File > Open > Seleccionar la carpeta del proyecto
```

## 🏠 Dispositivos Compatibles

### Sensores Soportados
- **Temperatura y Humedad** (DHT22, DHT11)
- **Movimiento** (PIR)
- **Luminosidad** (LDR)
- **Calidad del Aire** (MQ series)
- **Presencia** (Ultrasónicos)

### Actuadores Soportados
- **Luces LED** (RGB, PWM)
- **Relés** (Control de electrodomésticos)
- **Servomotores** (Puertas, ventanas)
- **Altavoces** (Notificaciones sonoras)
- **Pantallas** (Información local)

### Plataformas IoT
- **Arduino** (Uno, Nano, ESP32)
- **Raspberry Pi** (3B+, 4, Zero)
- **ESP8266/ESP32** (NodeMCU, Wemos)
- **Dispositivos comerciales** (Sonoff, Shelly)

## 🔧 Configuración IoT

### Configuración MQTT
```kotlin
// En build.gradle (app)
android {
    buildTypes {
        debug {
            buildConfigField "String", "MQTT_BROKER", "\"tcp://broker-dev.felihouse.com:1883\""
        }
        release {
            buildConfigField "String", "MQTT_BROKER", "\"tcp://broker.felihouse.com:1883\""
        }
    }
}
```

### Ejemplo de Conexión
```kotlin
class MqttManager {
    private val brokerUrl = BuildConfig.MQTT_BROKER
    private val clientId = "FeliHouse_${System.currentTimeMillis()}"
    
    fun connectToDevice(deviceId: String) {
        // Lógica de conexión MQTT
    }
}
```

## 📱 Compatibilidad

- **Android Mínimo**: API 21 (Android 5.0 Lollipop)
- **Android Target**: API 34 (Android 14)
- **Arquitecturas**: ARM64, ARMv7, x86, x86_64
- **Conectividad**: WiFi, Bluetooth, Datos móviles

---

<div align="center">
  <br><br>
  <i>Desarrollado con ❤️ por Felines</i>
</div>
