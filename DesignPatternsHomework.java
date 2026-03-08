
interface PaymentStrategy { void pay(int amount); }
class CreditCardPayment implements PaymentStrategy { public void pay(int amount) { System.out.println("Paid " + amount + " using Credit Card."); } }
class PayPalPayment implements PaymentStrategy { public void pay(int amount) { System.out.println("Paid " + amount + " using PayPal."); } }
class ShoppingCart {
    private PaymentStrategy strategy;
    public void setPaymentStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public void checkout(int amount) { strategy.pay(amount); }
}


abstract class Logger {
    protected Logger nextLogger;
    public void setNextLogger(Logger nextLogger) { this.nextLogger = nextLogger; }
    public void logMessage(int level, String msg) {
        if (canHandle(level)) write(msg);
        else if (nextLogger != null) nextLogger.logMessage(level, msg);
    }
    protected abstract boolean canHandle(int level);
    protected abstract void write(String message);
}
class InfoLogger extends Logger {
    protected boolean canHandle(int level) { return level == 1; }
    protected void write(String msg) { System.out.println("INFO: " + msg); }
}
class ErrorLogger extends Logger {
    protected boolean canHandle(int level) { return level == 2; }
    protected void write(String msg) { System.out.println("ERROR: " + msg); }
}


class UserProfile {
    private String name;
    private int age;
    private UserProfile(UserBuilder builder) { this.name = builder.name; this.age = builder.age; }
    public static class UserBuilder {
        private String name;
        private int age;
        public UserBuilder setName(String name) { this.name = name; return this; }
        public UserBuilder setAge(int age) { this.age = age; return this; }
        public UserProfile build() { return new UserProfile(this); }
    }
}


interface Database { void query(String sql); }
class RealDatabase implements Database { public void query(String sql) { System.out.println("Executing: " + sql); } }
class ProxyDatabase implements Database {
    private RealDatabase realDatabase;
    private boolean isAdmin;
    public ProxyDatabase(boolean isAdmin) { this.isAdmin = isAdmin; }
    public void query(String sql) {
        if (isAdmin) {
            if (realDatabase == null) realDatabase = new RealDatabase();
            realDatabase.query(sql);
        } else {
            System.out.println("Access Denied.");
        }
    }
}


interface Coffee { double getCost(); }
class BasicCoffee implements Coffee { public double getCost() { return 2.0; } }
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
    public double getCost() { return coffee.getCost(); }
}
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    public double getCost() { return super.getCost() + 0.5; }
}


interface JsonParser { void parseJson(String data); }
class XmlParser { void parseXml(String data) { System.out.println("Parsing XML: " + data); } }
class XmlToJsonAdapter implements JsonParser {
    private XmlParser xmlParser;
    public XmlToJsonAdapter(XmlParser xmlParser) { this.xmlParser = xmlParser; }
    public void parseJson(String data) {
        String convertedData = data.replace("json", "xml");
        xmlParser.parseXml(convertedData);
    }
}


public class DesignPatternsHomework {
    public static void main(String[] args) {
        System.out.println("=== 1. STRATEGY ===");
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout(150);

        System.out.println("\n=== 2. CHAIN OF RESPONSIBILITY ===");
        Logger infoLogger = new InfoLogger();
        Logger errorLogger = new ErrorLogger();
        infoLogger.setNextLogger(errorLogger);
        infoLogger.logMessage(2, "This is a critical error!");

        System.out.println("\n=== 3. BUILDER ===");
        UserProfile user = new UserProfile.UserBuilder().setName("Alex").setAge(25).build();
        System.out.println("User profile created successfully.");

        System.out.println("\n=== 4. PROXY ===");
        Database adminDb = new ProxyDatabase(true);
        adminDb.query("SELECT * FROM users");
        Database guestDb = new ProxyDatabase(false);
        guestDb.query("DROP TABLE users");

        System.out.println("\n=== 5. DECORATOR ===");
        Coffee myCoffee = new MilkDecorator(new BasicCoffee());
        System.out.println("Coffee with milk cost: $" + myCoffee.getCost());

        System.out.println("\n=== 6. ADAPTER ===");
        JsonParser adapter = new XmlToJsonAdapter(new XmlParser());
        adapter.parseJson("{ \"name\": \"Alex\" }");
    }
}