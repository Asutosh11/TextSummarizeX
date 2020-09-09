from summarizer import Summarizer

def summarize(text, fraction_of_original_text):

    fraction_of_original_text = float(fraction_of_original_text)
    model = Summarizer()
    result = model(text, min_length=40, ratio=fraction_of_original_text)
    fullOutput = ''.join(result)

    return fullOutput
