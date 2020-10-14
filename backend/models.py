#!/usr/bin/env python
# -*- coding: utf-8 -*-

from pydantic import BaseModel 
from fastapi import FastAPI, File, UploadFile, Form

class SUMMARY_FROM_URL_REQUEST(BaseModel):
    url: str
    fraction_of_original_text_in_summary: str
    
class SUMMARY_FROM_TEXT_REQUEST(BaseModel):
    text: str
    fraction_of_original_text_in_summary: str
    
class SUMMARY_FROM_FILE_REQUEST(BaseModel):
    file_extension: str = Form(...)
    fraction_of_original_text_in_summary: str = Form(...)
    
class PydanticFile(BaseModel):
    file: UploadFile = File(...)    