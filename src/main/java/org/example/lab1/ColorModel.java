package org.example.lab1;

import javafx.scene.control.ColorPicker;

public class ColorModel {

    private double r, g, b; // RGB
    private double c, m, y, k; // CMYK
    private double h, s, v; // HSV


    // Геттеры и сеттеры для CMYK
    public double getC() { return c; }
    public void setC(double c) { this.c = c; cmykToRgb(); rgbToHsv();}


    public double getM() { return m; }
    public void setM(double m) { this.m = m; cmykToRgb(); rgbToHsv();}

    public double getY() { return y; }
    public void setY(double y) { this.y = y; cmykToRgb(); rgbToHsv();}

    public double getK() { return k; }
    public void setK(double k) { this.k = k; cmykToRgb(); rgbToHsv();}

    // Геттеры и сеттеры для HSV
    public double getH() { return h; }
    public void setH(double h) { this.h = h; hsvToRgb(); rgbToCmyk();}

    public double getS() { return s; }
    public void setS(double s) { this.s = s; hsvToRgb(); rgbToCmyk();}

    public double getV() { return v; }
    public void setV(double v) { this.v = v; hsvToRgb(); rgbToCmyk();}

    // Геттеры и сеттеры для RGB
    public double getR() { return r; }
    public void setR(double r) { this.r = r; rgbToCmyk(); rgbToHsv(); }

    public double getG() { return g; }
    public void setG(double g) { this.g = g; rgbToCmyk(); rgbToHsv(); }

    public double getB() { return b; }
    public void setB(double b) { this.b = b; rgbToCmyk(); rgbToHsv(); }

    // Конструктор для инициализации значений
    public ColorModel(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
        rgbToCmyk();
        rgbToHsv();
    }

    // Метод для пересчета RGB -> CMYK
    public void rgbToCmyk() {
        double rPercent = r / 255.0;
        double gPercent = g / 255.0;
        double bPercent = b / 255.0;

        double kLocal = 1 - Math.max(rPercent, Math.max(gPercent, bPercent));
        this.k = kLocal * 100;

        if (kLocal < 1) {
            this.c = (1 - rPercent - kLocal) / (1 - kLocal) * 100;
            this.m = (1 - gPercent - kLocal) / (1 - kLocal) * 100;
            this.y = (1 - bPercent - kLocal) / (1 - kLocal) * 100;
        }
        else {
            this.c = 0;
            this.m = 0;
            this.y = 0;
        }
    }

    // Метод для пересчета RGB -> HSV
    public void rgbToHsv() {
        double rNorm = r / 255.0;
        double gNorm = g / 255.0;
        double bNorm = b / 255.0;

        double max = Math.max(rNorm, Math.max(gNorm, bNorm));
        double min = Math.min(rNorm, Math.min(gNorm, bNorm));
        double delta = max - min;

        if (delta == 0) {
            h = 0;
        } else if (max == rNorm) {
            h = (60 * ((gNorm - bNorm) / delta) + 360) % 360;
        } else if (max == gNorm) {
            h = (60 * ((bNorm - rNorm) / delta) + 120) % 360;
        } else if (max == bNorm) {
            h = (60 * ((rNorm - gNorm) / delta) + 240) % 360;
        }

        s = (max == 0) ? 0 : (delta / max) * 100;
        v = max * 100;
    }


    // Метод для пересчета CMYK -> RGB

    public void cmykToRgb() {
        double rPercent = (1 - c/100) * (1 - k/100);
        double gPercent = (1 - m/100) * (1 - k/100);
        double bPercent = (1 - y/100) * (1 - k/100);

        this.r = rPercent * 255;
        this.g = gPercent * 255;
        this.b = bPercent * 255;
    }
    public void hsvToRgb() {
        double sNorm = s / 100.0;
        double vNorm = v / 100.0;

        double c = vNorm * sNorm;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = vNorm - c;

        double rPrime = 0, gPrime = 0, bPrime = 0;

        if (0 <= h && h < 60) {
            rPrime = c;
            gPrime = x;
        } else if (60 <= h && h < 120) {
            rPrime = x;
            gPrime = c;
        } else if (120 <= h && h < 180) {
            gPrime = c;
            bPrime = x;
        } else if (180 <= h && h < 240) {
            gPrime = x;
            bPrime = c;
        } else if (240 <= h && h < 300) {
            rPrime = x;
            bPrime = c;
        } else if (300 <= h && h < 360) {
            rPrime = c;
            bPrime = x;
        }

        this.r = (rPrime + m) * 255;
        this.g = (gPrime + m) * 255;
        this.b = (bPrime + m) * 255;

    }

    // Метод для преобразования CMYK -> HSV
    public void cmykToHsv() {
        cmykToRgb();

        rgbToHsv();
    }

    // Метод для преобразования HSV -> CMYK
    public void hsvToCmyk() {
        hsvToRgb();
        rgbToCmyk();
    }

}
