package ru.vityaman.itmo.web.lab.taparia.figure.primitive;

import lombok.Value;

@Value(staticConstructor = "of")
public class Point {
    long x;
    long y;
}
