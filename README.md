# WakeOn FX!
A simple JavaFX tool to turn on a computer with Wake-On-LAN (WoL). Can monitor computer connectivity and running services.

> [!TIP]
> You must enable **WoL** in both the system's BIOS/UEFI and in the OS's network adapter properties.
***

This application require java 11. To easily set it, use:
[sdkman](https://sdkman.io/)

#### Update project
`mvn dependency:resolve`

#### Unit Test
`mvn test`

#### Integration Test
`mvn integration-test`

#### Run the project
`mvn javafx:run`

### Create and verify jar package
`mvn verify`

to run test without gui just add `-Dheadless`

### Run wakeonfx using uber-jar package
`java -Dlogback.configurationFile=logback.xml -jar target/wakeonfx-1.1.0-uber.jar`

***

#### Screenshot
> [!IMPORTANT]
> Icons used in the application have use restriction.


![gui main](main.png?raw=true)

![gui add](add.png?raw=true)
