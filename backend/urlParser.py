from bs4 import BeautifulSoup
from bs4.element import Comment
from urllib.request import Request, urlopen

def tag_visible(element):
    if element.parent.name in ['style', 'script', 'head', 'title', 'meta', '[document]']:
        return False
    if isinstance(element, Comment):
        return False
    return True


# this is the function that has to be called for get text from a webpage URL
# Other functions in this file are just helper functions
def getTextFromURL(url):
    hdr = {'User-Agent': 'Mozilla/5.0'}
    req = Request(url,headers=hdr)
    page = urlopen(req)
    soup = BeautifulSoup(page)
    texts = soup.prettify()
    texts = soup.findAll(text=True)
    visible_texts = filter(tag_visible, texts)  
    final_text = u" ".join(t.strip() for t in visible_texts)
    return (final_text.lstrip())