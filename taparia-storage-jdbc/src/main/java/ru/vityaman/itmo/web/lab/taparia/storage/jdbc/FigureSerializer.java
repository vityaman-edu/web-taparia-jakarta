package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure.FigureView;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;

public interface FigureSerializer {
    String convertToJson(Figure figure);

    Figure parseFigureFromJson(String json);

    final class UnsafeJackson implements FigureSerializer {
        private final ObjectMapper jackson = new ObjectMapper();

        @SneakyThrows
        public String convertToJson(Figure figure) {
            return jackson.writeValueAsString(
                FigureView.fromModelFigure(figure)
            );
        }

        @SneakyThrows
        public Figure parseFigureFromJson(String json) {
            return jackson.readValue(json, FigureView.class).model();
        }
    }
}
