from flask import Flask, request
import bertExtractiveSummarizer
import fileReader, urlParser
import re
from gevent.pywsgi import WSGIServer


UPLOAD_FOLDER = '/uploads'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

ALLOWED_EXTENSIONS = {'txt', 'pdf', 'doc', 'docx'}

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
           

@app.route('/summary_from_file', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        
        file = request.files.get('file')        
        fileExtension = request.form.get('file_extension')
        
        if file and allowed_file(file.filename):
            
                if(fileExtension == 'txt'):
                    raw_txt = fileReader.readTxt(file)
                    raw_txt = raw_txt.decode()
                
                elif(fileExtension == 'pdf'):
                    raw_txt = fileReader.readPdf(file)
                
                elif(fileExtension == 'docx'):
                    raw_txt = fileReader.readDocx(file)
                
                elif(fileExtension == 'doc'):
                    raw_txt = fileReader.readDocx(file)
                          
                return summarize(raw_txt)
              
        else:
            return "null"
        
        
@app.route('/summary_from_url', methods=['GET', 'POST'])
def summary_from_url():
    req_data = request.get_json(force=True)
    url = req_data['url']
    text_from_webpage = urlParser.getTextFromURL(url)
    return summarize(text_from_webpage)


@app.route('/summary_from_text', methods=['GET', 'POST'])
def summary_from_text():
    req_data = request.get_json(force=True)
    text = req_data['text']
    return summarize(text)
    
    
def summarize(raw_txt):
    stripped_text = raw_txt.strip().replace("\n","")
    summary = bertExtractiveSummarizer.summarize(stripped_text)
    txt_no_special_chars_in_start = re.sub(r"^\W+", "", summary)
    return txt_no_special_chars_in_start



if __name__=='__main__':
    # app.run(host='0.0.0.0', threaded="true")
    http_server = WSGIServer(('127.0.0.1', 8000), app)
    http_server.serve_forever()