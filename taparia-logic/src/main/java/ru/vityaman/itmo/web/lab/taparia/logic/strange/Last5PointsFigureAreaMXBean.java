package ru.vityaman.itmo.web.lab.taparia.logic.strange;

public interface Last5PointsFigureAreaMXBean {
    double getValue();

    double getGraph();

    void setValue(double value);

    final class Instance implements Last5PointsFigureAreaMXBean {
        private double value;

        public Instance(long value) {
            this.value = value;
        }

        @Override
        public double getValue() {
            return value;
        }

        @Override
        public double getGraph() {
            return value;
        }

        @Override
        public void setValue(double value) {
            this.value = value;
        }
    }
}
