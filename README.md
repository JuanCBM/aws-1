# aws-1

#Comandos a tener en cuenta, en un futuro generalizados

-Intalar en EC2 mysql-client y realizar la conexion con password

`mysql -h <<ENDPOINT_RDS>> -P 3306 -u admin -p`

-Copia del JAR a la carpeta interna del EC2
`scp -i <<TU_CLAVE_PEM>> <<ARCHIVO_JAR>> <<EC2_NAME>>@<<EC2_DNS>>:<<DESTINATION_DIR_EC2>>/<<NAME_DESTINATION_JAR>>`


-Nos conectamos a EC2 Y obtenemos el JAR con `wget` guardandolo en un directorio, al que accedemos y lanzamos:

```sudo java -jar -Dspring.profiles.active=production practica_1_cloud_ordinaria_2021-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:mysql://<<AWS_RDS_ENDPOINT>>/<<DB_INSTANCE_NAME>> --spring.datasource.username=admin --spring.datasource.password=password1 --amazon.s3.bucket-name=<<BUCKET_NAME>> --amazon.s3.endpoint=https://s3.amazonaws.com/<<BUCKET_NAME>> --amazon.s3.region=<<REGION>>```

-Link al video de Yoputube que genera la creacion de AMI image of EC2

https://www.youtube.com/watch?v=kkdr8Av2cQQ
