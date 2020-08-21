from bs4 import BeautifulSoup
from bs4.element import Comment
import urllib.request
import requests


def tag_visible(element):
    if element.parent.name in ['style', 'script', 'head', 'title', 'meta', '[document]']:
        return False
    if isinstance(element, Comment):
        return False
    return True


def text_from_html(body):
    soup = BeautifulSoup(body, 'html.parser')
    texts = soup.findAll(text=True)
    visible_texts = filter(tag_visible, texts)  
    return u" ".join(t.strip() for t in visible_texts)

# this is the function that has to be called for get text from a webpage URL
# Other functions in this file are just helper functions
def getTextFromURL(url):
    parsed_text = ""

    r=requests.get(url)
    
    if(r.status_code == 200):
        html = urllib.request.urlopen(url).read()
        parsed_text = text_from_html(html)

    else:
        parsed_text = ""

    return parsed_text