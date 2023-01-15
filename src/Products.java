public class Products {
    int id;
    String name;
    int stock=0;
    float price;
    int gst;
    int w_id;
    int r_id;

    Products(int id, String name, int stock) {
        this.id=id;
        this.name=name;
        this.stock=stock;
    }

    Products(int id, String name, int stock, float price, int gst, int w_id) {
        this.id=id;
        this.name=name;
        this.stock=stock;
        this.price=price;
        this.gst=gst;
        this.w_id = w_id;
    }

    Products(int id, String name, int stock, float price, int gst) {
        this.id=id;
        this.name=name;
        this.stock=stock;
        this.price=price;
        this.gst=gst;
    }

    Products(int id, String name, int stock, float price, int gst, int w_id, int r_id) {
        this.id=id;
        this.name=name;
        this.stock=stock;
        this.price=price;
        this.gst=gst;
        this.w_id = w_id;
        this.r_id=r_id;
    }
}