# TextSummarizeX

<h3>How to make the docker image and run it</h3>


1. Go to your app directory
2. make a file named DockerFile with these contents - 

```
FROM ubuntu:18.04
FROM python:3
RUN apt-get update -y && apt-get install -y python-pip python-dev
COPY . /backend
WORKDIR /backend
RUN pip3 install -r requirements.txt
EXPOSE 8000
CMD gunicorn api:app -k gevent --worker-connections 1000
```

```
<Don't copy this>

above params explained::
i. backend - is the folder for the python project
ii. requirements.txt - where all dependencies are listed for the project
iii. CMD - command to run the app, in my case to run api.py

</>
```

3. ```sudo docker build -t textsummarizex:1.16 .```
4. ```docker images``` 
5. Copy the name and tag of the docker image you just built, it will be listed on the screen. In our case now, it is <b>textsummarizex:1.16</b> 
6. ```sudo docker run -it textsummarizex:1.16```

<b>Now you have a docker image ready and running on ur local.<br>Need to push that to docker hub, so that you can pull it from a linux server and run it</b>

7. ```sudo -s```
8. ```docker login```
9. ```docker images```
10. ```docker tag ea41543608f0 thoughtleaf/textsummarizex:1.16```
11. ```docker push thoughtleaf/textsummarizex```
