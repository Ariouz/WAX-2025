package com.oleamedical.wax.samples;

import com.oleamedical.wax.samples.controller.SceneController;
import com.oleamedical.wax.samples.controller.ViewerController;
import com.oleamedical.wax.samples.model.SceneEnum;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

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
