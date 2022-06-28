package com.luckwheat;

import io.micronaut.http.annotation.*;

@Controller("/notesStorageService")
public class NotesStorageServiceController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}