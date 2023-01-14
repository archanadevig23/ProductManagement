import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductManagement {

    static ArrayList<Products> products = new ArrayList<>();
    static ArrayList<Wholesalers> wholesalers = new ArrayList<Wholesalers>();
    static ArrayList<Retailers> retailers = new ArrayList<Retailers>();

    public static void main(String[] args) {

        try {

            Scanner sc1 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/products.csv"));
            Scanner sc2 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/wholesalers.csv"));
            Scanner sc3 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/retailers.csv"));

            sc1.useDelimiter("\n");
            while (sc1.hasNext()) {
                String[] prod_data = sc1.next().split(",", 4);
                products.add(new Products(Integer.parseInt(prod_data[0]), prod_data[1], Integer.parseInt(prod_data[2])));
            }
            sc1.close();

            sc2.useDelimiter("\n");
            while (sc2.hasNext()) {
                String[] wholesalers_data = sc2.next().split(",", 3);
                wholesalers.add(new Wholesalers(Integer.parseInt(wholesalers_data[0]), wholesalers_data[1]));
            }
            sc2.close();

            sc3.useDelimiter("\n");
            while (sc3.hasNext()) {
                String[] retailers_data = sc3.next().split(",", 3);
                retailers.add(new Retailers(Integer.parseInt(retailers_data[0]), retailers_data[1]));
            }

            System.out.println("Enter the option: ");
            System.out.println("1. View products");
            System.out.println("2. View wholesalers");
            System.out.println("3. View retailers");
            System.out.println("4. Allocate product to wholesaler");
            System.out.println("5. Allocate wholesaler to retailer");
            System.out.println("6. Quit");


            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("----------------------------------------");
                System.out.println("Enter your option: ");
                int opt = sc.nextInt();

                switch (opt) {
                    case 1:
                        viewProducts();
                        break;
                    case 2:
                        viewWholesalers();
                        break;
                    case 3:
                        viewRetailers();
                        break;
                    case 4:
                        System.out.println("Enter your wholesaler id: ");
                        int w_id = sc.nextInt();
                        System.out.println("Enter the product id you want to purchase: ");
                        int w_req_id = sc.nextInt();
                        System.out.println("Enter the amount of stock you want to purchase: ");
                        int w_req_stock = sc.nextInt();
                        allocateToWholesaler(w_id, w_req_id, w_req_stock);
                        System.out.println("Purchase successful");
                        break;
                    case 5:
                        System.out.println("Enter your retailer id: ");
                        int r_id = sc.nextInt();
                        System.out.println("Enter the wholesaler id you want to purchase from: ");
                        int w_r_id = sc.nextInt();
                        System.out.println("Enter the product id you want to purchase: ");
                        int r_req_id = sc.nextInt();
                        System.out.println("Enter the amount of stock you want to purchase: ");
                        int r_req_stock = sc.nextInt();
                        allocateToRetailer(r_id, w_r_id, r_req_id, r_req_stock);
                        break;
                    case 6:
                        System.out.println("Program ended");
                        return;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static void viewProducts() {
        System.out.println("----------------------------------------");
        System.out.println("Products: ");
        System.out.println("ID  Name  Stock");
        for (Products p : products) {
            System.out.print(p.id + " " + p.name + " " + p.stock + "\n");
        }
        System.out.println();
    }

    static void viewWholesalers() {
        System.out.println("----------------------------------------");
        System.out.println("Wholesalers: ");
        System.out.println("ID  Name\t\t    Stock");
        for (Wholesalers w : wholesalers) {
            System.out.print("\n" + w.id + " " + w.name + " ");
            boolean[] wp_bool = new boolean[w.w_products.size()];
            for (int i = 0; i < w.w_products.size(); i++)
            {
                if(wp_bool[w.w_products.indexOf(w.w_products.get(i))]==false)
                    System.out.print(w.w_products.get(i).id + " " + w.w_products.get(i).name + " " + w.w_products.get(i).stock + " , ");
                wp_bool[w.w_products.indexOf(w.w_products.get(i))]=true;

            }
        }
        System.out.println();
    }

    static void viewRetailers() {
        System.out.println("----------------------------------------");
        System.out.println("Retailers: ");
        System.out.println("ID  Name\t\t    Stock\t\t\tWholesaler ID ");
        for (Retailers r : retailers) {
            System.out.print("\n" + r.id + " " + r.name + " ");
            for (int i = 0; i < r.r_products.size(); i++)
            {
                System.out.print(r.r_products.get(i).id + " " + r.r_products.get(i).name + " " + r.r_products.get(i).stock + " " + r.r_products.get(i).w_id);
            }
        }
        System.out.println();
    }

    static void allocateToWholesaler(int w_id, int p_id, int stock) {

        for (Wholesalers wholesaler : wholesalers) {

            if (wholesaler.id == w_id) {

                int index = wholesalers.indexOf(wholesaler);

                for (Products product : products) {

                    if (product.id == p_id) {

                        if (product.stock >= stock) {

                            int p_index = products.indexOf(product);
                            Products p = new Products(product.id, product.name, 0, w_id);
                            wholesalers.get(index).w_products.add(p);
                            products.get(p_index).stock = products.get(p_index).stock - stock;
                            int w_index=-1;
                            boolean w_bool[] = new boolean[wholesalers.get(index).w_products.size()];

                            for(Products w_product: wholesalers.get(index).w_products)
                            {
                                w_bool[wholesalers.get(index).w_products.indexOf(w_product)] = false;
                                if(w_product.w_id == w_id) {
                                    w_bool[wholesalers.get(index).w_products.indexOf(w_product)] = true;
                                    w_index = wholesalers.get(index).w_products.indexOf(w_product);
                                }
                            }
                            wholesalers.get(index).w_products.get(w_index).stock = wholesalers.get(index).w_products.get(w_index).stock + stock;

                        }
                    }
                }
            }
        }
    }

    static void allocateToRetailer(int r_id, int w_r_id, int r_p_id, int stock) {

        for (Retailers retailer : retailers) {

            if (retailer.id == r_id) {

                int r_index = retailers.indexOf(retailer);

                for(Wholesalers wholesaler: wholesalers) {

                    if(w_r_id == wholesaler.id) {
                        int w_index = wholesalers.indexOf(wholesaler);
                        for (Products product : products) {
                            if (product.id == r_p_id) {
                                if (product.stock >= stock) {
                                    int p_index = products.indexOf(product);
                                    System.out.println("hello + " + r_index);
                                    Products p = new Products(product.id, product.name, 0, r_id);
                                    retailers.get(r_index).r_products.add(p);
                                    System.out.println("size : " + retailers.get(r_index).r_products.size());



                                    System.out.println(w_index + " " + p_index + " " +  wholesalers.get(w_index).w_products.size());

                                    int pe_index = retailers.get(r_index).r_products.indexOf(p);

                                    System.out.println("line 202 " + wholesalers.get(w_index).w_products.get(pe_index).stock);

                                    wholesalers.get(w_index).w_products.get(pe_index).stock = wholesalers.get(w_index).w_products.get(pe_index).stock - stock;

                                    System.out.println(wholesalers.get(w_index).w_products.get(pe_index).stock);

                                    int re_index=-1;

                                    System.out.println("r_index - " + r_index);

                                    boolean w_bool[] = new boolean[retailers.get(r_index).r_products.size()];

                                    System.out.println("test 1");

                                    for(Products r_product: retailers.get(r_index).r_products)
                                    {
                                        System.out.println("test 2 " + wholesalers.get(r_index).w_products.size());

                                        w_bool[wholesalers.get(r_index).w_products.indexOf(r_product)] = false;

                                        System.out.println("test 3");

                                        if(r_product.w_id == r_id) {
                                            w_bool[wholesalers.get(r_index).w_products.indexOf(r_product)] = true;
                                            re_index = wholesalers.get(r_index).w_products.indexOf(r_product);
                                        }
                                    }

                                    System.out.println("line 228 ");

                                    retailers.get(r_index).r_products.get(re_index).stock = retailers.get(r_index).r_products.get(re_index).stock + stock;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}