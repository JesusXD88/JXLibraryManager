# JXLibraryManager
Aplicación a Consola basada en Java que permite gestionar una biblioteca de libros usando una base de datos SQLite.
Proyecto personal que he ido desarrollando en mi tiempo libre con mis limitados conocimientos de 2º de carrera de Ingeniería Inform
## Instalación
1. Descargar e instalar Java JDK 16 o OpenJDK 16 si no estaba ya instalado, ya que este proyecto se ha compilado con Java 16.
2. Descargar el archivo JAR desde [Releases](https://github.com/JesusXD88/JXLibraryManager/releases "Releases") y la Base de Datos (importante, ya que el programa no funcionará sin ella).
3. Situar ambos archivo en el mismo directorio.
4. Desde una terminal (bash, zsh, CMD, PowerShell,...) navegar hasta el directorio donde se encuentra el programa y ejecutar el siguiente comando:
    ```bash
    java -jar "JXLibraryManager(v1.00).jar"
    ```
5. Enjoy!
## Uso
Desde los menús se pueden visualizar las operaciones que se pueden realizar, tales como:
* Insertar, modificar y eliminar libros.
* Visualizar la biblioteca y los libros extraidos.
* Extraer libros.
* Realizar opciones avanzadas como eliminar todos los datos de la DB o código SQL.
## Compilación
Este programa lo he estado desarrollando únicamente en EclipseIDE con sólamente los conocimientos que he ido adquiriendo durante 1º y 2º del grado de Ingeniería Informática. Por lo que puede que esta no sea la manera más óptima ni por asomo, pero es hasta lo que alcanzo hacer.
Para importar el código en Eclipse (Desarrollado usando Eclipse 2021-06 (4.20.0)):
1. Copiar la URL git del repo.
2. En Eclipse dirigirse a File -> Import -> Import Projects From Git.
3. Seleccionar Clone URI -> Pegar la URL anteriormente copiada.
4. En la siguiente pantalla, seleccionar la rama main y a continuación seleccionar el directorio dónde almacenar los proyectos.
5. Cuando termine de clonarse el repo, seleccionar el proyecto JXLibraryManager y si también se desea el proyecto docs con la documentación JavaDoc.
6. Una vez finalizada la importación, click derecho sobre el proyecto -> Properties -> Java Build Path -> Libraries y hacer doble click sobre cada libreria externa.
   En la ventana de selección de archivo, navegar hasta la raíz del proyecto y dentro de la carpeta libs seleccionar el archivo correspondiente a dicha librería.
7. Repetir el paso anterior para cada una de las librerías externas.
8. Ejecutar la clase Main como Java Application.

Para compilar y exportar el archivo ejecutable JAR:

1. Click derecho sobre el proyecto -> Export -> Runnable JAR File. Seleccionar como launch configuration el Main, el archivo a guardar y seleccionar la opción de Extract required libraries into JAR.
2. A continuación, copiar la base de datos que se encuentra en la raíz del proyecto a la misma carpeta del archivo JAR que se ha exportado. Si no el programa no funcionará.
3. Ejecutar tal y como está explicado en el apartado de Instalación.
## Documentación
Puedes consultar la documentación a continuación:
[Documentación JavaDoc](https://jesusxd88.github.io/JXLibraryManager/org/JXLibraryManager/App/package-summary.html "Documentación JavaDoc")
