### Escuela Colombiana de Ingeniería
### Arquiecturas de Software

## Construción de un cliente 'grueso' con un API REST, HTML5, Javascript y CSS3. Parte I.




### Integrantes
#### Santiago Arévalo Rojas
#### Juan Felipe Sánchez Pérez

### Trabajo individual o en parejas. A quienes tuvieron malos resultados en el parcial anterior se les recomienda hacerlo individualmente.

![img_9.png](img/img_9.png)

* __Al oprimir 'Get blueprints', consulta los planos del usuario dado en el formulario. Por ahora, si la consulta genera un error, sencillamente no se mostrará nada.__
* __Al hacer una consulta exitosa, se debe mostrar un mensaje que incluya el nombre del autor, y una tabla con: el nombre de cada plano de autor, el número de puntos del mismo, y un botón para abrirlo. Al final, se debe mostrar el total de puntos de todos los planos (suponga, por ejemplo, que la aplicación tienen un modelo de pago que requiere dicha información).__
* __Al seleccionar uno de los planos, se debe mostrar el dibujo del mismo. Por ahora, el dibujo será simplemente una secuencia de segmentos de recta realizada en el mismo orden en el que vengan los puntos.__


## Ajustes Backend

1. __Trabaje sobre la base del proyecto anterior (en el que se hizo el API REST).__ 
2. __Incluya dentro de las dependencias de Maven los 'webjars' de jQuery y Bootstrap (esto permite tener localmente dichas librerías de JavaScript al momento de construír el proyecto):__  

    ```xml
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>webjars-locator</artifactId>
    </dependency>

    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>bootstrap</artifactId>
        <version>3.3.7</version>
    </dependency>

    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.1.0</version>
    </dependency>                

    ```

## Front-End - Vistas

1. __Cree el directorio donde residirá la aplicación JavaScript. Como se está usando SpringBoot, la ruta para poner en el mismo contenido estático (páginas Web estáticas, aplicaciones HTML5/JS, etc) es:__

    ```
    src/main/resources/static
    ```

