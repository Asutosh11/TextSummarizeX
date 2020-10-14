#!/usr/bin/env python
# -*- coding: utf-8 -*- 

from fastapi import FastAPI, Form, Request, File, UploadFile
from fastapi.responses import PlainTextResponse, HTMLResponse, FileResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
from pydantic import BaseModel
import random
import uvicorn

import bertExtractiveSummarizer
import fileReader
import urlParser
import re

import models

import logging
print(logging.__file__)

UPLOAD_FOLDER = '/uploads'

app = FastAPI()

ALLOWED_EXTENSIONS = {'txt', 'pdf', 'doc', 'docx'}

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
           
@app.get("/", response_class=PlainTextResponse)
async def hello():
    return "It works !!"
           

@app.post('/summary_from_file', response_class=PlainTextResponse)
async def upload_file(fileInBytes: bytes = File(...), fraction_of_original_text_in_summary: str = Form(...), file_extension: str = Form(...)):

    with open("myfile", "rb") as fileInBytes:
        file = fileInBytes.read(1)
        while file != b"":
        # Do stuff with byte.
            file = file.read(1)

    fileExtension = file_extension
    fraction_of_original_text_in_summary = fraction_of_original_text_in_summary
 
    if(fileExtension == 'txt'):
        raw_txt = fileReader.readTxt(file)
        raw_txt = raw_txt.decode()
    
    elif(fileExtension == 'pdf'):
        raw_txt = fileReader.readPdf(file)
    
    elif(fileExtension == 'docx'):
        raw_txt = fileReader.readDocx(file)
    
    elif(fileExtension == 'doc'):
        raw_txt = fileReader.readDocx(file)
              
    return summarize(raw_txt, fraction_of_original_text_in_summary)
  
        
        
@app.post('/summary_from_url', response_class=PlainTextResponse)
async def summary_from_url(request: models.SUMMARY_FROM_URL_REQUEST):
    url = request.url
    fraction_of_original_text_in_summary = request.fraction_of_original_text_in_summary
    text_from_webpage = urlParser.getTextFromURL(url)
    return summarize(text_from_webpage, fraction_of_original_text_in_summary)


@app.post('/summary_from_text', response_class=PlainTextResponse)
async def summary_from_text(request: models.SUMMARY_FROM_TEXT_REQUEST):
    text = request.text
    fraction_of_original_text_in_summary = request.fraction_of_original_text_in_summary
    return summarize(text, fraction_of_original_text_in_summary)
    
    
def summarize(raw_txt, fraction_of_original_text_in_summary):
    stripped_text = raw_txt.strip().replace("\n","")
    summary = bertExtractiveSummarizer.summarize(stripped_text, fraction_of_original_text_in_summary)
    txt_no_special_chars_in_start = re.sub(r"^\W+", "", summary)
    txt_broken_into_paras = makeParagraph(3, txt_no_special_chars_in_start)
    return txt_broken_into_paras

def makeParagraph(full_stops_in_one_para, input_string):
    number_of_fullstops = 0
    a = ''
    for i in input_string:
        if i=='.':
            number_of_fullstops = number_of_fullstops + 1
            a = a+i
            if(number_of_fullstops == full_stops_in_one_para):
                a = a + ' \n\n'
                number_of_fullstops = 0
        else:
            a+=i
    return(a)



if __name__ == '__main__':
    uvicorn.run('api:app', host='0.0.0.0', port=8000)