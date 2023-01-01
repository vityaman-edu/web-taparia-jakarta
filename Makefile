.ONESHELL: clean war infra

clean:
	mvn clean

prepare-app:
	cd taparia-app/src/main/webapp
	tsc
	cd ../../../..

prepare-api:


wars: prepare-api prepare-app
	mvn package

infra: clean wars Dockerfile docker-compose.yml
	sudo docker compose down
	sudo docker compose build --no-cache
	sudo docker compose up --build --force-recreate

infra-stop:
	docker compose stop
