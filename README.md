# BIXAPP

### Android Jetpack Demo

:warning: Pre-alpha - no usar en produccion! :warning: 

## Introducción
La aplicacion utiliza el api definida en [Gorest](https://gorest.co.in) y muestra varias pantallas
en las cuales se puede navegar por una lista de personas, posts y comentarios realizados por las personas
y albums al igual que visualizar fotografias.

Para compilar tu mismo la aplicacion puedes utilizar android studio, el proyecto implementa
la estructura estandard de un proyecto Android.

Para comunicarse con el api de [Gorest](https://gorest.co.in) se requiere de un token de autenticacion,
para conseguirlo puedes ir al enlace y hacer click en **free registration**, como se ve en la siguiente
imagen:

<p align="center">
  <img src="http://qualisys.com.co/media/github/jp1.png" alt="Botón registro gratis" />
</p>

Puedes loguearte con tus credenciales de facebook, google o github. Completa tu información de cuenta escogiendo el modo de autenticación para GO REST:

<p align="center">
  <img src="http://qualisys.com.co/media/github/jp3.png" alt="Selección del modo de autenticacion" />
</p>

Y luego se generará tu token para ser utilizado en la aplicación Android

<p align="center">
  <img src="http://qualisys.com.co/media/github/jp4.png" alt="token" />
</p>


Luego tienes que buscar el siguiente archivo: `Constants.java` ubicado en este directorio

```Jetpack-app/app/src/main/java/com/example/bixapp/commons/```

En el cual deberás remplazar la constante `ACCESS_TOKEN` con el toquen generado en los pasos anteriores.