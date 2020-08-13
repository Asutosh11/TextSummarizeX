from flask import Flask, request, redirect, url_for
import smallModel, largeModel
import fileReader
import re



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
                    raw_txt = fileReader.readTxt(file)
                
                elif(fileExtension == 'pdf'):
                    raw_txt = fileReader.readPdf(file)
                
                elif(fileExtension == 'docx'):
                    raw_txt = fileReader.readDocx(file)
                
                elif(fileExtension == 'doc'):
                    raw_txt = fileReader.readDocx(file)
                          
                decoded_txt = raw_txt.decode()
                stripped_text = decoded_txt.strip().replace("\n","")
                summary = largeModel.summarize(stripped_text)
                txt_no_special_chars_in_start = re.sub(r"^\W+", "", summary)
                return txt_no_special_chars_in_start
        else:
            return "null"


@app.route('/')
def display():
    return "Looks like it works!"

if __name__=='__main__':
    app.run(host='0.0.0.0')