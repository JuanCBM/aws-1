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



## Intento de ejecución del script al arrancar
Creación del script para ejecución de la app al desplegar:

1) Creación de los scripts de inicio y parada de la aplicación.
- Ejemplo:
### demo-start.sh
```sh
#!/bin/bash
cd /home/ubuntu/awsDemo/
java -jar -Dspring.profiles.active=production target/jcblazquez-mahuerta-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:mysql://<RDS_ENDPOINT>/<DATABASE_NAME> --spring.datasource.username=admin --spring.datasource.password=password1 --amazon.s3.bucket-name=<BUCKET_NAME> --amazon.s3.endpoint=<S3_ENDPOINT> --amazon.s3.region=a<S3_REGION>
```

### demo-stop.sh
```sh
#!/bin/bash
sudo fuser 8443/tcp -k || true
```

2) Create a file named `demo` inside /etc/init.d/
```sh
#!/bin/bash

case $1 in
    start)
        /bin/bash /home/ubuntu/scripts/demo-start.sh
    ;;
    stop)
        /bin/bash /home/ubuntu/scripts/demo-stop.sh  
    ;;
    restart)
        /bin/bash /home/ubuntu/scripts/demo-stop.sh
        /bin/bash /home/ubuntu/scripts/demo-start.sh
    ;;
esac
exit 0
```

3) Mark `demo` as executable:
```sh
cd /etc/init.d/
sudo chmod +x demo 
```

4) Make the script start on boot:

```
sudo systemctl start demo
```