package com.oleamedical.wax.samples;

import com.oleamedical.wax.samples.controller.SceneController;
import com.oleamedical.wax.samples.controller.ViewerController;
import com.oleamedical.wax.samples.model.SceneEnum;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class JFXDicomViewer extends Application {

    private File dcmFile = null;

    private ViewerController viewerController;
    private SceneController sceneController;

    public void start(Stage stage) throws Exception {
        sceneController = new SceneController(stage);
        viewerController = new ViewerController(sceneController);
        sceneController.createScene(SceneEnum.DICOM_SELECTOR_SCENE);
        sceneController.createScene(SceneEnum.DICOM_VIEWER_SCENE);

        sceneController.openScene(SceneEnum.DICOM_SELECTOR_SCENE, viewerController);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}