all: jar 

jar:
	@mvn  clean package

clean:
	:@mvn clean 

