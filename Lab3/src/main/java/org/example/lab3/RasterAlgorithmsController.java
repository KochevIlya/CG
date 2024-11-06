package org.example.lab3;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RasterAlgorithmsController {

    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> algorithmSelector;
    @FXML
    private Button drawButton;
    @FXML
    private TextField startXField;
    @FXML
    private TextField startYField;
    @FXML
    private TextField endXField;
    @FXML
    private TextField endYField;
    @FXML
    private Label executionTimeLabel;

    private static final int CELL_SIZE = 20;

    @FXML
    public void initialize() {
        drawGrid();
        algorithmSelector.getSelectionModel().select("Пошаговый алгоритм");

        startXField.setText("0");
        startYField.setText("0");
        endXField.setText("0");
        endYField.setText("0");

        setNumericOnlyWithDefaultZero(startXField);
        setNumericOnlyWithDefaultZero(startYField);
        setNumericOnlyWithDefaultZero(endXField);
        setNumericOnlyWithDefaultZero(endYField);

        drawButton.setOnAction(e -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawGrid();

            int x1 = Integer.parseInt(startXField.getText());
            int y1 = Integer.parseInt(startYField.getText());
            int x2 = Integer.parseInt(endXField.getText());
            int y2 = Integer.parseInt(endYField.getText());

            long startTime = System.nanoTime();

            switch (algorithmSelector.getValue()) {
                case "Пошаговый алгоритм":
                    stepByStepLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм ЦДА":
                    ddaLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм Брезенхема (отрезок)":
                    bresenhamLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм Брезенхема (окружность)":
                    int radius = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    bresenhamCircle(gc, x1 - 1, y1 - 1, radius);
                    break;
            }

            long endTime = System.nanoTime();
            double executionTime = (endTime - startTime);
            executionTimeLabel.setText("Execution Time: " + executionTime);
        });
    }

    private void setNumericOnlyWithDefaultZero(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1 && newValue.startsWith("0")) {
                textField.setText(newValue.substring(1));
            }


            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (textField.getText().isEmpty()) {
                textField.setText("0");
            }
        });
    }

    private double transformX(int x) {
        return (canvas.getWidth() / 2) + x * CELL_SIZE;
    }

    private double transformY(int y) {
        return (canvas.getHeight() / 2) - y * CELL_SIZE;
    }

    private void drawGrid() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);

        for (int x = 0; x < canvas.getWidth(); x += CELL_SIZE) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y < canvas.getHeight(); y += CELL_SIZE) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

        gc.setStroke(Color.BLACK);
        gc.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight());
        gc.strokeLine(0, (canvas.getHeight() / 2) + CELL_SIZE, canvas.getWidth(), (canvas.getHeight() / 2) + CELL_SIZE);
    }

    private void stepByStepLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double steps = Math.max(Math.abs(dx), Math.abs(dy));
        double xIncrement = dx / steps;
        double yIncrement = dy / steps;

        double x = x1;
        double y = y1;
        gc.setFill(Color.RED);
        for (int i = 0; i <= steps; i++) {
            gc.fillRect(transformX((int) Math.round(x)), transformY((int) Math.round(y)), CELL_SIZE, CELL_SIZE);
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void ddaLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        double xIncrement = dx / steps;
        double yIncrement = dy / steps;

        double x = x1;
        double y = y1;
        gc.setFill(Color.GREEN);
        for (int i = 0; i <= steps; i++) {
            gc.fillRect(transformX((int) Math.round(x)), transformY((int) Math.round(y)), CELL_SIZE, CELL_SIZE);
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void bresenhamLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        gc.setFill(Color.BLUE);
        while (true) {
            gc.fillRect(transformX(x1), transformY(y1), CELL_SIZE, CELL_SIZE);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void bresenhamCircle(GraphicsContext gc, double xc, double yc, int r) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        gc.setFill(Color.PURPLE);

        while (y >= x) {
            drawCirclePoints(gc, xc, yc, x, y);
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
        }
    }

    private void drawCirclePoints(GraphicsContext gc, double xc, double yc, int x, int y) {
        gc.fillRect(transformX((int) Math.round(xc + x)), transformY((int) Math.round(yc + y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - x)), transformY((int) Math.round(yc + y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + x)), transformY((int) Math.round(yc - y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - x)), transformY((int) Math.round(yc - y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + y)), transformY((int) Math.round(yc + x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - y)), transformY((int) Math.round(yc + x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + y)), transformY((int) Math.round(yc - x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - y)), transformY((int) Math.round(yc - x)), CELL_SIZE, CELL_SIZE);
    }
}