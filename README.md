<h1 align="center">Práctica 1. Despliegue de una aplicación Spring 👨🏻‍💻 </h1>

<p align="center">
  <a href="/docs" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
</p>

Proyecto para desplegar una aplicación utilizando los servicios de AWS.

## Authors

👤 **JuanCBM**: Juan Carlos Blázquez Muñoz

* Github: [@JuanCBM](https://github.com/JuanCBM)

👤 **mahuerta**: Miguel Ángel Huerta Rodríguez

* Github: [@mahuerta](https://github.com/mahuerta)

# Sobre la aplicación
- Adjuntamos el material de la presentación en la raíz del proyecto `presentacion_aws.pptx`
- Adjuntamos el vídeo de youtube donde explicamos la práctica XXXXXXXXXXXXX

# Comandos a tener en cuenta
- Instalaciones al acceder por primera vez a la instancia EC2
> sudo apt-get update

> sudo apt install -y openjdk-11-jdk

> java -version
- Instalar en EC2 mysql-client y realizar la conexión con password
> sudo apt-get install mysql-client

> mysql -h <ENDPOINT_RDS> -P 3306 -u admin -p

- Copia del JAR a la carpeta interna del EC2 
> scp -i <CLAVE_PEM> <NAME_ORIGIN_JAR> <EC2_NAME>@<EC2_DNS>:<DESTINATION_DIR_EC2>/<NAME_DESTINATION_JAR>

- Despliegue de la aplicación
> java -jar -Dspring.profiles.active=production target/jcblazquez-mahuerta-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:mysql://<RDS_ENDPOINT>/<DATABASE_NAME> --spring.datasource.username=admin --spring.datasource.password=password1 --amazon.s3.bucket-name=<BUCKET_NAME> --amazon.s3.endpoint=<S3_ENDPOINT> --amazon.s3.region=a<S3_REGION>

- Fuentes adicionales: Creación de imagen AMI de EC2
https://www.youtube.com/watch?v=kkdr8Av2cQQ
https://gist.github.com/marlonbernardes/eef26b818270ef3b6d02



## Creacion de la AMI con la app en ejecución al arrancar
Creación del script para la generacion del DAEMON:

1) Creación del file .service para el lanzamiento del DAEMON.

```nano /etc/systemd/system/MyProjectName.service```

### Demo.service
```
   [Unit]
    Description = Java App Demo Service

   [Service]
    Type=simple
    Restart=always
    RestartSec=1
    ExecStart = /usr/bin/java -jar -Dspring.profiles.active=production
      /home/ubuntu/awsDemo/appDemo.jar 
      --spring.datasource.url=jdbc:mysql://<RDS_ENPOINT>/<DATABASE_NAME> 
      --spring.datasource.username=admin 
      --spring.datasource.password=password1 
      --amazon.s3.bucket-name=<S3_NAME> 
      --amazon.s3.endpoint=https://s3.amazonaws.com/<S3_NAME> 
      --amazon.s3.region=<S3_region>
    SuccessExitStatus=143

   [Install]
    WantedBy=multi-user.target
```
2) Enable your new service.

```systemctl enable MyProjectName```

3) Start your service.

```systemctl start MyProjectName```

4) Check the status of your daemon.

```sudo systemctl status MyProjectName```

Una vez realizado estos pasos, actualmente ya tendriamos, 
una estancia EC2 de AWS,  con nuestra APP corriendo automaticamente en el despliegue.

Ahora iriamos a nuestra consola de AWS, panel de EC2, marcariamos nuestra Instancia y clickariamos en `Crear Imagen`

![image](https://user-images.githubusercontent.com/22147932/110395807-237c3580-806f-11eb-87b0-38057d0b0c9c.png)

Una vez que hemos realizado el seteo del nombre y la cantidad de GB que queremos para nuestra Imagen, en la secccion de AMIs nos aparecera nuestra nueva imagen creada a partir de nuestra instancia anterior con nuestra APP

![image](https://user-images.githubusercontent.com/22147932/110396062-9a193300-806f-11eb-8a5b-479ca1029001.png)

Una vez en el estado `available` nos dejara escogerla en la seleccion de AMIs para la creacion de instancias

![image](https://user-images.githubusercontent.com/22147932/110396180-d64c9380-806f-11eb-80be-f0c7058cba21.png)

Y ya por ultimo, una vez elegidos todos los pasos igual que si lanzasemos una instancia nueva, quedaria conectarnos a un Postman pasados un par de minutillos para ver que tenemos todo funcionando :D
