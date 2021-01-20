package shubhangi.kumbhar.bankapplication.pojo;

public class TransactionModel {
    String transaction_id;
    String amount;
    String transaction_comment;
    String transaction_type;
    String transaction_date;
    String initiator_id;

    public TransactionModel(String transaction_id, String amount, String transaction_comment, String transaction_type, String transaction_date, String initiator_id) {
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.transaction_comment = transaction_comment;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.initiator_id = initiator_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransaction_comment() {
        return transaction_comment;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public String getInitiator_id() {
        return initiator_id;
    }
}
