package com.java2d.pracs;

public class OpenSimplexNoise {
    private OpenSimplexNoiseKS generator;

    public final static String VERSION = "##library.prettyVersion##";


    /**
     * Constructs a new OpenSimplexNoise object,
     * using the system's current time as the noise seed.
     */
    public OpenSimplexNoise() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructs a new OpenSimplexNoise object,
     * using the provided value as the noise seed.
     */
    public OpenSimplexNoise(long seed) {
        welcome();
        generator = new OpenSimplexNoiseKS(seed);
    }


    private void welcome() {
        System.out.println("##library.name## ##library.prettyVersion## by ##author##");
    }

    private double remap(double val) {
        return (val + 1) * 0.5;
    }


    public float noise (float xoff) {
        return this.noise(xoff, 0);
    }

    public float noise (float xoff, float yoff) {
        return (float) remap(generator.eval(xoff, yoff));
    }

    public float noise (float xoff, float yoff, float zoff) {
        return (float) remap(generator.eval(xoff, yoff, zoff));
    }

    public float noise (float xoff, float yoff, float zoff, float uoff) {
        return (float) remap(generator.eval(xoff, yoff, zoff, uoff));
    }


    /**
     * return the version of the Library.
     *
     * @return String
     */
    public static String version() {
        return VERSION;
    }
}
