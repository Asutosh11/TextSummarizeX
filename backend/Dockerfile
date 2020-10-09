FROM ubuntu:18.04
FROM python:3
RUN apt-get update -y && apt-get install -y python-pip python-dev
COPY . /backend
WORKDIR /backend
RUN pip3 install -r requirements.txt
EXPOSE 8000
ENTRYPOINT ["gunicorn"]
CMD ["-w", "4", "-t", "10000", "-b", "0.0.0.0:8000", "wsgi:app"]
