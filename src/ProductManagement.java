import com.sun.tools.javah.Gen;

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
                String[] prod_data = sc1.next().split(",", 5);
                products.add(new Products(Integer.parseInt(prod_data[0]), prod_data[1], Integer.parseInt(prod_data[2]), Float.parseFloat(prod_data[3]), Integer.parseInt(prod_data[4])));
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

            System.out.println("Options: ");
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
                    default:
                        System.out.println("Incorrect option");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // view data -> completed

    static void viewProducts() throws NoDataException{

        try {

            if(products.size()<=0)
                throw new NoDataException("There are no products available in the warehouse! ");
            else
            {
                System.out.println("----------------------------------------");
                System.out.println("Products: ");
                System.out.println("ID  Name  \tStock\t Price\t  GST");
                System.out.println();
                for (Products p : products) {
                    System.out.print(p.id + "  " + p.name + "  " + p.stock + "  \t" + p.price + "    " + p.gst + "%\n");
                }
                System.out.println();

            }
        }

        catch (NoDataException e) {
            System.out.println(e);
        }
    }

    static void viewWholesalers() {

        try {

            if(wholesalers.size()<=0)
                throw new NoDataException("There are no products available in the warehouse! ");
            else
            {
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
        }

        catch (NoDataException e) {
            System.out.println(e);
        }
    }

    static void viewRetailers() {

        try {

            if(retailers.size()<=0)
                throw new NoDataException("There are no products available in the warehouse! ");
            else
            {
                System.out.println("----------------------------------------");
                System.out.println("Retailers: ");
                System.out.println("ID  Name\t\t    Stock\t\t\t\t\tWholesaler ID ");
                for (Retailers r : retailers) {
                    System.out.print("\n" + r.id + " " + r.name + " ");
                    for (int i = 0; i < r.r_products.size(); i++)
                    {
                        System.out.print(r.r_products.get(i).id + " " + r.r_products.get(i).name + " " + r.r_products.get(i).stock + " " + r.r_products.get(i).w_id);
                    }
                }
                System.out.println();

            }
        }

        catch (NoDataException e) {
            System.out.println(e);
        }
    }

    // allocate to wholesaler

    static void allocateToWholesaler(int w_id, int p_id, int stock) throws InvalidWholesalerCodeException {

        boolean wb = false;
        boolean pr = false;

        for (Wholesalers wholesaler : wholesalers) {        // iterate through all the wholesalers to find which wholesaler requests a purchase

            if (wholesaler.id == w_id)
            {
                wb=true;
                int index = wholesalers.indexOf(wholesaler);    // gets the index of requesting wholesaler

                for (Products product : products) {     // iterate through all the products to find which product is requested by the wholesaler

                    if (product.id == p_id) {
                        pr = true;

                        // exception handling if there is no enough stock
                        try {

                            if (product.stock >= stock) {

                                int p_index = products.indexOf(product); //   gets the index of requesting wholesaler

                                Products p = new Products(product.id, product.name, 0, product.price, product.gst, w_id);   // we are creating a new object of product class with stock as 0

                                wholesalers.get(index).w_products.add(p);   // adding that product to the wholesaler products list

                                products.get(p_index).stock = products.get(p_index).stock - stock; // updating the product stock in products

                                // iterate through all the products in w_products and update the product stock if it already exists, else create a new object and update it
                                int w_index = -1;

                                boolean w_bool[] = new boolean[wholesalers.get(index).w_products.size()];

                                for (Products w_product : wholesalers.get(index).w_products) {
                                    w_bool[wholesalers.get(index).w_products.indexOf(w_product)] = false;
                                    if (w_product.w_id == w_id) {
                                        w_bool[wholesalers.get(index).w_products.indexOf(w_product)] = true;
                                        w_index = wholesalers.get(index).w_products.indexOf(w_product);
                                    }
                                }

                                wholesalers.get(index).w_products.get(w_index).stock = wholesalers.get(index).w_products.get(w_index).stock + stock;
                                System.out.println("Purchase successful");
                                GeneratePDF.generate(wholesaler, wholesalers.get(index).w_products.get(w_index));
                            }
                            else
                                throw new InsufficientStockAvailableException("Requested stock of product " + p_id + " is not available in the warehouse");

                        }

                        catch (InsufficientStockAvailableException e) {
                            System.out.println(e);
                        }

                    }
            }

                try {
                    if(pr==false)
                        throw new InvalidProductCodeException("You have entered an invalid product code. Enter a valid one");
                }
                catch (InvalidProductCodeException e) {
                    System.out.println(e);
                }

            sumUpWProducts(index);

            }

        }
        try {
            if (wb == false)
            {
                throw new InvalidWholesalerCodeException("You have entered an invalid wholesaler code. Enter a valid one");
            }
        }
        catch (InvalidWholesalerCodeException e) {
            System.out.println(e);
        }

    }

    // sum up wholesaler products before displaying

    static void sumUpWProducts(int w_index) {

        ArrayList<Integer> iop = new ArrayList<>();

        for(int i=0; i<wholesalers.get(w_index).w_products.size(); i++) {
            Products w_product = wholesalers.get(w_index).w_products.get(i);
            for(int j=i+1; j<wholesalers.get(w_index).w_products.size(); j++) {
                Products w_product1 = wholesalers.get(w_index).w_products.get(j);
                if((w_product.id == w_product1.id) && (wholesalers.get(w_index).w_products.indexOf(w_product)!=wholesalers.get(w_index).w_products.indexOf(w_product1)) ) {
                    w_product.stock += w_product1.stock;
                    iop.add(wholesalers.get(w_index).w_products.indexOf(w_product1));
                }
            }
        }

        for(Integer i: iop) {
            System.out.println(i);
            wholesalers.get(w_index).w_products.remove(wholesalers.get(w_index).w_products.get(i));
        }

    }

    static void allocateToRetailer(int r_id, int w_id, int p_id, int stock) {

        boolean re = false;
        boolean wb = false;
        boolean pr = false;

        for(Retailers retailer: retailers) {

            if(retailer.id == r_id) {

                re=true;

                int r_index = retailers.indexOf(retailer);

                for(Wholesalers wholesaler: wholesalers) {

                    if(wholesaler.id == w_id) {

                        wb=true;

                        int w_index = wholesalers.indexOf(wholesaler);

                        for(Products product: products) {

                            try {

                                if(product.id == p_id) {

                                    pr=true;

                                    if(product.stock >= stock) {

                                        int p_index = products.indexOf(product);

                                        Products p = new Products(product.id, product.name, 0, product.price, product.gst, w_id, r_id);

                                        retailers.get(r_index).r_products.add(p);

                                        System.out.println("Line 1 " + wholesalers.get(w_index));
                                        System.out.println("Line 2 " + wholesalers.get(w_index).w_products.get(p_index));



                                        wholesalers.get(w_index).w_products.get(p_index).stock -= stock;

                                        int re_index = -1;

                                        System.out.println("Line");

                                        boolean r_bool[] = new boolean[retailers.get(r_index).r_products.size()];

                                        for (Products r_product : retailers.get(r_index).r_products) {
                                            r_bool[retailers.get(r_index).r_products.indexOf(r_product)] = false;
                                            if (r_product.r_id == r_id) {
                                                r_bool[retailers.get(r_index).r_products.indexOf(r_product)] = true;
                                                re_index = retailers.get(r_index).r_products.indexOf(r_product);
                                            }
                                        }

                                        retailers.get(r_index).r_products.get(re_index).stock += stock;
                                        GeneratePDF.generate(retailer, retailers.get(r_index).r_products.get(re_index));
                                    }

                                    else {

                                        throw new InsufficientStockAvailableException("Requested stock of product " + p_id + " is not available in the wholesaler " + w_id);

                                    }

                                }


                            }

                            catch (InsufficientStockAvailableException e) {

                                System.out.println(e);

                            }

                        }

                        try {
                            if(pr==false)
                                throw new InvalidProductCodeException("You have entered an invalid product code. Enter a valid one");
                        }
                        catch (InvalidProductCodeException e) {
                            System.out.println(e);
                        }

                        sumUpRProducts(r_index);

                    }

                }

                try {
                    if (wb == false)
                    {
                        throw new InvalidWholesalerCodeException("You have entered an invalid wholesaler code. Enter a valid one");
                    }
                }
                catch (InvalidWholesalerCodeException e) {
                    System.out.println(e);
                }

            }

        }

        try {
            if (re == false)
            {
                throw new InvalidRetailerCodeException("You have entered an invalid retailer code. Enter a valid one");
            }
        }
        catch (InvalidRetailerCodeException e) {
            System.out.println(e);
        }

    }

    static void sumUpRProducts(int r_index) {

        ArrayList<Integer> iop = new ArrayList<>();

        for(int i=0; i<retailers.get(r_index).r_products.size(); i++) {
            Products r_product = retailers.get(r_index).r_products.get(i);
            for(int j=i+1; j<retailers.get(r_index).r_products.size(); j++) {
                Products r_product1 = retailers.get(r_index).r_products.get(j);
                if((r_product.id == r_product1.id) && (retailers.get(r_index).r_products.indexOf(r_product)!=retailers.get(r_index).r_products.indexOf(r_product1)) ) {
                    r_product.stock += r_product1.stock;
                    iop.add(retailers.get(r_index).r_products.indexOf(r_product1));
                }
            }
        }

        for(Integer i: iop) {
            System.out.println(i);
            retailers.get(r_index).r_products.remove(retailers.get(r_index).r_products.get(i));
        }

    }

}