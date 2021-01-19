FROM mongo:4.0.20

RUN apt-get update && apt-get -y install curl

RUN mkdir /scripts

COPY ./script_init.sh /scripts/script_init.sh

COPY ./script_user.sh /scripts/script_user.sh

RUN chmod +x /scripts/*

CMD ["./scripts/script_init.sh"]