public class Products {
    int id;
    String name;
    int stock=0;
    int w_id;

    Products(int id, String name, int stock) {
        this.id=id;
        this.name=name;
        this.stock=stock;
    }

    Products(int id, String name, int stock, int w_id) {
        this.id=id;
        this.name=name;
        this.stock=stock;
        this.w_id = w_id;
    }
}