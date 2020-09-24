#!/usr/bin/env python
# -*- coding: utf-8 -*- 

from summarizer import Summarizer

def summarize(text, fraction_of_original_text_in_summary):

    fraction_of_original_text_in_summary = float(fraction_of_original_text_in_summary)
    model = Summarizer()
    result = model(text, min_length=40, ratio=fraction_of_original_text_in_summary)
    fullOutput = ''.join(result)

    return fullOutput
