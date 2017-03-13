all: jar mv  cp

jar:
	@mvn  clean package

mv:
	@mv ./target/httpd.jar ./target/httpd

cp:
	@cp ./target/httpd ./httpd

clean: rm_http  mvn_clean

mvn_clean:
	@mvn clean 
	
rm_http:
	@rm ./httpd
