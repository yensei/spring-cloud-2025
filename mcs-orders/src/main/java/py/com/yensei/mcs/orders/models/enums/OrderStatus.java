package py.com.yensei.mcs.orders.models.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PEN", "Pending"),
    CANCELLED("CAN", "Cancelled"),
    AUTHORIZED("AUT", "Authorized"),
    CONFIRMED("CON", "Confirmed"),
    DELETED("DLT", "Deleted");

    private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue // Le dice a Jackson que use este valor para convertir el objeto a JSON
    public String getCode() {
        return code;
    }

    @JsonCreator // Le dice a Jackson cómo crear el enum a partir de un valor JSON
    public static OrderStatus fromCode(String code) {
        return Stream.of(OrderStatus.values())
              .filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }
}

