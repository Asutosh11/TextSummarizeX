<h2>TextSummarizeX</h2>

<h3>How to run the Flask app as prod, locally using gunicorn?</h3>

```bash
gunicorn --bind 0.0.0.0:8000  --workers=4 wsgi:app --timeout 10000
```

<h3>Dockerize your Flask app</h3>


1. Go to your app directory.
2. make a file named DockerFile with these contents - 

```bash
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

3. Now open terminal and enter these commands

```bash 
sudo docker build -t textsummarizex:1.16 .
   ```
```bash 
docker images
   ``` 
Copy the name and tag of the docker image you just built, it will be listed on the screen. In our case now, it is <b>textsummarizex:1.16</b> 
```bash
sudo docker run -it -p 8000:8000 textsummarizex:1.16
   ```

4. Now you have a docker image ready and running on ur local.<br>Need to push that to docker hub, so that you can pull it from a linux server and run it


```bash 
sudo -s 
   ```
   
```bash 
docker login
   ```
   
```bash 
docker images
   ```
   
```bash
docker tag ea41543608f0 thoughtleaf/textsummarizex:1.16 
```
   
```bash 
docker push thoughtleaf/textsummarizex
```
<br>

<h3>How to delete all Docker images in the system?</h3>

```bash 
docker system prune -a
```
