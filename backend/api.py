#!/usr/bin/env python
# -*- coding: utf-8 -*- 
from flask import Flask, request
import bertExtractiveSummarizer
import fileReader
import urlParser
import re

import logging
print(logging.__file__)

UPLOAD_FOLDER = '/uploads'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

ALLOWED_EXTENSIONS = {'txt', 'pdf', 'doc', 'docx'}

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
           
@app.route('/', methods=['GET', 'POST'])
def test():
    return "works fine"
           

@app.route('/summary_from_file', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        
        file = request.files.get('file')        
        fileExtension = request.form.get('file_extension')
        fraction_of_original_text_in_summary = request.form.get('fraction_of_original_text_in_summary')
 
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
      
        
        
@app.route('/summary_from_url', methods=['GET', 'POST'])
def summary_from_url():
    req_data = request.get_json(force=True)
    url = req_data['url']
    fraction_of_original_text_in_summary = req_data['fraction_of_original_text_in_summary']
    text_from_webpage = urlParser.getTextFromURL(url)
    return summarize(text_from_webpage, fraction_of_original_text_in_summary)


@app.route('/summary_from_text', methods=['GET', 'POST'])
def summary_from_text():
    req_data = request.get_json(force=True)
    text = req_data['text']
    fraction_of_original_text_in_summary = req_data['fraction_of_original_text_in_summary']
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



if __name__=='__main__':
    app.run()
