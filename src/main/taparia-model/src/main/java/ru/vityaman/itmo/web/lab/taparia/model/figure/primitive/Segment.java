package ru.vityaman.itmo.web.lab.taparia.figure.primitive;

import lombok.Value;

@Value
public class Segment {
    Point start;
    Point end;

    public Segment(Point start, Point end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException(
                String.format("start == end == %s", start)
            );
        }
        this.start = start;
        this.end = end;
    }

    public static Segment on(Point start, Point end) {
        return new Segment(start, end);
    }

    // https://stackoverflow.com/questions/217578/
    public boolean intersects(Segment other) {
        return areD1AndD2SuitableHaveDifferentSigns(this, other)
            && areD1AndD2SuitableHaveDifferentSigns(other, this);
    }

    @Value
    private static class StandardLinearEquationForm {
        long a;
        long b;
        long c;

        StandardLinearEquationForm(long a, long b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        static StandardLinearEquationForm of(Segment s) {
            return new StandardLinearEquationForm(
                s.end().y() - s.start().y(),
                s.start().x() - s.end().x(),
                s.end().x() * s.start().y() - s.start().x() * s.end().y()
            );
        }
    }

    private boolean areD1AndD2SuitableHaveDifferentSigns(
        Segment first, Segment second
    ) {
        final StandardLinearEquationForm form =
            StandardLinearEquationForm.of(first);
        final long a = form.a, b = form.b, c = form.c;
        final long d1 =
            (a * second.start().x()) + (b * second.start().y()) + c;
        final long d2 =
            (a * second.end().x()) + (b * second.end().y()) + c;
        return (d1 <= 0 || d2 <= 0) && (d1 >= 0 || d2 >= 0);
    }
}

