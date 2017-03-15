FROM lwieske/java-8:latest

ARG home_dir=/home/1C_corp

CMD mkdir ${home_dir}
WORKDIR ${home_dir}

CMD mkdir ${home_dir}/bin
CMD mkdir ${home_dir}/static
ADD ./target/ ${home_dir}/bin

VOLUME ${home_dir}/static
EXPOSE 80

CMD java -jar ./bin/httpd.jar -p 80 -r ${home_dir}/static
