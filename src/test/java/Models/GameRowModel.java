package Models;

import enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class GameRowModel {

    private String name;
    private String price;
    private String discount;
    private List<String> platform;

    @Override
    public String toString() {
        return "GameRowModel{" +
                "name = " + name +
                ", price = " + price +
                ", discount = " + discount +
                ", platform = " + Collections.singletonList(platform) +
                '}';
    }
}
