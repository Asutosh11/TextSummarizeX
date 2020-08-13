from docx import Document
import PyPDF2


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


ALLOWED_EXTENSIONS = {'txt', 'pdf', 'doc', 'dox'}


def readTxt(fileName):
    return fileName.read()

def readDocx(filename):
    doc = Document(filename)
    txt = ""
    for para in doc.paragraphs:
        txt = txt + para.text
    return txt

def readPdf(filename):
    pdfReader = PyPDF2.PdfFileReader(filename)
    count = pdfReader.numPages
    txt = ""
    for i in range(count):
        page = pdfReader.getPage(i)
        txt = txt + page.extractText()
    return txt