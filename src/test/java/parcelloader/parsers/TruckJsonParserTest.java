package parcelloader.parsers;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.liga.parcelloader.type.model.Truck;
import ru.liga.parcelloader.data.parser.FileParser;
import ru.liga.parcelloader.data.parser.TruckJsonParser;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TruckJsonParserTest {
    public static FileParser<Truck> truckFileParser;

    @BeforeAll
    public static void before() {
        truckFileParser = new TruckJsonParser();
    }

    @Test
    public void jsonParserShouldWorkProperly() throws IOException {
        assertThat(
            truckFileParser.parse("./src/test/resources/truck_valid.json").toString()
        ).isEqualTo(new Truck(new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ' },
            { '8', '8', '8', '8', ' ', ' ' },
            { '8', '8', '8', '8', ' ', ' ' },
            { '7', '7', '7', ' ', ' ', ' ' },
            { '7', '7', '7', '7', '1', '1' },
            }).toString()
        );
    }

    @Test
    public void jsonParserShouldThrowError() {
        assertThatThrownBy(() -> truckFileParser.parse("./src/test/resources/truck_invalid.json"))
                .isInstanceOf(JsonSyntaxException.class);
    }
}
