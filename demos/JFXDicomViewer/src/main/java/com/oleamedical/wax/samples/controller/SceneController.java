package com.oleamedical.wax.samples.controller;

import com.oleamedical.wax.samples.ViewerTemplate;
import com.oleamedical.wax.samples.ViewerUtils;
import com.oleamedical.wax.samples.model.SceneEnum;
import com.oleamedical.wax.samples.view.scenes.JFXView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {


    private final Stage stage;
    private final HashMap<SceneEnum, Scene> scenes = new HashMap<>();
    private final ViewerTemplate viewerTemplate;

    public SceneController(Stage stage) {
        this.stage = stage;
        this.viewerTemplate = new ViewerTemplate(stage, this);
    }

    public Scene getActiveScene() {
        return stage.getScene();
    }

    public void openScene(SceneEnum sceneEnum, ViewerController viewerController) throws Exception {
        if (!scenes.containsKey(sceneEnum)) throw new NullPointerException("Cannot open an unregistered scene " + sceneEnum);

        Scene scene = scenes.get(sceneEnum);
        Class<?> clazz = sceneEnum.getClazz();
        JFXView viewInstance = (JFXView) clazz.getConstructor().newInstance();

        Pane root = (Pane) scene.getRoot();
        Node node = root.getChildren().get(0);
        root.getChildren().clear();
        root.getChildren().add(node);

        if (viewInstance.open(stage, scene, viewerController) == null) return ;
        stage.setScene(scene);
    }

    public void createScene(SceneEnum sceneEnum) {
        if (scenes.containsKey(sceneEnum)) throw new IllegalArgumentException("Scene "+sceneEnum+" already exists");
        Scene scene = viewerTemplate.createScene();
        registerScene(scene, sceneEnum);
    }

    private void registerScene(Scene scene, SceneEnum sceneEnum) {
        if (scenes.containsKey(sceneEnum)) return ;
        scenes.put(sceneEnum, scene);
    }

    public ViewerTemplate getViewerTemplate() {
        return viewerTemplate;
    }

}
