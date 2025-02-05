# RapiTube 🎬

RapiTube es un proyecto universitario desarrollado en Java con Spring Boot y SQL Server. Su objetivo es simular una plataforma 
de streaming de videos con usuarios regulares y premium, basicamente se trato de emular lo que es YouTube con fines educativos
y de exploracion y uso inicial en las bases de datos.

## 🚀 Características
- Registro e inicio de sesión de usuarios.
- Visualización de videos con historial.
- Diferenciación entre usuarios regulares y premium.
- Permite subir y eliminar videos.
- Modificacion de elementos del perfil de usuario.

## 🛠️ Tecnologías utilizadas
- **Backend:** Java 17, Spring Boot, Maven
- **Base de datos:** SQL Server
- **Frontend:** HTML, CSS

## 📦 Instalación
1. Crea dos carpetas en tu disco local (C:)
2. Primera carpeta `FotosPerfilRP`
3. Segunda carpeta `VideosRP`
### 1️⃣ Clonar el repositorio
```sh
git clone https://github.com/Sacrilegious0x/RapiTube.git
cd RapiTube
```
## 📂 Base de Datos

Para importar la base de datos en SQL Server, sigue estos pasos:

1. Abre **SQL Server Management Studio (SSMS)**.
2. Crea una nueva base de datos con el nombre `RapiTube`.
3. Abre el archivo `RapiTubeDB.sql` y ejecútalo en el servidor.
4. Verifica que las tablas y datos se hayan importado correctamente.

   Nota: el Script se encuentra en la carpeta db del proyecto, ademas puede ser necesario realizar otras acciones
   como, configurar el usuario y la contraseña en el archivo `aplication.properties`

¡Listo! Ahora puedes ejecutar la aplicación. 🚀


