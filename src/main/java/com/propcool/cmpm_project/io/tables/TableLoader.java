package com.propcool.cmpm_project.io.tables;

import com.propcool.cmpm_project.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class TableLoader {
    public Set<Point> load(File path) throws FileNotFoundException {
        Set<Point> points = new LinkedHashSet<>();
        Scanner in = new Scanner(path);
        in.useDelimiter("\n");
        int index = 0;
        while(in.hasNext()) {
            Scanner line = new Scanner(in.next());
            double x = Double.parseDouble(line.next());
            double y = Double.parseDouble(line.next());
            if(line.hasNext()) throw new InputMismatchException();
            points.add(new Point(x, y));
            index++;
        }
        if(index == 0) throw new InputMismatchException();
        return points;
    }
}
