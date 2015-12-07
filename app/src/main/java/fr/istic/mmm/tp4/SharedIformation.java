package fr.istic.mmm.tp4;
import android.provider.BaseColumns;
/**
 * Created by soudou on 12/10/15.
 */
public class SharedIformation {
    public SharedIformation() {
    }
    public static final class Customers implements BaseColumns {

        private Customers() {}

        public static final String Customer_ID = "CUSTOMER";
        public static final String Customer_NAME = "CUSTOMER_NAME";
        public static final String Customer_SURNAME = "CUSTOMER_SURNAME";
        public static final String Customer_DATEOFBORTH="CUSTOMER_DATEOFBIRTH";
        public static final String Customer_TOWN="CUSTOMER_TOWN";
    }
}


