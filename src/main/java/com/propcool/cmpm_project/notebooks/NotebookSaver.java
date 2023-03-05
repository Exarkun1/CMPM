package com.propcool.cmpm_project.notebooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NotebookSaver {
    public void save(Notebook notebook, File path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(notebook);
    }
}
