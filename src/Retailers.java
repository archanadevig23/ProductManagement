import java.util.ArrayList;

public class Retailers {
    int id;
    String name;
    ArrayList<Products> r_products = new ArrayList<Products>();

    Retailers(int id, String name) {
        this.id=id;
        this.name=name;
    }
}