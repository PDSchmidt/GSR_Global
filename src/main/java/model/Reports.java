package model;


import model.entity.ReportComparator;
import java.util.*;

/**
 * A class that represents a collection of reports that will query the database for a custom
 * breakdown of information in the database.
 * @author Paul Schmidt
 */
public class Reports {
    /**
     * The collection of reports.
     */
    private final Map<String, Report> theReports;
    /**
     * The collection of the report titles.
     */
    private final List<String> reportTitles;

    /**
     * Constructs a collection of reports predefined.
     */
    public Reports() {
        theReports = new HashMap<>();
        theReports.put("Orders per Customer", new Report("Orders per Customer", "Displays the Customer's Information as well" +
                " as the number of orders they've made.", "select FirstName, LastName, Street, City, State, customers.ZIPCODE, Phone, Num_Orders\n" +
                "    from Customers inner join zip on (customers.zipcode = zip.zipcode)\n" +
                "\t\t\t\t   inner join (select customerID, count(orderid) as Num_Orders from orders group by customerID)\n" +
                "                   as NumOrders on NumOrders.customerID = Customers.customerID;"));
        theReports.put("Greater than Average Orders", new Report("Greater than Average Orders", "Displays orders that are greater than the highest customer average.",
                "select customerid, orderid, totalcost\n" +
                        "\tfrom orders\n" +
                        "\twhere totalcost > all(select avg(totalCost) from orders group by customerid)\n" +
                        "\torder by CustomerID;"));
        theReports.put("Customers First Delivered Order", new Report("Customers First Delivered Order", "Displays the order and delivery date of a customer's earliest DELIVERED order.",
                "SELECT CustomerId, FirstName, LastName, orderID, DeliveryDate\n" +
                        "\tFROM (select * from ORDERS natural join delivery natural join (select CustomerID, FirstName, LastName from customers) as CustInfo) as OUTERTABLE\n" +
                        "\tWHERE (DeliveryDate) = (SELECT MIN(DeliveryDate) \n" +
                        "\t\t\t\t\t\t\tFROM (select * from Orders natural join Delivery) as innertable\n" +
                        "\t\t\t\t\t\t\tWHERE innertable.CUSTOMERID = OUTERTABLE.CUSTOMERID\n" +
                        "\t\t\t\t\t\t\tGROUP BY CUSTOMERID);"));
        theReports.put("Order Breakdown by Line-Item", new Report("Order Breakdown by Line-Item", "Displays information about each line item associated with an order",
                "select orders.OrderID, product.ProductName, order_item.quantity, product.UnitPrice, order_item.SubTotalCost, orders.TotalCost  from\n" +
                        "    orders left outer join order_item on (orders.OrderID = order_item.OrderID)\n" +
                        "    right outer join product on (order_item.ProductID = product.ProductID)\n" +
                        "    order by orders.OrderID;"));
        theReports.put("Orders in UT and TX", new Report("Orders in UT and TX", "Displays orders that will/have been delivered to " +
                        "addresses in two predetermined states (UT, TX).",
                "(SELECT o.OrderID, c.CustomerID, o.TotalCost, z.State\n" +
                        "\tFROM orders AS o\n" +
                        "\tJOIN customers AS c ON o.CustomerID = c.CustomerID\n" +
                        "\tJOIN zip AS z ON c.ZIPCODE = z.ZIPCODE\n" +
                        "\tWHERE z.State = 'UT')\n" +
                        "\n" +
                        "\tUNION\n" +
                        "\n" +
                        "\t(SELECT o.OrderID, c.CustomerID, o.TotalCost, z.State\n" +
                        "\tFROM orders AS o\n" +
                        "\tJOIN customers AS c ON o.CustomerID = c.CustomerID\n" +
                        "\tJOIN zip AS z ON c.ZIPCODE = z.ZIPCODE\n" +
                        "\tWHERE z.State = 'TX');"));
        theReports.put("Total Amount Spent by Customer", new Report("Total Amount Spent by Customer", "Displays the total spent on all orders per customer",
                "select customers.customerid, customers.FirstName, customers.LastName, sum(orders.TotalCost) as SumOfOrders\n" +
                        "\tfrom Customers left outer join orders on customers.customerid=orders.customerid\n" +
                        "\tgroup by customers.customerid;"));
        theReports.put("Number of Orders by Store", new Report("Number of Orders by Store", "Displays the total number of orders per store location.",
                "select LocationID, LocationName, NumOrders\n" +
                        "    from storelocations\n" +
                        "    natural join (select locationID, count(locationId) as NumOrders from orders group by locationID) as counts;"));
        theReports.put("Orders Awaiting Payment", new Report("Orders Awaiting Payment", "Displays which orders are awaiting payment.",
                "select concat(LastName,\", \",FirstName) as Name, Email, Phone, OrderID, OrderDate, TotalCost, OrderStatus\n" +
                        "    from customers left outer join orders on (customers.CustomerID = orders.CustomerID) where OrderStatus = \"PENDING PAYMENT\"\n" +
                        "    order by customers.CustomerID;"));
        theReports.put("All Orders with Customer Info", new Report("All Orders with Customer Info", "Displays all orders and information about the customer that placed them.",
                "SELECT c.FirstName, c.LastName, o.OrderID, d.DeliveryStatus, z.City\n" +
                        "\tFROM customers c\n" +
                        "\tJOIN orders o ON c.CustomerID = o.CustomerID\n" +
                        "\tJOIN delivery d ON d.DeliveryID = o.DeliveryID\n" +
                        "\tJOIN zip z ON z.ZIPCODE = c.ZIPCODE;"));
        theReports.put("Product/Part Breakdown", new Report("Product/Part Breakdown", "Displays the parts needed to create each product and which vendor supplies those parts.",
                "SELECT prd.ProductName, prt.PartName, v.VendorName\n" +
                        "\tFROM product AS prd\n" +
                        "\tJOIN productparts AS prdp ON prdp.ProductID = prd.ProductID\n" +
                        "\tJOIN parts AS prt ON prt.PartNumber = prdp.PartNumber\n" +
                        "\tJOIN vendors AS v ON v.VendorID = prt.VendorID;"));
        reportTitles = new ArrayList<>();
        for(String title : theReports.keySet()) {
            reportTitles.add(title);
        }
        Collections.sort(reportTitles, new ReportComparator());
    }

