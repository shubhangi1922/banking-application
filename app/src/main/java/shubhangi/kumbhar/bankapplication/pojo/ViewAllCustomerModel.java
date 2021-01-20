package shubhangi.kumbhar.bankapplication.pojo;

public class ViewAllCustomerModel {
    String cust_id;
    String cust_name;
    String cust_contact;
    String cust_email;
    String cust_account_no;
    String cust_account_balance;

    public ViewAllCustomerModel(String cust_id, String cust_name, String cust_contact, String cust_email, String cust_account_no, String cust_account_balance) {
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.cust_contact = cust_contact;
        this.cust_email = cust_email;
        this.cust_account_no = cust_account_no;
        this.cust_account_balance = cust_account_balance;
    }

    public String getCust_id() {
        return cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public String getCust_contact() {
        return cust_contact;
    }

    public String getCust_email() {
        return cust_email;
    }

    public String getCust_account_no() {
        return cust_account_no;
    }

    public String getCust_account_balance() {
        return cust_account_balance;
    }
}
