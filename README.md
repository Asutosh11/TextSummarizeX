# TextSummarizeX

<h3>How to make the docker image and run it</h3>


1. Go to your app directory
2. make a file named DockerFile with these contents - 

```
FROM ubuntu:18.04
RUN apt-get update -y && \
    apt-get install -y python-pip python-dev
COPY . /backend
WORKDIR /backend
RUN pip install -r requirements.txt
ENTRYPOINT [ "python" ]
CMD [ "api.py" ]
EXPOSE 8000
CMD gunicorn api:app -k gevent --worker-connections 1000
```

```
< Don't copy this>

above params explained::
i. backend - is the folder for the python project
ii. requirements.txt - where all dependencies are listed for the project
iii. api.py - entry point to run the app, in my case its a flask app
iv. CMD - command to run the app, in my case to run api.py

</>
```

3. ```sudo docker build -t textsummarizex:latest .```
4. ```docker images``` 
5. copy the id of the docker image you just built, it will be listed on the screen
6. ```mkdir ~/docker-images```
7. ```cd ~/docker-images```
8. ```chmod 777 ./```
9. ```sudo docker save <docker_img_id_of_ur_file_u_just_copied> -o ./<new_filename>```

Now you have a docker image ready to be used


 
