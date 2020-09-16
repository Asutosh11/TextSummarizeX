package com.thoughtleaf.textsummarizex.data.model

import java.io.File

data class FileRequest (var file: File, var file_extension: String, var fraction_of_original_text_in_summary: Float)