all: rm_httpd jar mv

jar:
	@mvn  clean package

mv:
	@mv ./target/httpd.jar ./httpd

clean: rm_httpd  mvn_clean

mvn_clean:
	@mvn clean

rm_httpd:
	@rm -f ./httpd
