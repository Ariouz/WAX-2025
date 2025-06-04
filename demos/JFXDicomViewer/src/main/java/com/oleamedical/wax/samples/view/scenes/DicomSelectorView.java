package com.oleamedical.wax.samples.view.scenes;

import com.oleamedical.wax.samples.ViewerUtils;
import com.oleamedical.wax.samples.controller.SceneController;
import com.oleamedical.wax.samples.controller.ViewerController;
import com.oleamedical.wax.samples.model.SceneEnum;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DicomSelectorView extends JFXView {

    @Override
    public Scene open(Stage stage, Scene scene, ViewerController viewerController) {
        HBox dicomSelector = new HBox(50);
        dicomSelector.setAlignment(Pos.CENTER);
        dicomSelector.setId("dicom-selector");

        Button selectDicomSeries = new Button("Select Dicom Series");
        selectDicomSeries.setOnAction(e -> {
            File file = viewerController.getSceneController().getViewerTemplate()
                    .createDirChooser("Select dicom series")
                    .showDialog(stage);
            if (!viewerController.setDicomSeries(file)) return ;
            openViewerScene(viewerController);
        });

        Button selectDicomFile = new Button("Select Dicom File");
        selectDicomFile.setOnAction(e -> {
            File file = viewerController.getSceneController().getViewerTemplate()
                    .createFileChooser("Select dicom file", "Dicom (.dcm)", "*.dcm")
                    .showOpenDialog(stage);
            if (!viewerController.addDicomFile(file)) return ;
            openViewerScene(viewerController);
        });

        dicomSelector.getChildren().add(selectDicomFile);
        dicomSelector.getChildren().add(selectDicomSeries);

        VBox root = (VBox) scene.getRoot();
        root.getChildren().add(dicomSelector);
        return scene;
    }

    private void openViewerScene(ViewerController viewerController) {
        try {
            viewerController.getSceneController().openScene(SceneEnum.DICOM_VIEWER_SCENE, viewerController);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
