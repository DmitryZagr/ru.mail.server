all: jar mv 

jar:
	@mvn  clean package

mv:
	@mv ./target/httpd.jar ./target/httpd

clean:
	@mvn clean 

