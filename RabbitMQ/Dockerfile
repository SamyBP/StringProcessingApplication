FROM rabbitmq:3-management

ENV RABBITMQ_USER app
ENV RABBITMQ_PASSWORD app
ENV RABBITMQ_PID_FILE /var/lib/rabbitmq/mnesia/rabbitmq
ENV RABBITMQ_VHOST app.vhost

ADD init.sh /init.sh
RUN chmod +x /init.sh

CMD ["/init.sh"]