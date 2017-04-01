FROM lwieske/java-8:latest

ARG home_dir=/home/mail

RUN mkdir ${home_dir}
WORKDIR ${home_dir}

RUN mkdir ${home_dir}/static
ADD . ${home_dir}

RUN apk update && \ 
    apk add make && \
    apk add maven

RUN make

VOLUME ${home_dir}/static
EXPOSE 80

CMD java -jar $(pwd)/httpd -p 80 -r $(pwd)/static
