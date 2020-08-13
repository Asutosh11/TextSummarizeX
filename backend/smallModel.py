import torch
import os
from transformers import T5Tokenizer, T5ForConditionalGeneration

def summarize(text):

    model = T5ForConditionalGeneration.from_pretrained('t5-small')
    tokenizer = T5Tokenizer.from_pretrained('t5-small')
    device = torch.device('cpu')
    
    tokenized_text = tokenizer.encode(text, return_tensors="pt").to(device)
    
    
    # summmarize 
    summary_ids = model.generate(tokenized_text,
                                        num_beams=4,
                                        no_repeat_ngram_size=2,
                                        min_length=30,
                                        max_length=100,
                                        early_stopping=True)
    
    output = tokenizer.decode(summary_ids[0], skip_special_tokens=True)
    output = output.replace("\n", "")
    
    return output
