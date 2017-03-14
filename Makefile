all: jar mv

jar:
	@mvn  clean package

mv:
	@mv ./target/httpd.jar ./httpd

clean: rm_http  mvn_clean

mvn_clean:
	@mvn clean 
	
rm_http:
	@rm ./httpd
