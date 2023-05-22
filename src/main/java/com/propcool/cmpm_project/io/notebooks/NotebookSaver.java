package com.propcool.cmpm_project.io.notebooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * Сохранщик экрана
 * */
public class NotebookSaver {
    public void save(Notebook notebook, File path) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(notebook);
    }
}
