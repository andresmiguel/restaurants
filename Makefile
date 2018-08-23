docker-clean:
	docker-compose stop
	docker-compose rm -f -v

docker-package-builder: docker-clean
	mvn -DskipTests clean package
	docker-compose build

docker-run: docker-package-builder
	docker-compose up

run:
	mvn spring-boot:run