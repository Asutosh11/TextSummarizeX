from flask import Flask, request, jsonify, flash, redirect, url_for
import smallModel, largeModel
from werkzeug.utils import secure_filename
import os
import fileReader


UPLOAD_FOLDER = '/uploads'
app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = {'txt', 'pdf', 'doc', 'docx'}

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
           

@app.route('/upload_file', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        
        file = request.files.get('file')        
        fileExtension = request.form.get('file_extension')
        
        if file and allowed_file(file.filename):
            
                if(fileExtension == 'txt'):
                    return fileReader.readTxt(file)
                
                elif(fileExtension == 'pdf'):
                    return fileReader.readPdf(file)
                
                elif(fileExtension == 'docx'):
                    return fileReader.readDocx(file)
                
                elif(fileExtension == 'doc'):
                    return ""
        else:
            return "null"


@app.route('/')
def display():
    return "Looks like it works!"

# API Request from front wend is - 
# URL - 127.0.0.1:5000/json-example  
# Raw JSON Request - {"language":"french", "framework":"flask"}

@app.route('/summarize-small-model', methods=['POST']) 
def summarize_small_model():
    req_data = request.get_json(force=True)
    
    text = req_data['text']
    result = smallModel.summarize(text)
    
    return jsonify(summary = result)


@app.route('/summarize-large-model', methods=['POST']) 
def summarize_large_model():
    req_data = request.get_json(force=True)
    
    text = req_data['text']
    result = largeModel.summarize(text)
    
    return jsonify(summary = result)


if __name__=='__main__':
    app.run(host='0.0.0.0')
    

# Response is - 
# {"framework": "flask is robust", "language": "hindi is nice"}    