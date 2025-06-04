package com.oleamedical.wax.samples.controller;

import com.oleamedical.wax.samples.ViewerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewerController {

    private final SceneController sceneController;

    private List<File> dicomSeries = new ArrayList<>();

    public ViewerController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public boolean addDicomFile(File dicomFile) {
        if (dicomFile == null) return false;
        if (!dicomFile.canRead()) {
            ViewerUtils.openErrorDialog("Cannot read dicom file");
            return false;
        }
        this.dicomSeries.add(dicomFile);
        return true;
    }

    public boolean setDicomSeries(File dir) {
        if (dir == null || !dir.isDirectory()) return false;

        File[] seriesFiles = dir.listFiles(file -> file.getName().endsWith(".dcm"));
        if (seriesFiles == null) {
            ViewerUtils.openErrorDialog("No files found in " + dir.getAbsolutePath());
            return false;
        }

        for (File file : seriesFiles) {
            if (!addDicomFile(file)) return false;
        }

        if (dicomSeries.isEmpty()) {
            ViewerUtils.openErrorDialog("Series is empty (no dicom file at root)");
            return false;
        }

        dicomSeries.sort(Comparator.naturalOrder());
        return true;
    }

    public File getDicomFile(int index) {
        return dicomSeries.get(index);
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public int getDicomSeriesSize() {
        return this.dicomSeries.size();
    }
}
