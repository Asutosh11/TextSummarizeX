#!/usr/bin/env python
# -*- coding: utf-8 -*- 

import trafilatura


def getTextFromURL(url):
    try:
       downloaded = trafilatura.fetch_url(url)
       return(trafilatura.extract(downloaded))
    except:
        return""  