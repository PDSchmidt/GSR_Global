package model;


import model.entity.ReportComparator;

import java.util.*;

public class Reports {
    private final Map<String, Report> theReports;
    private final List<String> reportTitles;
    public Reports() {
        theReports = new HashMap<>();
        theReports.put("Query 1", new Report("Query 1", "Displays the Customer's Information as well" +
                " as the number of orders they've made.", "select FirstName, LastName, Street, City, State, customers.ZIPCODE, Phone, Num_Orders\n" +
                "    from Customers inner join zip on (customers.zipcode = zip.zipcode)\n" +
                "\t\t\t\t   inner join (select customerID, count(orderid) as Num_Orders from orders group by customerID)\n" +
                "                   as NumOrders on NumOrders.customerID = Customers.customerID;"));
        theReports.put("Query 2", new Report("Query 2", "Displays orders that are greater than the highest customer average.",
                "select customerid, orderid, totalcost\n" +
                        "\tfrom orders\n" +
                        "\twhere totalcost > all(select avg(totalCost) from orders group by customerid)\n" +
                        "\torder by CustomerID;"));
        theReports.put("Query 3", new Report("Query 3", "Displays the order and delivery date of a customer's earliest DELIVERED order.",
                "SELECT CustomerId, FirstName, LastName, orderID, DeliveryDate\n" +
                        "\tFROM (select * from ORDERS natural join delivery natural join (select CustomerID, FirstName, LastName from customers) as CustInfo) as OUTERTABLE\n" +
                        "\tWHERE (DeliveryDate) = (SELECT MIN(DeliveryDate) \n" +
                        "\t\t\t\t\t\t\tFROM (select * from Orders natural join Delivery) as innertable\n" +
                        "\t\t\t\t\t\t\tWHERE innertable.CUSTOMERID = OUTERTABLE.CUSTOMERID\n" +
                        "\t\t\t\t\t\t\tGROUP BY CUSTOMERID);"));
        theReports.put("Query 4", new Report("Query 4", "Displays information about each line item associated with an order",
                "select orders.OrderID, product.ProductName, order_item.quantity, product.UnitPrice, order_item.SubTotalCost, orders.TotalCost  from\n" +
                        "    orders left outer join order_item on (orders.OrderID = order_item.OrderID)\n" +
                        "    right outer join product on (order_item.ProductID = product.ProductID)\n" +
                        "    order by orders.OrderID;"));
        theReports.put("Query 5", new Report("Query 5", "Displays orders that will/have been delivered to " +
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
        theReports.put("Query 6", new Report("Query 6", "Displays the total spent on all orders per customer",
                "select customers.customerid, customers.FirstName, customers.LastName, sum(orders.TotalCost) as SumOfOrders\n" +
                        "\tfrom Customers left outer join orders on customers.customerid=orders.customerid\n" +
                        "\tgroup by customers.customerid;"));
        theReports.put("Query 7", new Report("Query 7", "Displays the total number of orders per store location.",
                "select LocationID, LocationName, NumOrders\n" +
                        "    from storelocations\n" +
                        "    natural join (select locationID, count(locationId) as NumOrders from orders group by locationID) as counts;"));
        theReports.put("Query 8", new Report("Query 8", "Displays which orders are awaiting payment.",
                "select concat(LastName,\", \",FirstName) as Name, Email, Phone, OrderID, OrderDate, TotalCost, OrderStatus\n" +
                        "    from customers left outer join orders on (customers.CustomerID = orders.CustomerID) where OrderStatus = \"PENDING PAYMENT\"\n" +
                        "    order by customers.CustomerID;"));
        theReports.put("Query 9", new Report("Query 9", "Displays all orders and information about the customer that placed them.",
                "SELECT c.FirstName, c.LastName, o.OrderID, d.DeliveryStatus, z.City\n" +
                        "\tFROM customers c\n" +
                        "\tJOIN orders o ON c.CustomerID = o.CustomerID\n" +
                        "\tJOIN delivery d ON d.DeliveryID = o.DeliveryID\n" +
                        "\tJOIN zip z ON z.ZIPCODE = c.ZIPCODE;"));
        theReports.put("Query 10", new Report("Query 10", "Displays the parts needed to create each product and which vendor supplies those parts.",
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
    public List<String> getReportTitles() {
        return reportTitles;
    }
    public Report get(final String queryTitle) {
        return theReports.get(queryTitle);
    }
    public class Report {
        private final String query;
        private final String title;
        private final String description;
        private Report(final String title, final String description, final String query) {
            this.title = title;
            this.description = description;
            this.query = query;
        }
        public String getTitle() {
            return title;
        }
        public String getQuery() {
            return query;
        }
        public String getDescription() {
            return description;
        }
    }
}
