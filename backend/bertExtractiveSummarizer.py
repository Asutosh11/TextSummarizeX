from summarizer import Summarizer

def summarize(text):

    model = Summarizer()
    result = model(text, min_length=40, ratio=0.1)
    fullOutput = ''.join(result)

    return fullOutput
