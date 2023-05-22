package com.propcool.cmpm_project.io.notebooks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
/**
 * Загрузчик экрана
 * */
public class NotebookLoader {
    public Notebook load(File path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        return (Notebook) in.readObject();
    }
}
