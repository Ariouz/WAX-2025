package com.oleamedical.wax.samples.gol;

import com.oleamedical.wax.samples.gol.simulation.GOLSimulation;

public class Application {

    public static int WIDTH = 50;
    public static int HEIGHT = 50;
    public static int ITERATIONS = 1000;
    public static boolean DISPLAY = false;

    public static void main(String[] args) throws Exception {
        if (System.getProperty("width") != null) WIDTH = Integer.parseInt(System.getProperty("width"));
        if (System.getProperty("height") != null) HEIGHT = Integer.parseInt(System.getProperty("height"));
        if (System.getProperty("iterations") != null) ITERATIONS = Integer.parseInt(System.getProperty("iterations"));
        if (System.getProperty("display") != null) DISPLAY = Boolean.parseBoolean(System.getProperty("display"));

        long startTime = System.currentTimeMillis();
        new GOLSimulation(WIDTH, HEIGHT)
                .simulate(ITERATIONS);

        System.out.println("Grid size: " + WIDTH + " x " + HEIGHT);
        System.out.println("Iterations: " + ITERATIONS);
        System.out.println("Simulation time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

}


