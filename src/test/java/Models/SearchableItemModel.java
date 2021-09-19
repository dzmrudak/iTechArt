package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class SearchableItemModel {

    private String name;
    private String price;

    @Override
    public String toString() {
        return "SearchableItemModel{" +
                "name=" + name +
                ", price=" + price +
                '}';
    }
}
