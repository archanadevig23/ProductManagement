import java.util.ArrayList;

public class Wholesalers{
    int id;
    String name;
    ArrayList<Products> w_products = new ArrayList<Products>();

    Wholesalers(int id, String name) {
        this.id=id;
        this.name=name;
    }
}
