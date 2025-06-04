package com.oleamedical.wax.samples.view.scenes;

import com.oleamedical.wax.samples.ViewerUtils;
import com.oleamedical.wax.samples.controller.ViewerController;
import com.oleamedical.wax.samples.dicom.DicomReader;
import com.oleamedical.wax.samples.model.SceneEnum;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class DicomViewerView extends JFXView {

    @Override
    public Scene open(Stage stage, Scene scene, ViewerController viewerController) {
        VBox viewerContainer = new VBox();
        viewerContainer.setSpacing(10);
        viewerContainer.setAlignment(Pos.CENTER);

        HBox dcmFileInfo = new HBox();
        dcmFileInfo.setAlignment(Pos.CENTER);

        Label selectedDicom = new Label("Selected Dicom: ");
        selectedDicom.getStyleClass().addAll("text", "text-orange");

        Label dicomName = new Label(viewerController.getDicomFile(0).getName());
        dicomName.getStyleClass().add("text");

        dcmFileInfo.getChildren().add(selectedDicom);
        dcmFileInfo.getChildren().add(dicomName);
        viewerContainer.getChildren().add(dcmFileInfo);

        ImageView imageView = new ImageView();
        imageView.setImage(getImage(0, viewerController));
        imageView.setFitHeight(250);
        viewerContainer.getChildren().add(imageView);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(viewerController.getDicomSeriesSize() - 1);
        slider.setShowTickLabels(true);
        slider.setValue(0);
        slider.setMaxWidth(200);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(getImage(newValue.intValue(), viewerController));
            dicomName.setText(viewerController.getDicomFile(newValue.intValue()).getName());
        });

        viewerContainer.getChildren().add(slider);

        Button openOther = new Button("Open Other");
        openOther.setOnAction(event -> {
            try {
                viewerController.getSceneController().openScene(SceneEnum.DICOM_SELECTOR_SCENE, viewerController);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        viewerContainer.getChildren().add(openOther);

        VBox root = (VBox) scene.getRoot();
        root.getChildren().add(viewerContainer);
        return scene;
    }

    private Image getImage(int index, ViewerController viewerController) {
        BufferedImage dcmBufferedImage;
        try {
            dcmBufferedImage = new DicomReader().readDicom(viewerController.getDicomFile(index));
        } catch (IOException e) {
            ViewerUtils.openErrorDialog("Failed to read dicom image");
            return null;
        }
        return SwingFXUtils.toFXImage(dcmBufferedImage, null);
    }

}