4. __Cree, en el directorio anterior, la página index.html, sólo con lo básico: título, campo para la captura del autor, botón de 'Get blueprints', campo <div> donde se mostrará el nombre del autor seleccionado, [la tabla HTML](https://www.w3schools.com/html/html_tables.asp) donde se mostrará el listado de planos (con sólo los encabezados), y un campo <div> donde se mostrará el total de puntos de los planos del autor. Recuerde asociarle identificadores a dichos componentes para facilitar su búsqueda mediante selectores.__

5. __En el elemento \<head\> de la página, agregue las referencia a las librerías de jQuery, Bootstrap y a la hoja de estilos de Bootstrap.__
    ```html
    <head>
        <title>Blueprints</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <script src="/webjars/jquery/jquery.min.js"></script>
        <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    </head>
    ```


5. __Suba la aplicación (mvn spring-boot:run), y rectifique:__
	1. __Que la página sea accesible desde:__
    ```
    http://localhost:8080/index.html
    ```
   Ejecutando la aplicación y creando el archivo index.html con los elementos especificados, la página web se ve así:  
	![img_2.png](img/img_2.png)
	2. __Al abrir la consola de desarrollador del navegador, NO deben aparecer mensajes de error 404 (es decir, que las librerías de JavaScript se cargaron correctamente).__  
   Como se ve a conitnuación, no aparecen mensajes de error en la consola del desarrolador.
   ![img_3.png](img/img_3.png)

## Front-End - Lógica

1. __Ahora, va a crear un Módulo JavaScript que, a manera de controlador, mantenga los estados y ofrezca las operaciones requeridas por la vista. Para esto tenga en cuenta el [patrón Módulo de JavaScript](https://toddmotto.com/mastering-the-module-pattern/), y cree un módulo en la ruta static/js/app.js .__

2. __Copie el módulo provisto (apimock.js) en la misma ruta del módulo antes creado. En éste agréguele más planos (con más puntos) a los autores 'quemados' en el código.__  
    Se copia el módulo apimock.js y se agrega un plano a cada uno de los autores:  
    ![img_4.png](img/img_4.png)  


3. __Agregue la importación de los dos nuevos módulos a la página HTML (después de las importaciones de las librerías de jQuery y Bootstrap):__
    ```html
    <script src="js/apimock.js"></script>
    <script src="js/app.js"></script>
    ```

3. __Haga que el módulo antes creado mantenga de forma privada:__
	* __El nombre del autor seleccionado.__
	* __El listado de nombre y tamaño de los planos del autor seleccionado. Es decir, una lista objetos, donde cada objeto tendrá dos propiedades: nombre de plano, y número de puntos del plano.__  

   __Junto con una operación pública que permita cambiar el nombre del autor actualmente seleccionado.__  
    Se realizó la implementación solicitada. Realizando una prueba, estableciendo el nombre del autor e imprimiendolo en consola, se comprueba el funcionamiento de la función creada:  
    ![img_5.png](img/img_5.png)  
    ![img_7.png](img/img_7.png)  

4. __Agregue al módulo 'app.js' una operación pública que permita actualizar el listado de los planos, a partir del nombre de su autor (dado como parámetro). Para hacer esto, dicha operación debe invocar la operación 'getBlueprintsByAuthor' del módulo 'apimock' provisto, enviándole como _callback_ una función que:__  

	* __Tome el listado de los planos, y le aplique una función 'map' que convierta sus elementos a objetos con sólo el nombre y el número de puntos.__

	* __Sobre el listado resultante, haga otro 'map', que tome cada uno de estos elementos, y a través de jQuery agregue un elemento \<tr\> (con los respectvos \<td\>) a la tabla creada en el punto 4. Tenga en cuenta los [selectores de jQuery](https://www.w3schools.com/JQuery/jquery_ref_selectors.asp) y [los tutoriales disponibles en línea](https://www.tutorialrepublic.com/codelab.php?topic=faq&file=jquery-append-and-remove-table-row-dynamically). Por ahora no agregue botones a las filas generadas.__

	* __Sobre cualquiera de los dos listados (el original, o el transformado mediante 'map'), aplique un 'reduce' que calcule el número de puntos. Con este valor, use jQuery para actualizar el campo correspondiente dentro del DOM.__  
    Cómo aun no se ha implementado la funcionalidad del botón "Get Blueprints", se realizó el llamado a las funciones dentro el app.js:  
   ![img_11.png](img/img_11.png)  
   Una vez agregadas las operaciones descritas anteriormente, al ejecutar la aplicación web, se actualiza la tabla y la cantidad de puntos del autor especificado:  
   ![img_10.png](img/img_10.png)
    

5. Asocie la operación antes creada (la de app.js) al evento 'on-click' del botón de consulta de la página.  
Se realiza la implementación correspondiente para que al hacer click, se muestren los planos del autor indicado en el input.  
![img_12.png](img/img_12.png)  

6. Verifique el funcionamiento de la aplicación. Inicie el servidor, abra la aplicación HTML5/JavaScript, y rectifique que al ingresar un usuario existente, se cargue el listado del mismo.  
Se hacen las pruebas con los autores y sus blueprintss quemados en el código de la aplicación.  
![img_13.png](img/img_13.png)  
![img_12.png](img/img_12.png)  

## Para la próxima semana

8. __A la página, agregue un [elemento de tipo Canvas](https://www.w3schools.com/html/html5_canvas.asp), con su respectivo identificador. Haga que sus dimensiones no sean demasiado grandes para dejar espacio para los otros componentes, pero lo suficiente para poder 'dibujar' los planos.__  
Se añadió el elemento canvas al index.html.  
![img_14.png](img/img_14.png)


9. __Al módulo app.js agregue una operación que, dado el nombre de un autor, y el nombre de uno de sus planos dados como parámetros, haciendo uso del método getBlueprintsByNameAndAuthor de apimock.js y de una función _callback_:__  
	* __Consulte los puntos del plano correspondiente, y con los mismos dibuje consectivamente segmentos de recta, haciendo uso [de los elementos HTML5 (Canvas, 2DContext, etc) disponibles](https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_canvas_tut_path)* Actualice con jQuery el campo <div> donde se muestra el nombre del plano que se está dibujando (si dicho campo no existe, agruéguelo al DOM).__  
   Se implementó en app.js.

10. __Verifique que la aplicación ahora, además de mostrar el listado de los planos de un autor, permita seleccionar uno de éstos y graficarlo. Para esto, haga que en las filas generadas para el punto 5 incluyan en la última columna un botón con su evento de clic asociado a la operación hecha anteriormente (enviándo como parámetro los nombres correspondientes).__  
Se implmentó en el app.js.  

11. __Verifique que la aplicación ahora permita: consultar los planos de un auto y graficar aquel que se seleccione.__  
Al escribir el nombre del autor, obtener sus blueprints y dar click en el botón "open" de alguno de ellos, se dibuja el plano en el canvas:  
![img_15.png](img/img_15.png)  
Si abrimos otro blueprint, vemos que se borra el canvas y se dibuja el nuevo plano seleccionado:  
![img_16.png](img/img_16.png)  
Al cambiar de autor, y seleecionar uno de sus planos, se dibuja este correctamente:  
![img_17.png](img/img_17.png)  

12. __Una vez funcione la aplicación (sólo front-end), haga un módulo (llámelo 'apiclient') que tenga las mismas operaciones del 'apimock', pero que para las mismas use datos reales consultados del API REST. Para lo anterior revise [cómo hacer peticiones GET con jQuery](https://api.jquery.com/jquery.get/), y cómo se maneja el esquema de _callbacks_ en este contexto.__  
Se implementó el módulo apiclient con las mismas funciones, pero solicitando los recursos correspondientes a la API Rest elaborada en el laboratorio anterior.  
Probando el funcionamiento de la aplicación web, verificamos su correcto funcionamiento.  
![img_18.png](img/img_18.png)  
Con otro autor:  
![img_19.png](img/img_19.png)  


13. __Modifique el código de app.js de manera que sea posible cambiar entre el 'apimock' y el 'apiclient' con sólo una línea de código.__  
Para esto, creamos la variable currentModule que se modifica según el módulo que se desee usar, y que se emplea en los llamados a las funciones de los módulos.  
Si se usa apimock, se verifica el funcionamiento:  
![img_20.png](img/img_20.png)  
Si se usa apiclient, se verifica el funcionamiento:  
![img_21.png](img/img_21.png)
  
14. __Revise la [documentación y ejemplos de los estilos de Bootstrap](https://v4-alpha.getbootstrap.com/examples/) (ya incluidos en el ejercicio), agregue los elementos necesarios a la página para que sea más vistosa, y más cercana al mock dado al inicio del enunciado.__  
Se creó el archivo styles.css para que la página se asemeje más al mock.