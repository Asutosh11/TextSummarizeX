FROM ubuntu:18.04
FROM python:3
RUN apt-get update -y && apt-get install -y python-pip python-dev
COPY . /backend
WORKDIR /backend
RUN pip3 install -r requirements.txt
EXPOSE 8000
CMD gunicorn --timeout 1000 --workers 1 --threads 4 --log-level debug --bind 0.0.0.0:8000 wsgi:app