    /**
     * Returns a list of the titles of all reports.
     * @return the list of the titles of all reports.
     */
    public List<String> getReportTitles() {
        return reportTitles;
    }

    /**
     * Returns the Report object associated with the given title.
     * @param queryTitle the title of the report
     * @return the report
     */
    public Report get(final String queryTitle) {
        return theReports.get(queryTitle);
    }

    /**
     * Inner class that represents a report object. Provides information about it such as title,
     * what it does, and the SQL query required to execute it.
     */
    public class Report {
        /**
         * The SQL query
         */
        private final String query;
        /**
         * The title of the report
         */
        private final String title;
        /**
         * The description of the report
         */
        private final String description;

        /**
         * Constructs a Report with the given title, description, and associated
         * SQL query.
         * @param title the title of the report
         * @param description the description of the report
         * @param query the SQL query needed to generate the report
         */
        private Report(final String title, final String description, final String query) {
            this.title = title;
            this.description = description;
            this.query = query;
        }

        /**
         * Returns the title of the Report
         * @return the title of the Report
         */
        public String getTitle() {
            return title;
        }

        /**
         * Returns the query of the Report
         * @return the query of the Report
         */
        public String getQuery() {
            return query;
        }

        /**
         * Returns the description of the Report
         * @return the description of the Report
         */
        public String getDescription() {
            return description;
        }
    }
}
